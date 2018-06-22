package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class ChallengeProtectedChecker extends ChallengeChecker {

    // levels are input from experimentation.
    public final static int VALUE_OK = 20;
    public final static int VALUE_GOOD = 10;
    public final static int POINTS_PER_DEATH = 10;
    private static final int MINS_5 = 300;

    private int deaths;
    private int points;


    public ChallengeProtectedChecker(String name) {
        super(name);
    }

    public String validate(Recorder recorder) {
        Context context = recorder.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preference_name), Context.MODE_PRIVATE);
        if (! timeMatches(sharedPreferences.getLong(recorder.getContext().getString(R.string.other_maxtime_key), 0))) {
            return "time does not match";
        }
        deaths = 0;
        ArrayList<DataItem> items = recorder.getData();
        for (DataItem item : items) {
            deaths += item.ncull;
        }
        points = (recorder.getMaxnbeasts() - deaths) * POINTS_PER_DEATH;
        if (deaths <= VALUE_GOOD) {
            return String.format("Well done, only %d deaths.", deaths);
        }
        if (deaths <= VALUE_OK) {
            return String.format("Not bad - only %d deaths.", deaths);
        }
        return String.format("Could do better! %d deaths.", deaths);
    }

    @Override
    public int getPoints() {
        return points;
    }

}
