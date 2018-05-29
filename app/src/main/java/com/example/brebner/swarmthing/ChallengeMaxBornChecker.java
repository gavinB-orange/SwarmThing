package com.example.brebner.swarmthing;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class ChallengeMaxBornChecker extends ChallengeChecker {

    // levels are input from experimentation.
    public final static int VALUE_OK = 180;
    public final static int VALUE_GOOD = 240;
    public final static int POINTS_PER_BIRTH = 10;
    public final static int MINS_5 = 300;

    private int births = 0;

    public ChallengeMaxBornChecker(String name) {
        super(name);
    }

    @Override
    public String validate(Recorder recorder) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(recorder.getContext());
        if (! timeMatches(sharedPreferences.getInt(recorder.getContext().getString(R.string.other_maxtime_key), 0))) {
            return "time does not match";
        }
        births = 0;
        ArrayList<DataItem> items = recorder.getData();
        for (DataItem item : items) {
            births += item.nsplit;
        }
        if (births > VALUE_GOOD) {
            return String.format("Well done, %d births.", births);
        }
        if (births > VALUE_OK) {
            return String.format("Not bad - %d births.", births);
        }
        return String.format("Could do better! Only %d births.", births);
    }

    @Override
    public int getPoints() {
        return births * POINTS_PER_BIRTH;
    }

}
