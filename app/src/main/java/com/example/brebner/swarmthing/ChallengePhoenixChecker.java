package com.example.brebner.swarmthing;

public class ChallengePhoenixChecker extends ChallengeChecker {

    private static final int POINTS_SCALE = 10;
    private static final int VALUE_GOOD = 5;
    private static final int VALUE_OK = 10;
    private int points;

    @Override
    public String validate(Recorder recorder) {
        int minn = recorder.getMinnbeasts();
        if (minn <= 0) {
            points = 0;
            return "No beasts left!";
        }
        points = (MainActivity.MAXBEASTS - minn) * POINTS_SCALE;
        if (minn < VALUE_GOOD) {
            points *= 20;
            return "Well done!";
        }
        if (minn < VALUE_OK) {
            return "Not bad";
        }
        points /= 2;
        return "Could do better!";
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public String getChallengeName() {
        return super.getChallengeName();
    }

    public ChallengePhoenixChecker(String name) {
        super(name);
    }
}
