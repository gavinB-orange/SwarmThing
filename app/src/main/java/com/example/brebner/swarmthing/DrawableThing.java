package com.example.brebner.swarmthing;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class DrawableThing {

    int xpos;
    int ypos;
    Paint paint;

    public DrawableThing() {
        xpos = 0;
        ypos = 0;
        paint = new Paint();
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    abstract public void draw(Canvas canvas);

}
