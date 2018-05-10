package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class SwarmPlaygroundView extends SurfaceView implements Runnable {

    private static final String TAG = "SwarmPlaygroundView";
    // private int BACKGROUND = Color.argb(255, 32, 96, 0);

    private final int OTHER_RATIO = 3;

    private SurfaceHolder surfaceHolder;

    private int screenX;
    private int screenY;
    private int nbeasts;

    private boolean playing = false;
    private boolean paused = false;

    private Thread gameThread;

    private long fps;

    //background
    private Bitmap bitmap;
    private Paint paint;

    // things
    private Hud hud;
    private ArrayList<Beast> beasts;

    public SwarmPlaygroundView(Context context, int screenX, int screenY, int nb) {
        super(context);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_2);
        bitmap = Bitmap.createScaledBitmap(bitmap, screenX, screenY, false);
        paint = new Paint();
        surfaceHolder = getHolder();
        this.screenX = screenX;
        this.screenY = screenY;
        nbeasts = nb;
        hud = new Hud(40,40, nbeasts);
        int n_beast_cols = (int) Math.sqrt(nbeasts);
        int n_beast_rows = nbeasts / n_beast_cols;
        while (n_beast_rows * n_beast_cols < nbeasts) {
            n_beast_cols++;
        }
        Log.d(TAG, "SwarmPlaygroundView: n_cols = " + n_beast_cols + " n_rows = " + n_beast_rows);
        beasts = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < nbeasts; i++) {
            int bx = (screenX / n_beast_cols) * (i % n_beast_cols) + screenX / Beast.NPERX;
            int by = (screenY / n_beast_rows) * ((i / n_beast_cols) % n_beast_rows) + screenY / Beast.NPERY;
            if (random.nextInt(OTHER_RATIO) == 0) {
                beasts.add(new OtherBeast(i, bx, by, screenX, screenY, R.drawable.beast_2, beasts, context));
            }
            else {
                beasts.add(new Beast(i, bx, by, screenX, screenY, R.drawable.beast_1, beasts, context));
            }
        }
        loadSounds(context);
    }

    void loadSounds(Context context) {
        Log.w(TAG, "loadSounds: NOT IMPLEMENTED", null);
        Resources res = context.getResources();
    }

    void update() {
        hud.setFps(fps);
        for (Beast b: beasts) {
            b.update();
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
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            if (!paused) {
                update();
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
