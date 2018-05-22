package com.example.brebner.swarmthing;


public class DataItem {

    public long cycle;
    public int nfb;
    public int nbeasts;
    public int nsplit;
    public int ncull;

    public DataItem(int nfb, int nbeasts, int splitcount, int cullcount) {
        this.nfb = nfb;
        this.nbeasts = nbeasts;
        this.nsplit = splitcount;
        this.ncull = cullcount;
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
