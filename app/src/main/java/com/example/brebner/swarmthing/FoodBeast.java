package com.example.brebner.swarmthing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.ArrayList;

public class FoodBeast extends Beast {

    public static final long INIT_ENERGY = 5000;

    public final int SPLIT_THRESHOLD = 1000;
    public int splitValue;

    public FoodBeast(long id, int x, int y, int screenX, int screenY, Bundle configBundle, ArrayList<Beast> beasts, Context context) {
        super(id, x, y, screenX, screenY, configBundle, beasts, context);
        bitmaps = new Bitmap[4];
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_2_50);
        bitmaps[0] = Bitmap.createScaledBitmap(bitmaps[0], width, height, false);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_2_66);
        bitmaps[1] = Bitmap.createScaledBitmap(bitmaps[1], width, height, false);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_2_75);
        bitmaps[2] = Bitmap.createScaledBitmap(bitmaps[2], width, height, false);
        bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_2_100);
        bitmaps[3] = Bitmap.createScaledBitmap(bitmaps[3], width, height, false);
        splitValue = 0;
    }

    @Override
    int getStep() {
        if (energy > INIT_ENERGY / 2) {
            return  2;
        }
        return 1;
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

    @Override
    void resetSplit() {
        super.resetSplit();
        splitValue = 0;
    }

    public void grow(long amount) {
        energy += amount;
        if (energy > INIT_ENERGY) {
            energy = INIT_ENERGY;
            splitValue ++;
        }
        if (! splitReady && splitValue > SPLIT_THRESHOLD) {
            splitReady = true;
        }
    }

    public long ouch(long max) {
        // I am being munched
        long lose = energy / 2;
        if (lose > max) {
            lose = max;
        }
        energy -= lose;
        return lose;
    }

}
