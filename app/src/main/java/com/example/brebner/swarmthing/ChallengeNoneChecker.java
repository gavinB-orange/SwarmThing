package com.example.brebner.swarmthing;

public class ChallengeNoneChecker extends ChallengeChecker {

    public ChallengeNoneChecker(String name) {
        super(name);
    }

    @Override
    public String validate(Recorder recorder) {
        return "No Challenge Selected";
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public boolean timeMatches(long time) {
        return true;
    }

}
