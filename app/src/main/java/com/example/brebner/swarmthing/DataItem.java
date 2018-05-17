package com.example.brebner.swarmthing;


public class DataItem {

    public long cycle;
    public int nfb;
    public int nbeasts;

    public DataItem(int nfb, int nbeasts) {
        this.nfb = nfb;
        this.nbeasts = nbeasts;
    }

    @Override
    public String toString() {
        return "DataItem: nfb = " + nfb + " nbeasts = " + nbeasts;
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
