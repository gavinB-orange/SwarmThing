package com.example.brebner.swarmthing;


public class DataItem {

    public long cycle;
    public int nfb;
    public int nbeasts;

    public DataItem(long cycle, int nfb, int nbeasts) {
        this.cycle = cycle;
        this.nfb = nfb;
        this.nbeasts = nbeasts;
    }

    public long getCycle() {
        return cycle;
    }

    public int getNfb() {
        return nfb;
    }

    public int getNbeasts() {
        return nbeasts;
    }

    public void setTimestamp(long cycle) {
        this.cycle = cycle;
    }

    public void setNfb(int nfb) {
        this.nfb = nfb;
    }

    public void setNbeasts(int nbeasts) {
        this.nbeasts = nbeasts;
    }

}
