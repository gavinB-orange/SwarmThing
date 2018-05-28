package com.example.brebner.swarmthing;

import java.util.ArrayList;

public class ChallengeOneChecker extends ChallengeChecker {

    public final static int VALUE_OK = 180;
    public final static int VALUE_GOOD = 200;

    public ChallengeOneChecker(String name) {
        super(name);
    }

    @Override


    public String validate(Recorder recorder) {
        int births = 0;
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

}
