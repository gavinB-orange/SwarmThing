package com.example.brebner.swarmthing;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class ChallengeMaxDeadChecker extends ChallengeChecker {

    // levels are input from experimentation.
    public final static int VALUE_OK = 180;
    public final static int VALUE_GOOD = 240;
    public final static int POINTS_PER_DEATH = 10;
    private static final int MINS_5 = 300;

    private int deaths = 0;

    public ChallengeMaxDeadChecker(String name) {
        super(name);
    }

    @Override
    public String validate(Recorder recorder) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(recorder.getContext());
        if (! timeMatches(sharedPreferences.getLong(recorder.getContext().getString(R.string.other_maxtime_key), 0))) {
            return "time does not match";
        }
        deaths = 0;
        ArrayList<DataItem> items = recorder.getData();
        for (DataItem item : items) {
            deaths += item.ncull;
        }
        if (deaths > VALUE_GOOD) {
            return String.format("Well done, %d deaths.", deaths);
        }
        if (deaths > VALUE_OK) {
            return String.format("Not bad - %d deaths.", deaths);
        }
        return String.format("Could do better! Only %d deaths.", deaths);
    }

    @Override
    public int getPoints() {
        return deaths * POINTS_PER_DEATH;
    }

}
