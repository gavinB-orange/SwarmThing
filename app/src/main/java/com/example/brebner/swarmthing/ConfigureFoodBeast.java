package com.example.brebner.swarmthing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class ConfigureFoodBeast extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    // TODO add fb_split_threshold

    private static final String TAG = "ConfigureFoodBeast";
    public static final int DEFAULT_INIT_ENERGY = 5000;
    public static final int MAX_INIT_ENERGY = 10000;
    public static final int DEFAULT_SPLIT_THRESHOLD = 1000;
    public static final int MAX_SPLIT_THRESHOLD = 4000;

    private int initenergy;
    private int split_threshold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_food_beast);
        SeekBar energySeekBar = (SeekBar)findViewById(R.id.fbEnergyseekBar);
        energySeekBar.setProgress(DEFAULT_INIT_ENERGY * energySeekBar.getMax() / MAX_INIT_ENERGY);
        energySeekBar.setOnSeekBarChangeListener(this);
        initenergy = DEFAULT_INIT_ENERGY;
        SeekBar splitSeekBar = (SeekBar)findViewById(R.id.configFBSplitSeekBar);
        splitSeekBar.setProgress(DEFAULT_SPLIT_THRESHOLD * splitSeekBar.getMax() / MAX_SPLIT_THRESHOLD);
        splitSeekBar.setOnSeekBarChangeListener(this);
        split_threshold = DEFAULT_SPLIT_THRESHOLD;

    }

    public void reportFBConfigData(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.fb_init_energy_key), initenergy);
        editor.putInt(getString(R.string.fb_split_threshold_key), split_threshold);
        editor.commit();
        Log.w(TAG, "reportFBConfigData: set FB init energy to " + initenergy);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        initenergy = (2 * progress * DEFAULT_INIT_ENERGY) / seekBar.getMax();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
