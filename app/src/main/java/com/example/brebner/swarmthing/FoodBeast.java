package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

public class FoodBeast extends Beast {

    private static final String TAG = "FoodBeast";


    private int splitValue;
    private int split_threshold;

    private long init_energy;

    private static Bitmap[] cachedbitmaps;

    FoodBeast(long id, int x, int y, int screenX, int screenY, ArrayList<Beast> beasts, Context context) {
        super(id, x, y, screenX, screenY, beasts, context);
        if (cachedbitmaps == null) {
            Log.d(TAG, "FoodBeast: Creating bitmaps");
            bitmaps = new Bitmap[4];
            bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_2_50);
            bitmaps[0] = Bitmap.createScaledBitmap(bitmaps[0], width, height, false);
            bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_2_66);
            bitmaps[1] = Bitmap.createScaledBitmap(bitmaps[1], width, height, false);
            bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_2_75);
            bitmaps[2] = Bitmap.createScaledBitmap(bitmaps[2], width, height, false);
            bitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_2_100);
            bitmaps[3] = Bitmap.createScaledBitmap(bitmaps[3], width, height, false);
            cachedbitmaps = bitmaps;
        }
        else {
            Log.d(TAG, "FoodBeast: using cached bitmaps");
            bitmaps = cachedbitmaps;
        }
        splitValue = 0;
        init_energy = sharedPreferences.getInt(context.getString(R.string.fb_init_energy_key), ConfigureFoodBeast.DEFAULT_INIT_ENERGY);
        split_threshold = sharedPreferences.getInt(context.getString(R.string.fb_split_threshold_key), ConfigureFoodBeast.DEFAULT_SPLIT_THRESHOLD);
        int fb_max_age = sharedPreferences.getInt(context.getString(R.string.fb_max_age_key), ConfigureFoodBeast.DEFAULT_AGE);
        set_max_age(fb_max_age);
        Log.d(TAG, "FoodBeast: init_energy set to " + init_energy);
        Log.d(TAG, "FoodBeast: split_threshold set to " + split_threshold);
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

    @Override
    void collisionExchange(Beast b) {
        // FoodBeast does not take action on a collision
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
