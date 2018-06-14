package com.example.brebner.swarmthing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class FingerBeast extends Beast {

    private static final String TAG = "FingerBeast";

    public static int MAX_AGE = 4;  // max age in seconds.

    private int ENERGY = 1000000;  // big number so age is the limit

    private static Bitmap[] cachedbitmaps;

    public FingerBeast(long id, int x, int y, int screenX, int screenY, ArrayList<Beast> beasts, Context context) {
        super(id, x, y, screenX, screenY, beasts, context);
        this.setEnergy(ENERGY);
        this.set_max_age(MAX_AGE);
        if (cachedbitmaps == null) {
            bitmaps = new Bitmap[1];
            bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_3);
            bitmaps[0] = Bitmap.createScaledBitmap(bitmaps[0], width, height, false);
            cachedbitmaps = bitmaps;
        }
        else {
            bitmaps = cachedbitmaps;
        }
        Log.w(TAG, "FingerBeast: created as beast " + id);
    }

    @Override
    int getStep() {
        return 1;
    }

    @Override
    Bitmap whichBitmap() {
        return bitmaps[0];
    }

    @Override
    long getInitEnergy() {
        return ENERGY;
    }

    @Override
    void set_max_age(int age) {
        myage = age;
    }
}
