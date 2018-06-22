package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Random;

public abstract class Beast extends DrawableThing {

    public static final int NPERX = 30;
    public static final int NPERY = 30;

    private static final int INEBRIATION = 50;

    long id;
    Bitmap[] bitmaps;
    private Random random;
    private int screenX;
    private int screenY;
    private int vx;
    private int vy;
    private int nextXpos;
    private int nextYpos;
    int height;
    int width;
    boolean splitReady = false;
    private boolean collided;

    protected int myage;

    private RectF rectF;
    private ArrayList<Beast> beasts;
    SharedPreferences sharedPreferences;


    public Beast(long id, int x, int y, int screenX, int screenY, ArrayList<Beast> beasts, Context context) {
        super();
        this.id = id;
        this.xpos = x;
        this.ypos = y;
        this.screenX = screenX;
        this.screenY = screenY;
        random = new Random();
        width = screenX / NPERX;
        height = screenY / NPERY;
        this.beasts = beasts;
        rectF = new RectF();
        int tmpstep = getStep();
        vx = random.nextInt(2 * tmpstep + 1) - tmpstep;
        vy = random.nextInt(2 * tmpstep + 1) - tmpstep;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preference_name), Context.MODE_PRIVATE);
        myage = 1000; // something long enough to last until we overwrite immediately
    }

    void setMaxAge(int age) {
        myage = age + random.nextInt(age / 10);
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
        rectF.left = (float)xpos;
        rectF.right = (float)xpos + (float)width;
        rectF.top = (float)ypos;
        rectF.bottom = (float)ypos + (float)height;
    }

    boolean isSplitReady() {
        return splitReady;
    }

    RectF getPseudoRectF(int x, int y) {
        // make a rectF of same size but at the new position.
        RectF rf = new RectF();
        rf.left = (float)x;
        rf.right = (float)x + (float)width;
        rf.bottom = (float)y + (float)height;
        rf.top = (float)y;
        return rf;
    }

    public boolean getNoCollision(int newxpos, int newypos) {
        boolean collision;
        for (Beast b: beasts) {
            if (b.getID() != getID()) {
                collision = RectF.intersects(getPseudoRectF(newxpos, newypos), b.getRectF());
                if (collision) {
                    return false;
                }
            }
        }
        return true;
    }


    void resetSplit() {
        splitReady = false;
    }

    public RectF getRectF() {
        return rectF;
    }

    public int getWidth() {
        return width;
    }

    public long getID() {
        return id;
    }

    abstract void collisionExchange(Beast b);

    private void doMoves() {
        // move based on current velocity
        if (xpos < 0 || xpos > screenX || ypos < 0 || ypos > screenY) {  // if somehow off screen - die
            setActive(false);
        }
        nextXpos = xpos + vx;
        nextYpos = ypos + vy;
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
        if (nextXpos <= width) {
            nextXpos = width;
            vx = tmpstep;
        }
        if (nextYpos <= height) {
            nextYpos = height;
            vy = tmpstep;
        }
        if (nextXpos >= screenX - width) {
            nextXpos = screenX - width;
            vx = -tmpstep;
        }
        if (nextYpos >= screenY - height) {
            nextYpos = screenY - height;
            vy = -tmpstep;
        }
    }

    @Override
    public void update() {
        super.update();
        // if I'm out of energy, die
        if (energy <= 0) {
            setActive(false);
            return;
        }
        doMoves();
        // collision detect with other beasts
        // collided reset per cycle in SwarmPlaygroundView
        for (Beast b: beasts) {
            if ((b.getID() != id) && ! b.hasCollided() &&  RectF.intersects(b.getRectF(), getRectF())) {
                b.setCollided();  // so only one collision between beasts per cycle
                collisionExchange(b);
                // Bounce and change direction
                nextXpos = xpos - vx;
                nextYpos = ypos - vy;
                vx = -vx;
                vy = -vy;
            }
        }
        xpos = nextXpos;
        ypos = nextYpos;
        // now update the rect
        adjustRectF();
        myage--;
        if (myage <= 0) {
            // Log.d(TAG, "update: Beast " + id + " has died of old age");
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
