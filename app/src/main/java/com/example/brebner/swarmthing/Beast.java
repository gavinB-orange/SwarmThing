package com.example.brebner.swarmthing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public abstract class Beast extends DrawableThing {

    private static final String TAG = "Beast";

    public final static int NPERX = 30;
    public final static int NPERY = 30;

    final int INEBRIATION = 50;

    int id;
    Bitmap[] bitmaps;
    Random random;
    int screenX;
    int screenY;
    int vx;
    int vy;
    int height;
    int width;

    RectF rectF;
    ArrayList<Beast> beasts;

    public Beast(int id, int x, int y, int screenX, int screenY, ArrayList<Beast> beasts, Context context) {
        super();
        this.id = id;
        this.xpos = x;
        this.ypos = y;
        this.screenX = screenX;
        this.screenY = screenY;
        random = new Random();
        vx = random.nextInt(3) - 1;
        vy = random.nextInt(3) - 1;
        width = screenX / NPERX;
        height = screenY / NPERY;
        this.beasts = beasts;
        rectF = new RectF();
    }

    private void adjustRectF() {
        rectF.left = xpos;
        rectF.right = xpos + width;
        rectF.top = ypos;
        rectF.bottom = ypos + height;
    }

    public RectF getRectF() {
        return rectF;
    }

    public int getID() {
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
        if (random.nextInt(INEBRIATION) == 0) {
            if (vx == 0) {
                vx = random.nextInt(3) - 1;
            }
            else {
                vx = - vx;
            }
        }
        if (random.nextInt(INEBRIATION) == 0) {
            if (vy == 0) {
                vy = random.nextInt(3) - 1;
            }
            else {
                vy = -vy;
            }
        }
        // bounce off walls
        if (next_xpos <= width) {
            next_xpos = width;
            vx = 1;
        }
        if (next_ypos <= height) {
            next_ypos = height;
            vy = 1;
        }
        if (next_xpos >= screenX - width) {
            next_xpos = screenX - width;
            vx = -1;
        }
        if (next_ypos >= screenY - height) {
            next_ypos = screenY - height;
            vy = -1;
        }
        // collision detect with other beasts
        for (Beast b: beasts) {
            if (b.getID() != id) {
                if (RectF.intersects(b.getRectF(), getRectF())) {
                    Log.d(TAG, "update: Collision other = " + b.getID() + " me = " + id);
                    collisionExchange(b);
                    // Bounce and change direction
                    next_xpos = xpos - vx;
                    next_ypos = ypos - vy;
                    vx = -vx;
                    vy = -vy;
                    Log.d(TAG, "update: x = " + xpos + " y = " + ypos + " vx = " + vx + " vy = " + vy);
                }
            }
        }
        xpos = next_xpos;
        ypos = next_ypos;
        // now update the rect
        adjustRectF();
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
