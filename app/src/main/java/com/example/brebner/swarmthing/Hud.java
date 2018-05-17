package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;

public class Hud extends DrawableThing {

    private final int HUD_TEXT_SIZE = 60;

    private int nbeasts;
    private float stablefps = 0;
    private boolean showtime;
    private boolean showfps;
    private boolean showelapsed;
    private long endtime;
    private long starttime;

    Hud(int x, int y, int nbeasts, long endtime, SharedPreferences sharedPreferences, Context context) {
        super();
        this.xpos = x;
        this.ypos = y;
        this.nbeasts = nbeasts;
        showtime = sharedPreferences.getBoolean(context.getString(R.string.other_show_time), ConfigureOtherActivity.DEFAULT_SHOW_TIME);
        showfps = sharedPreferences.getBoolean(context.getString(R.string.other_show_fps), ConfigureOtherActivity.DEFAULT_SHOW_FPS);
        showelapsed = sharedPreferences.getBoolean(context.getString(R.string.other_elapsed_time_key), ConfigureOtherActivity.DEFAULT_SHOW_ELAPSED_TIME);
        this.endtime = endtime;
        starttime = sharedPreferences.getLong(context.getString(R.string.start_time_key), 0);
        if (starttime == 0) {
            throw new AssertionError("Start time not initialized");
        }
        int displayColor = Color.argb(128, 0, 0, 255);
        this.paint.setColor(displayColor);
        this.paint.setTextSize(HUD_TEXT_SIZE);
    }

    public void setFps(long fps) {
        int averagerange = 100;
        stablefps = ((averagerange - 1) * stablefps + fps) / averagerange;
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
        if (showelapsed) {
            long elapsed = (System.currentTimeMillis() - starttime) / 1000;
            text = "Elapsed Time : " + elapsed;
            dy += HUD_TEXT_SIZE;
            canvas.drawText(text, xpos, ypos + dy, paint);
        }
        if (showfps) {
            text = "FPS : " + (int)stablefps;
            dy += HUD_TEXT_SIZE;
            canvas.drawText(text, xpos, ypos + dy, paint);
        }
    }
}
