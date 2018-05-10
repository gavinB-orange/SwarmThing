package com.example.brebner.swarmthing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;

public class SwarmPlaygroundActivity extends Activity {

    public final static String BEAST_NUMBER_KEY = "com.example.brebner.swarmthing.BEAST_NUMBER_KEY";

    SwarmPlaygroundView swarmPlaygroundView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Intent intent = getIntent();
        int nbeasts = intent.getIntExtra(BEAST_NUMBER_KEY, 1);

        swarmPlaygroundView = new SwarmPlaygroundView(this, size.x, size.y, nbeasts);
        setContentView(swarmPlaygroundView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        swarmPlaygroundView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        swarmPlaygroundView.pause();
    }

}
