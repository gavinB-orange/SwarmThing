package com.example.brebner.swarmthing;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;

public class SwarmPlaygroundActivity extends Activity {

    SwarmPlaygroundView swarmPlaygroundView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        swarmPlaygroundView = new SwarmPlaygroundView(this, size.x, size.y);
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
