package com.example.brebner.swarmthing;

public abstract class ChallengeChecker {

    public final static int MINS_5 = 300;

    String name;

    public abstract String validate(Recorder recorder);
    // validate that time matches expectations.
    // check the recorder data against challenge expectations.
    // side effect - set the basis for the points score.
    // return a suitable string

    public abstract int getPoints();
    // return a number of points, or 0 if validate() has not been run

    public boolean timeMatches(int time) {
        return time >= MINS_5 * 0.9 && time <= MINS_5 * 1.1;  // provide some margin
    }

    public String getChallengeName() {
        return name;
    }

    public ChallengeChecker(String name) {
        this.name = name;
    }

}
