package com.example.brebner.swarmthing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

public class Beast extends DrawableThing {

    public final static int NPERX = 30;
    public final static int NPERY = 30;

    final int INEBRIATION = 16;

    private Bitmap bitmap;
    private Random random;
    private int screenX;
    private int screenY;
    private int vx;
    private int vy;
    private int height;
    private int width;

    private RectF rectF;

    public Beast(int x, int y, int screenX, int screenY, Context context) {
        super();
        this.xpos = x;
        this.ypos = y;
        this.screenX = screenX;
        this.screenY = screenY;
        random = new Random();
        vx = random.nextInt(5) - 2;
        vy = random.nextInt(5) - 2;
        width = screenX / NPERX;
        height = screenY / NPERY;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        rectF = new RectF();
    }

    void adjustRectF() {
        rectF.left = xpos;
        rectF.right = xpos + width;
        rectF.top = ypos;
        rectF.bottom = ypos + height;
    }

    RectF getRectF() {
        return rectF;
    }

    public void update() {
        // move based on current velocity
        xpos = xpos + vx;
        ypos = ypos + vy;
        // randomly switch from time to time
        if (random.nextInt(INEBRIATION) == 0) {
            vx = - vx;
        }
        if (random.nextInt(INEBRIATION) == 0) {
            vy = - vy;
        }
        // bounce off walls
        if (xpos <= 0) {
            xpos = 0;
            vx = random.nextInt(2);
        }
        if (ypos <= 0) {
            ypos = 0;
            vy = random.nextInt(2);
        }
        if (xpos >= screenX) {
            xpos = screenX;
            vx = -random.nextInt(2);
        }
        if (ypos >= screenY) {
            ypos = screenY;
            vy = - random.nextInt(2);
        }
        adjustRectF();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, xpos, ypos, paint);
    }
}
