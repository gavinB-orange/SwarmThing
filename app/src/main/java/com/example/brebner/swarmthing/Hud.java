package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;

public class Hud extends DrawableThing {

    private final int averagerange = 100;

    private final int HUD_TEXT_SIZE = 60;
    private int displayColor = Color.argb(128, 0, 0, 255);

    private int nbeasts;
    private long fps = 0;
    private float stablefps = 0;
    private boolean showtime;
    private boolean showfps;
    private long endtime;

    public Hud(int x, int y, int nbeasts, long endtime, SharedPreferences sharedPreferences, Context context) {
        super();
        this.xpos = x;
        this.ypos = y;
        this.nbeasts = nbeasts;
        showtime = sharedPreferences.getBoolean(context.getString(R.string.other_show_time), false);
        showfps = sharedPreferences.getBoolean(context.getString(R.string.other_show_fps), false);
        this.endtime = endtime;
        this.paint.setColor(displayColor);
        this.paint.setTextSize(HUD_TEXT_SIZE);
    }

    public void setFps(long fps) {
        this.fps = fps;
        stablefps = ((averagerange - 1) * stablefps + fps) / averagerange;
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
        int dy = 0;
        String text = "Number beasts : " + nbeasts;
        canvas.drawText(text, xpos, ypos, paint);
        if (showtime) {
            long timeleft = (endtime - System.currentTimeMillis()) / 1000;
            text = "Time left : " + timeleft + "s";
            dy += HUD_TEXT_SIZE;
            canvas.drawText(text, xpos, ypos + dy, paint);
        }
        if (showfps) {
            text = "\nFPS : " + (int)stablefps;
            dy += HUD_TEXT_SIZE;
            canvas.drawText(text, xpos, ypos + dy, paint);
        }
    }
}
