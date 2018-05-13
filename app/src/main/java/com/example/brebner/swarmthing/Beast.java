package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public abstract class Beast extends DrawableThing {

    private static final String TAG = "Beast";

    public final static int NPERX = 30;
    public final static int NPERY = 30;

    final int INEBRIATION = 50;

    long id;
    Bitmap[] bitmaps;
    Random random;
    int screenX;
    int screenY;
    int vx;
    int vy;
    int height;
    int width;
    boolean splitReady = false;
    boolean collided;

    private int myage;

    private Context context;

    RectF rectF;
    ArrayList<Beast> beasts;


    public Beast(long id, int x, int y, int screenX, int screenY, ArrayList<Beast> beasts, Context context) {
        super();
        this.id = id;
        this.xpos = x;
        this.ypos = y;
        this.screenX = screenX;
        this.screenY = screenY;
        this.context = context;
        random = new Random();
        width = screenX / NPERX;
        height = screenY / NPERY;
        this.beasts = beasts;
        rectF = new RectF();
        int tmpstep = getStep();
        vx = random.nextInt(2 * tmpstep + 1) - tmpstep;
        vy = random.nextInt(2 * tmpstep + 1) - tmpstep;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        myage = sharedPreferences.getInt(context.getString(R.string.other_maxage_key), ConfigureOtherActivity.DEFAULT_MAX_AGE);
        myage += random.nextInt(ConfigureOtherActivity.DEFAULT_MAX_AGE / 10);
        Log.d(TAG, "Beast: " + id + " has age " + myage);
    }

    abstract int getStep();

    void setCollided() {
        this.collided = true;
    }

    boolean hasCollided() {
        return this.collided;
    }

    public void resetCollided() {
        this.collided = false;
    }

    private void adjustRectF() {
        rectF.left = xpos;
        rectF.right = xpos + width;
        rectF.top = ypos;
        rectF.bottom = ypos + height;
    }

    boolean isSplitReady() {
        return splitReady;
    }

    void resetSplit() {
        Log.d(TAG, "resetSplit: beast " + getID());
        splitReady = false;
    }

    public RectF getRectF() {
        return rectF;
    }

    public long getID() {
        return id;
    }

    void collisionExchange(Beast b){
        // extended by descendants
    }

    public void update() {
        super.update();
        // move based on current velocity
        int next_xpos;
        int next_ypos;
        next_xpos = xpos + vx;
        next_ypos = ypos + vy;
        // randomly switch from time to time
        int tmpstep = getStep();
        if (random.nextInt(INEBRIATION) == 0) {
            if (vx == 0) {
                vx = random.nextInt(2 * tmpstep + 1) - tmpstep;
            }
            else {
                vx = - vx;
            }
        }
        if (random.nextInt(INEBRIATION) == 0) {
            if (vy == 0) {
                vy = random.nextInt(2 * tmpstep + 1) - tmpstep;
            }
            else {
                vy = -vy;
            }
        }
        // bounce off walls
        if (next_xpos <= width) {
            next_xpos = width;
            vx = tmpstep;
        }
        if (next_ypos <= height) {
            next_ypos = height;
            vy = tmpstep;
        }
        if (next_xpos >= screenX - width) {
            next_xpos = screenX - width;
            vx = -tmpstep;
        }
        if (next_ypos >= screenY - height) {
            next_ypos = screenY - height;
            vy = -tmpstep;
        }
        // collision detect with other beasts
        for (Beast b: beasts) {
            if (b.getID() != id) {
                if (RectF.intersects(b.getRectF(), getRectF())) {
                    if (! b.hasCollided()) {  // collided reset per cycle in SwarmPlaygroundView
                        b.setCollided();  // so only one collision between beasts per cycle
                        // Log.d(TAG, "update: Collision other = " + b.getID() + " me = " + id);
                        collisionExchange(b);
                        // Bounce and change direction
                        next_xpos = xpos - vx;
                        next_ypos = ypos - vy;
                        vx = -vx;
                        vy = -vy;
                        // Log.d(TAG, "update: x = " + xpos + " y = " + ypos + " vx = " + vx + " vy = " + vy);
                    }
                }
            }
        }
        xpos = next_xpos;
        ypos = next_ypos;
        // now update the rect
        adjustRectF();
        myage--;
        if (myage <= 0) {
            Log.d(TAG, "update: Beast " + id + " has died of old age");
           setActive(false);
        }
    }

    // choose which bitmap to show
    abstract Bitmap whichBitmap();

    // return the initial energy
    abstract long getInitEnergy();

    @Override
    public void draw(Canvas canvas) {
        if (bitmaps != null) {
            Bitmap bitmap = whichBitmap();
            canvas.drawBitmap(bitmap, xpos, ypos, paint);
        }
    }
}
