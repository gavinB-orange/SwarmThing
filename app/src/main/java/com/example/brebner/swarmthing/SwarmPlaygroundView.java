package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SwarmPlaygroundView extends SurfaceView implements Runnable {

    public static final int SUNLIGHT = 200;
    private boolean sound_effects_on;
    private int lightlevel;
    private int sane_light;

    private static final String TAG = "SwarmPlaygroundView";

    private SurfaceHolder surfaceHolder;

    private int screenX;
    private int screenY;

    private boolean playing = false;

    private Thread gameThread;

    private long fps;
    private long endtime;
    
    private Context context;

    // sounds
    private SoundPool soundPool;
    private int collisionSoundID;
    private int splitSoundID;
    private int cullSoundID;

    //background
    private Bitmap bitmap;
    private Paint paint;

    // recorder to create graph at end
    private Recorder recorder;
    private int splitcount;
    private int cullcount;

    // things
    private Hud hud;
    private ArrayList<Beast> beasts;
    private long beastID;  // monotonic beast ID

    private boolean started;

    private Random random;

    private boolean timeup = false;  // signal that time has expired
    private boolean unlimited_time;

    // challenge
    private int whichChallenge;
    private String challengResult;


    // dummy for tools
    public SwarmPlaygroundView(Context context) {
        this(context, 100, 100);
    }

    public SwarmPlaygroundView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int nbeasts = sharedPreferences.getInt(context.getString(R.string.other_nbeasts_key), ConfigureOtherActivity.DEFAULT_N_BEASTS);
        int other_ratio = sharedPreferences.getInt(context.getString(R.string.other_ratio_key), ConfigureOtherActivity.DEFAULT_RATIO);
        sane_light = sharedPreferences.getInt(context.getString(R.string.fb_sane_light_key), ConfigureFoodBeast.DEFAULT_SANE_LIGHT);
        unlimited_time = sharedPreferences.getBoolean(context.getString(R.string.other_unlimited_time_key), ConfigureOtherActivity.DEFAULT_UNLIMITED_TIME);
        sound_effects_on = sharedPreferences.getBoolean(context.getString(R.string.sound_effects_on_key), ConfigureOtherActivity.DEFAULT_SOUND_EFFECTS_ON);
        whichChallenge = sharedPreferences.getInt(context.getString(R.string.challenge_choice_key), ChallengeActivity.NO_CHALLENGE_SELECTED);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_4);
        bitmap = Bitmap.createScaledBitmap(bitmap, screenX, screenY, false);
        paint = new Paint();
        surfaceHolder = getHolder();
        this.screenX = screenX;
        this.screenY = screenY;
        int n_beast_cols = (int) Math.sqrt(nbeasts);
        int n_beast_rows = nbeasts / n_beast_cols;
        while (n_beast_rows * n_beast_cols < nbeasts) {
            n_beast_cols++;
        }
        beasts = new ArrayList<>();
        random = new Random();
        beastID = 0;
        for (int i = 0; i < nbeasts; i++) {
            int bx = (screenX / n_beast_cols) * (i % n_beast_cols) + screenX / Beast.NPERX;
            int by = (screenY / n_beast_rows) * ((i / n_beast_cols) % n_beast_rows) + screenY / Beast.NPERY;
            if (random.nextInt(ConfigureOtherActivity.MAX_RATIO) >= other_ratio) {
                beasts.add(new FoodBeast(beastID, bx, by, screenX, screenY, beasts, context));
            }
            else {
                beasts.add(new GrazingBeast(beastID, bx, by, screenX, screenY, beasts, context));
            }
            beastID++;
        }
        for (Beast b: beasts){
            b.setActive(true);
            b.setEnergy(random.nextInt((int)b.getInitEnergy()));
        }
        loadSounds(context);
        recorder = new Recorder(context);
        splitcount = 0;
        cullcount = 0;
        lightlevel = sane_light;
        started = false;  // set to true once game has truly started.
        endtime = System.currentTimeMillis() + sharedPreferences.getLong(context.getString(R.string.other_maxtime_key), ConfigureOtherActivity.DEFAULT_TIME);
        hud = new Hud(40,40, screenY, nbeasts, endtime, sharedPreferences, context);
    }

    private void loadSounds(Context context) {
        SoundPool.Builder spb;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            spb = new SoundPool.Builder();
            spb.setMaxStreams(10);
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            spb.setAudioAttributes(aa);
            soundPool = spb.build();
        }
        else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            // Create objects of the 2 required classes
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            // Load our fx in memory ready for use
            descriptor = assetManager.openFd("split.ogg");
            splitSoundID = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("big-bell.ogg");
            cullSoundID = soundPool.load(descriptor, 0);
        }
        catch(IOException e) {
            // Print an error message to the console
            Log.e(TAG, "loadSounds: FAILED to load", e);
        }
    }

    void update(long cycle) {
        hud.setFps(fps);
        hud.setNbeasts(beasts.size());
        int nFB = 0;
        for (Beast b: beasts) {
            b.resetCollided();
        }
        for (Beast b: beasts) {
            started = true;
            b.update();
            if (b.getClass() == FoodBeast.class) {
                FoodBeast fb = (FoodBeast) b;
                fb.grow(random.nextInt(lightlevel));
                nFB ++;
            }
        }
        if (nFB > 0) {  // no divide by 0 please
            lightlevel = SUNLIGHT / nFB;  // as more FBs, light/FB gets less.
        }
        else {
            lightlevel = sane_light;
        }
        if (lightlevel > sane_light) {
            lightlevel = sane_light;
        }
        if (cycle % 50 == 0) { // roughly every second or so
            if (sound_effects_on && splitcount > 0) {
                soundPool.play(splitSoundID, 1, 1, 0, 0, 1);
            }
            if (sound_effects_on && cullcount > 0) {
                soundPool.play(cullSoundID, 1, 1, 0, 0, 1);
            }
            recorder.putData(nFB, beasts.size(), splitcount, cullcount);
            splitcount = 0;
            cullcount = 0;
            // also check endtime and quit if we are done
            if (! unlimited_time && System.currentTimeMillis() > endtime) {
                timeup = true;
            }
        }
        if (started && ((nFB == 0) || (nFB == beasts.size()) || timeup)) {
            playing = false;
            Intent intent = new Intent(context, EndActivity.class);
            intent.putExtra(context.getString(R.string.final_fb_value_key), nFB);
            intent.putExtra(context.getString(R.string.final_gb_value_key), beasts.size() - nFB);
            intent.putExtra(context.getString(R.string.final_time_up_key), timeup);
            // create and save compressed graph image - should this be made async?
            long start = PreferenceManager.getDefaultSharedPreferences(context).getLong(context.getString(R.string.start_time_key), 0);
            long now = System.currentTimeMillis();
            long seconds = (now - start) / 1000;
            Bitmap graph = recorder.createBitmapDrawing(screenX - 10, screenY / 3, seconds);
            context.deleteFile(context.getString(R.string.recorder_graph_file_name));  // just in case
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                graph.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                FileOutputStream fo = context.openFileOutput(context.getString(R.string.recorder_graph_file_name), Context.MODE_PRIVATE);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (Exception e) {
                Log.e(TAG, "update: failed to write graph file", e);
            }
            ChallengeChecker challengeChecker = null;
            String challengeResult;
            int challengePoints;
            switch (whichChallenge) {
                case ChallengeActivity.NO_CHALLENGE_SELECTED:
                    challengeChecker = new ChallengeNoneChecker(getResources().getString(R.string.challenge_none_description));
                    break;
                case ChallengeActivity.CHALLENGE_MOST_DEAD:
                    challengeChecker = new ChallengeMaxDeadChecker(getResources().getString(R.string.challenge_most_dead_5m_description));
                    break;
                case ChallengeActivity.CHALLENGE_MOST_BORN:
                    challengeChecker = new ChallengeMaxBornChecker(getResources().getString(R.string.challenge_most_born_5m_description));
                    break;
                case ChallengeActivity.CHALLENGE_MOST_BEAST_ITERATIONS:
                    challengeChecker = new ChallengeBeastIterationsChecker(getResources().getString(R.string.challenge_most_beast_iterations_5m_description));
                    break;
                case ChallengeActivity.CHALLENGE_MOST_PROTECTED:
                    challengeChecker = new ChallengeProtectedChecker(getResources().getString(R.string.challenge_most_protected_5m_description));
                    break;
                case ChallengeActivity.CHALLENGE_PHOENIX:
                    challengeChecker = new ChallengePhoenixChecker(getResources().getString(R.string.challenge_phoenix_5m_description));
                    break;
                default:
                    challengeChecker = null;
                    Log.w(TAG, "update: Unknown challenge");
                    break;
            }
            if (challengeChecker == null) {
                challengeResult = "Unknown";
                challengePoints = 0;
            }
            else {
                challengeResult = challengeChecker.validate(recorder);
                challengePoints = challengeChecker.getPoints();
            }
            intent.putExtra(context.getString(R.string.challenge_result_key), challengeResult);
            intent.putExtra(context.getString(R.string.challenge_result_points_key), challengePoints);
            context.startActivity(intent);
        }
    }
    
    void split() {
        if (beasts.size() > MainActivity.MAXBEASTS) {  // stop at max !
            for (Beast b: beasts) {
                b.resetSplit();
            }
            return;
        }
        ArrayList<Beast> tosplit = new ArrayList<>();
        for (Beast b: beasts) {
            if (b.isSplitReady()) {
                tosplit.add(b);
            }
        }
        splitcount += tosplit.size();
        if (tosplit.size() < 1) {
            return;
        }
        for (Beast b: tosplit) {
            int newxpos = b.getXpos() + 2 + b.getWidth();
            if (newxpos > screenX)  {
                newxpos = screenX - 2 - b.getWidth();
            }
            int newypos = b.getYpos();  // try leaving y alone
            // int newypos = b.getYpos() + 1 + screenY / (2 * Beast.NPERY);
            // if (newypos > screenY)  {
            //     newypos = screenY - screenY / (2 * Beast.NPERY);
            // }
            if (b.getClass() == FoodBeast.class) {
                FoodBeast fb = (FoodBeast)b;
            }
            if (b.getNoCollision(newxpos, newypos)) {
                b.setEnergy(b.getEnergy() / 2);
                b.resetSplit();
                Beast newb;
                if (b.getClass() == FoodBeast.class) {
                    newb = new FoodBeast(beastID, newxpos, newypos, screenX, screenY, beasts, context);
                }
                else {
                    newb = new GrazingBeast(beastID, newxpos, newypos, screenX, screenY, beasts, context);
                }
                beastID++;
                newb.setEnergy(b.getEnergy());
                beasts.add(newb);
            }
            else {
                Log.w(TAG, "split: " + b.getClass() + " : " + b.id + " deferred split due to collision ...");
                splitcount--;
            }
        }
    }

    void cull() {
        ArrayList<Beast> todie = new ArrayList<>();
        for (Beast b: beasts) {
            if (! b.isActive()) {
                todie.add(b);
            }
        }
        cullcount += todie.size();
        for (Beast b: todie) {
            beasts.remove(b);
            // Log.d(TAG, "cull: removing beast " + b.getID());
        }
    }

    void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(bitmap, 0, 0, paint);
            hud.draw(canvas);
            for (Beast b: beasts) {
                b.draw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        long cycle = 0;
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            boolean paused = false;
            if (!paused) {
                update(cycle);
                cycle ++;
                cull();
                split();
            }
            draw();

            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    public void pause() {
        Log.w(TAG, "pause: ");
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "pause: ERROR", e);
        }
    }

    public void resume() {
        Log.w(TAG, "resume: ");
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}
