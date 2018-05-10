package com.example.brebner.swarmthing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SwarmPlaygroundView extends SurfaceView implements Runnable {

    private static final String TAG = "SwarmPlaygroundView";
    private int BACKGROUND = Color.argb(255, 32, 96, 0);

    private Context context;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    private int screenX;
    private int screenY;
    private int nbeasts;

    private boolean playing = false;
    private boolean paused = false;

    private Thread gameThread;

    private long fps;

    // things
    private Hud hud;
    private Beast exampleBeast;

    public SwarmPlaygroundView(Context context, int x, int y, int nb) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        screenX = x;
        screenY = y;
        nbeasts = nb;
        hud = new Hud(40,40, nbeasts);
        exampleBeast = new Beast(screenX / 2, screenY / 2, context);
        loadSounds(context);
        prepareLevel();
    }

    void loadSounds(Context context) {

    }

    void prepareLevel() {

    }

    void update() {
    }

    void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(BACKGROUND);
            hud.draw(canvas);
            exampleBeast.draw(canvas);
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
