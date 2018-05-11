package com.example.brebner.swarmthing;

import android.graphics.Canvas;
import android.graphics.Color;

public class Hud extends DrawableThing {


    private final int HUD_TEXT_SIZE = 40;
    private int displayColor = Color.argb(128, 0, 0, 255);

    private int nbeasts;
    private long fps = 0;

    public Hud(int x, int y, int nbeasts) {
        super();
        this.xpos = x;
        this.ypos = y;
        this.nbeasts = nbeasts;
        this.paint.setColor(displayColor);
        this.paint.setTextSize(HUD_TEXT_SIZE);
    }

    public void setFps(long fps) {
        this.fps = fps;
    }

    public void setDisplayColor(int displayColor) {
        this.displayColor = displayColor;
    }

    public int getDisplayColor() {
        return this.displayColor;
    }

    public int getNbeasts() {
        return nbeasts;
    }

    public void setNbeasts(int nbeasts) {
        this.nbeasts = nbeasts;
    }

    public void draw(Canvas canvas) {
        canvas.drawText("Number beasts : " + nbeasts + " FPS = " + fps, xpos, ypos, paint);
    }
}
