package com.example.brebner.swarmthing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class GrazingBeast extends Beast {

    public final static long INIT_ENERGY = 8000;

    public final int SPLIT_THRESHOLD = 10;
    public int splitValue;

    private static final String TAG = "GrazingBeast";

    public GrazingBeast(long id, int x, int y, int screenX, int screenY, Bundle configBundle, ArrayList<Beast> beasts, Context context) {
        super(id, x, y, screenX, screenY, configBundle, beasts, context);
        bitmaps = new Bitmap[4];
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1_50);
        bitmaps[0] = Bitmap.createScaledBitmap(bitmaps[0], width, height, false);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1_66);
        bitmaps[1] = Bitmap.createScaledBitmap(bitmaps[1], width, height, false);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1_75);
        bitmaps[2] = Bitmap.createScaledBitmap(bitmaps[2], width, height, false);
        bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1_100);
        bitmaps[3] = Bitmap.createScaledBitmap(bitmaps[3], width, height, false);
    }

    @Override
    void resetSplit() {
        super.resetSplit();
        splitValue = 0;
    }

    @Override
    int getStep() {
        if (energy < INIT_ENERGY / 2) {  // faster if hungry
            return 2;
        }
        return 1;
    }

    @Override
    void collisionExchange(Beast b) {
        super.collisionExchange(b);
        // I am grazing beast - can I eat?
        if (energy > INIT_ENERGY) {
            // I am full
            return;
        }
        // I am hungry - feed me
        if (b.getClass() == FoodBeast.class) {
            FoodBeast fb = (FoodBeast)b;
            long meal = fb.ouch(INIT_ENERGY - energy);  // cannot take more than INIT_ENERGY
            energy += meal;
            Log.d(TAG, "collisionExchange: grazer " + getID() + " taking " + meal + " from FoodBeast " + fb.getID());
            if (energy > 3 * INIT_ENERGY / 4 && meal > 0) {
                splitValue++;
                if (splitValue > SPLIT_THRESHOLD) {
                    splitReady = true;
                    splitValue = SPLIT_THRESHOLD;
                }
            }
        }
    }

    @Override
    Bitmap whichBitmap() {
        if (energy < INIT_ENERGY / 4) {
            return bitmaps[0];
        }
        if (energy < INIT_ENERGY / 2) {
            return bitmaps[1];
        }
        if (energy < 3 * INIT_ENERGY / 4) {
            return bitmaps[1];
        }
        return bitmaps[3];
    }

    @Override
    long getInitEnergy() {
        return INIT_ENERGY;
    }

}
