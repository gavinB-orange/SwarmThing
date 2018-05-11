package com.example.brebner.swarmthing;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class GrazingBeast extends Beast {

    private static final String TAG = "GrazingBeast";

    public GrazingBeast(int id, int x, int y, int screenX, int screenY, int drawable, ArrayList<Beast> beasts, Context context) {
        super(id, x, y, screenX, screenY, drawable, beasts, context);
    }

    @Override
    void collisionExchange(Beast b) {
        super.collisionExchange(b);
        // I am grazing beast - can I eat?
        if (b.getClass() == FoodBeast.class) {
            FoodBeast fb = (FoodBeast)b;
            long meal = fb.ouch();
            energy += meal;
            Log.d(TAG, "collisionExchange: grazer " + getID() + " taking " + meal + " from FoodBeast " + fb.getID());
        }
    }
}
