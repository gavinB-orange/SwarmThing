package com.example.brebner.swarmthing;

public abstract class ChallengeChecker {

    String name;

    public abstract String validate(Recorder recorder);

    public String getChallengeName() {
        return name;
    }

    public ChallengeChecker(String name) {
        this.name = name;
    }

}
