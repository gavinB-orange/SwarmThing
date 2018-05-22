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
    public final static int MAX_AGE = 20000;
    public final static int DEFAULT_AGE = 8000;
    public final static int MAX_SANE_LIGHT = 50;
    public final static int DEFAULT_SANE_LIGHT = 10;

    private int initenergy;
    private int split_threshold;
    private int max_age;
    private int sane_light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_food_beast);
        SeekBar energySeekBar = findViewById(R.id.fbEnergyseekBar);
        energySeekBar.setProgress(DEFAULT_INIT_ENERGY * energySeekBar.getMax() / MAX_INIT_ENERGY);
        energySeekBar.setOnSeekBarChangeListener(this);
        initenergy = DEFAULT_INIT_ENERGY;
        SeekBar splitSeekBar = findViewById(R.id.configFBSplitSeekBar);
        splitSeekBar.setProgress(DEFAULT_SPLIT_THRESHOLD * splitSeekBar.getMax() / MAX_SPLIT_THRESHOLD);
        splitSeekBar.setOnSeekBarChangeListener(this);
        split_threshold = DEFAULT_SPLIT_THRESHOLD;
        SeekBar maxAgeSeekBar = findViewById(R.id.configFBMaxAgeSeekBar);
        maxAgeSeekBar.setOnSeekBarChangeListener(this);
        maxAgeSeekBar.setProgress(DEFAULT_AGE * maxAgeSeekBar.getMax() / MAX_AGE);
        max_age = DEFAULT_AGE;
        SeekBar saneLightSeekBar = findViewById(R.id.configFBSaneLightSeekBar);
        saneLightSeekBar.setOnSeekBarChangeListener(this);
        saneLightSeekBar.setProgress(DEFAULT_SANE_LIGHT * saneLightSeekBar.getMax() / MAX_SANE_LIGHT);
        sane_light = DEFAULT_SANE_LIGHT;
    }

    public void reportFBConfigData(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.fb_init_energy_key), initenergy);
        editor.putInt(getString(R.string.fb_split_threshold_key), split_threshold);
        editor.putInt(getString(R.string.fb_max_age_key), max_age);
        editor.putInt(getString(R.string.fb_sane_light_key), sane_light);
        editor.commit();
        Log.w(TAG, "reportFBConfigData: set FB init energy to " + initenergy);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.fbEnergyseekBar) {
            initenergy = (progress * MAX_INIT_ENERGY) / seekBar.getMax();
        }
        if (seekBar.getId() == R.id.configFBSplitSeekBar) {
            split_threshold = progress * MAX_SPLIT_THRESHOLD / seekBar.getMax();
        }
        if (seekBar.getId() == R.id.configFBMaxAgeSeekBar) {
            max_age = progress * MAX_AGE / seekBar.getMax() + MAX_AGE / 10;  // no zero age please
        }
        if (seekBar.getId() == R.id.configFBSaneLightSeekBar) {
            sane_light = progress * MAX_SANE_LIGHT / seekBar.getMax();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
