package com.example.brebner.swarmthing;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class DrawableThing {

    int xpos;
    int ypos;
    long energy;
    private boolean active;

    Paint paint;

    public DrawableThing() {
        xpos = 0;
        ypos = 0;
        paint = new Paint();
        setActive(true);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getEnergy() {
        return energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

    public void update(){
        energy--;
        if (energy <= 0){
            setActive(false);
        }
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
