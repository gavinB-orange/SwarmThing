package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

public class GrazingBeast extends Beast {

    public final static int DEFAULT_INIT_ENERGY = 8000;
    public final int DEFAULT_SPLIT_THRESHOLD = 10;

    private int init_energy;
    private int split_threshold;

    private int splitValue;

    private static final String TAG = "GrazingBeast";

    public GrazingBeast(long id, int x, int y, int screenX, int screenY, ArrayList<Beast> beasts, Context context) {
        super(id, x, y, screenX, screenY, beasts, context);
        bitmaps = new Bitmap[4];
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1_50);
        bitmaps[0] = Bitmap.createScaledBitmap(bitmaps[0], width, height, false);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1_66);
        bitmaps[1] = Bitmap.createScaledBitmap(bitmaps[1], width, height, false);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1_75);
        bitmaps[2] = Bitmap.createScaledBitmap(bitmaps[2], width, height, false);
        bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1_100);
        bitmaps[3] = Bitmap.createScaledBitmap(bitmaps[3], width, height, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        init_energy = sharedPreferences.getInt(context.getString(R.string.gb_init_energy), DEFAULT_INIT_ENERGY);
        split_threshold = sharedPreferences.getInt(context.getString(R.string.gb_split_threshold), DEFAULT_SPLIT_THRESHOLD);
        Log.w(TAG, "GrazingBeast: init_energy = " + init_energy, null);
        Log.w(TAG, "GrazingBeast: split_threshold = " + split_threshold, null);
    }

    @Override
    void resetSplit() {
        super.resetSplit();
        splitValue = 0;
    }

    @Override
    int getStep() {
        if (energy < init_energy / 2) {  // faster if hungry
            return 2;
        }
        return 1;
    }

    @Override
    void collisionExchange(Beast b) {
        super.collisionExchange(b);
        // I am grazing beast - can I eat?
        if (energy > init_energy) {
            // I am full
            return;
        }
        // I am hungry - feed me
        if (b.getClass() == FoodBeast.class) {
            FoodBeast fb = (FoodBeast)b;
            long meal = fb.ouch(init_energy - energy);  // cannot take more than INIT_ENERGY
            energy += meal;
            Log.d(TAG, "collisionExchange: grazer " + getID() + " taking " + meal + " from FoodBeast " + fb.getID());
            if (energy > 3 * init_energy / 4 && meal > 0) {
                splitValue++;
                if (splitValue > split_threshold) {
                    splitReady = true;
                    splitValue = split_threshold;
                }
            }
        }
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

}
