package com.example.brebner.swarmthing;

import android.content.Context;

import java.util.ArrayList;

public class FoodBeast extends Beast {

    public FoodBeast(int id, int x, int y, int screenX, int screenY, int drawable, ArrayList<Beast> beasts, Context context) {
        super(id, x, y, screenX, screenY, drawable, beasts, context);
    }

    public void grow(long amount) {
        energy += amount;
    }

    public long ouch() {
        // I am being munched
        long lose = energy / 2;
        energy -= lose;
        return lose;
    }

}
