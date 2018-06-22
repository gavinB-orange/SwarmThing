package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class ChallengeBeastIterationsChecker extends ChallengeChecker {

    private static final int CROSS_COUNT_POINTS_SCALE = 25;
    private int cross_count = 0;
    
    @Override
    public String validate(Recorder recorder) {
        ArrayList<DataItem> items = recorder.getData();
        float average = recorder.getAverageNBeasts();
        cross_count = 0;
        SharedPreferences sharedPreferences = recorder.getContext().getSharedPreferences(recorder.getContext().getString(R.string.shared_preference_name), Context.MODE_PRIVATE);
        int initial = sharedPreferences.getInt(recorder.getContext().getString(R.string.other_nbeasts_key), 0);
        if (! timeMatches(sharedPreferences.getLong(recorder.getContext().getString(R.string.other_maxtime_key), 0))) {
           return "time does not match";
        }
        boolean increasing = (initial < average);
        for (int i = 0; i < items.size() - 1; i++) {
            if (increasing) {
                if (items.get(i).nbeasts > average && items.get(i+1).nbeasts > average) {
                    increasing = false;
                    cross_count += 1;
                }
            }
            else {
                if (items.get(i).nbeasts < average && items.get(i+1).nbeasts < average) {
                    increasing = true;
                    cross_count += 1;
                }
            }
        }
        return String.format("Saw %d crossings of the average.", cross_count);
    }

    @Override
    public int getPoints() {
        return cross_count * CROSS_COUNT_POINTS_SCALE;
    }

    @Override
    public String getChallengeName() {
        return super.getChallengeName();
    }

    public ChallengeBeastIterationsChecker(String name) {
        super(name);
    }
}
