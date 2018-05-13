package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

public class FoodBeast extends Beast {

    private static final String TAG = "FoodBeast";

    public static final int DEFAULT_INIT_ENERGY = 5000;

    public final int DEFAULT_SPLIT_THRESHOLD = 1000;

    private int splitValue;
    private int split_threshold;

    private long init_energy;

    public FoodBeast(long id, int x, int y, int screenX, int screenY, ArrayList<Beast> beasts, Context context) {
        super(id, x, y, screenX, screenY, beasts, context);
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        init_energy = sharedPreferences.getInt(context.getString(R.string.fb_init_energy), DEFAULT_INIT_ENERGY);
        split_threshold = sharedPreferences.getInt(context.getString(R.string.fb_split_threshold), DEFAULT_SPLIT_THRESHOLD);
        Log.w(TAG, "FoodBeast: init_energy set to " + init_energy);
        Log.w(TAG, "FoodBeast: split_threshold set to " + split_threshold);
    }

    @Override
    int getStep() {
        if (energy > init_energy / 2) {
            return  2;
        }
        return 1;
    }

    @Override
    Bitmap whichBitmap() {
        if (energy < init_energy / 4) {
            return bitmaps[0];
        }
        if (energy < init_energy / 2) {
            return bitmaps[1];
        }
        if (energy < 3 * init_energy / 4) {
            return bitmaps[1];
        }
        return bitmaps[3];
    }

    @Override
    long getInitEnergy() {
        return init_energy;
    }

    @Override
    void resetSplit() {
        super.resetSplit();
        splitValue = 0;
    }

    public void grow(long amount) {
        energy += amount;
        if (energy > init_energy) {
            energy = init_energy;
            splitValue ++;
        }
        if (! splitReady && splitValue > split_threshold) {
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
