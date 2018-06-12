package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;

public class Hud extends DrawableThing {

    private static final int PADDING = 10;

    private int nbeasts;
    private float stablefps = 0;
    private boolean showtime;
    private boolean showfps;
    private boolean showelapsed;
    private long endtime;
    private long starttime;


    private int hud_text_size;
    private static final int HUD_COLOR = Color.argb(128, 200, 200, 200);


    Hud(int x, int y, int height, int nbeasts, long endtime, SharedPreferences sharedPreferences, Context context) {
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
        int displayColor = HUD_COLOR;
        this.paint.setColor(displayColor);
        hud_text_size = height / 40;
        this.paint.setTextSize(hud_text_size);
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
            dy += hud_text_size + PADDING;
            canvas.drawText(text, xpos, ypos + dy, paint);
        }
        if (showelapsed) {
            long elapsed = (System.currentTimeMillis() - starttime) / 1000;
            text = "Elapsed Time : " + elapsed;
            dy += hud_text_size + PADDING;
            canvas.drawText(text, xpos, ypos + dy, paint);
        }
        if (showfps) {
            text = "FPS : " + (int)stablefps;
            dy += hud_text_size + PADDING;
            canvas.drawText(text, xpos, ypos + dy, paint);
        }
    }
}
