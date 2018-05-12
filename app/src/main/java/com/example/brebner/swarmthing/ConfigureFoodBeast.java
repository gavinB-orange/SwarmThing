package com.example.brebner.swarmthing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class ConfigureFoodBeast extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "ConfigureFoodBeast";
    public final int DEFAULT_INIT_ENERGY = 5000;
    public final static String FB_KEY = "com.example.brebner.swarmthing.FB_KEY";
    public final static String FB_INIT_ENERGY = "FB_INIT_ENERGY";

    private int initenergy = DEFAULT_INIT_ENERGY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_food_beast);
        SeekBar energySeekBar = (SeekBar)findViewById(R.id.fbEnergyseekBar);
        energySeekBar.setOnSeekBarChangeListener(this);
    }

    public void reportFBConfigData(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.WHOAMI_KEY, FB_KEY);
        bundle.putInt(FB_INIT_ENERGY, initenergy);
        intent.putExtra(FB_KEY, bundle);
        Log.d(TAG, "reportFBConfigData: sending FB init energy value of " + initenergy);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        initenergy = progress * DEFAULT_INIT_ENERGY / (2 * seekBar.getMax());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
