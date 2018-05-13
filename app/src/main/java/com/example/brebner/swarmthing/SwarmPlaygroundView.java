package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class SwarmPlaygroundView extends SurfaceView implements Runnable {

    private static final String TAG = "SwarmPlaygroundView";

    private final int DEFAULT_OTHER_RATIO = 2;
    private final int DEFAULT_BEAST_NUMBER = 12;

    private SurfaceHolder surfaceHolder;

    private int screenX;
    private int screenY;
    private int nbeasts;
    private int other_ratio;

    private boolean playing = false;
    private boolean paused = false;

    private Thread gameThread;

    private long fps;
    
    private Context context;

    //background
    private Bitmap bitmap;
    private Paint paint;

    // things
    private Hud hud;
    private ArrayList<Beast> beasts;
    private long beastID;  // monotonic beast ID


    public SwarmPlaygroundView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        nbeasts = sharedPreferences.getInt(context.getString(R.string.beast_number_key), DEFAULT_BEAST_NUMBER);
        Log.w(TAG, "SwarmPlaygroundView: nbeasts = " + nbeasts, null);
        other_ratio = sharedPreferences.getInt(context.getString(R.string.other_ratio_key), DEFAULT_OTHER_RATIO);
        Log.w(TAG, "SwarmPlaygroundView: other_ratio set to " + other_ratio, null);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_3);
        bitmap = Bitmap.createScaledBitmap(bitmap, screenX, screenY, false);
        paint = new Paint();
        surfaceHolder = getHolder();
        this.screenX = screenX;
        this.screenY = screenY;
        hud = new Hud(40,40, nbeasts);
        int n_beast_cols = (int) Math.sqrt(nbeasts);
        int n_beast_rows = nbeasts / n_beast_cols;
        while (n_beast_rows * n_beast_cols < nbeasts) {
            n_beast_cols++;
        }
        Log.d(TAG, "SwarmPlaygroundView: n_cols = " + n_beast_cols + " n_rows = " + n_beast_rows);
        beasts = new ArrayList<>();
        Random random = new Random();
        beastID = 0;
        for (int i = 0; i < nbeasts; i++) {
            int bx = (screenX / n_beast_cols) * (i % n_beast_cols) + screenX / Beast.NPERX;
            int by = (screenY / n_beast_rows) * ((i / n_beast_cols) % n_beast_rows) + screenY / Beast.NPERY;
            if (random.nextInt(other_ratio) == 0) {
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
    }

    void loadSounds(Context context) {
        Log.w(TAG, "loadSounds: NOT IMPLEMENTED", null);
        Resources res = context.getResources();
    }

    void update(long cycle) {
        hud.setFps(fps);
        hud.setNbeasts(beasts.size());
        for (Beast b: beasts) {
            b.update();
            if (b.getClass() == FoodBeast.class) {
                FoodBeast fb = (FoodBeast) b;
                fb.grow((cycle / 100) % 10);
            }
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
        if (tosplit.size() < 1) {
            return;
        }
        Log.d(TAG, "split: Have " + tosplit.size() + " beasts ready to split");
        for (Beast b: tosplit) {
            if (b.getClass() == FoodBeast.class) {
                Log.w(TAG, "split: " + b.getID() + " Splitting to form new FoodBeast");
                FoodBeast fb = (FoodBeast)b;
                fb.setEnergy(fb.getEnergy() / 2);
                fb.resetSplit();
                FoodBeast newfb = new FoodBeast(beastID, fb.getXpos() + 8, fb.getYpos() + 8, screenX, screenY, beasts, context);
                beastID++;
                newfb.setEnergy(fb.getEnergy());
                beasts.add(newfb);
            }
            else {
                if (b.getClass() == GrazingBeast.class) {
                    Log.w(TAG, "split: Splitting to form new GrazingBeast");
                    GrazingBeast gb = (GrazingBeast)b;
                    gb.energy = gb.energy / 2;
                    gb.resetSplit();
                    GrazingBeast newgb = new GrazingBeast(beastID, gb.getXpos() + 8, gb.getYpos() + 8, screenX, screenY, beasts, context);
                    beastID++;
                    newgb.setEnergy(gb.energy);
                    beasts.add(newgb);
                }
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
        for (Beast b: todie) {
            beasts.remove(b);
            Log.d(TAG, "cull: removing beast " + b.getID());
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
        Log.d(TAG, "run ");
        long cycle = 0;
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
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
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "pause: ERROR", e);
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}
