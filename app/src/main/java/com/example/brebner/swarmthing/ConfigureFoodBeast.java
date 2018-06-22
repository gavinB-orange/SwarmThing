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
    private int maxAge;
    private int sane_light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_food_beast);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int initial_init_energy = sharedPreferences.getInt(getString(R.string.fb_init_energy_key), DEFAULT_INIT_ENERGY);
        int initial_split_threshold = sharedPreferences.getInt(getString(R.string.fb_split_threshold_key), DEFAULT_SPLIT_THRESHOLD);
        int initial_max_age = sharedPreferences.getInt(getString(R.string.fb_max_age_key), DEFAULT_AGE);
        int initial_sane_light = sharedPreferences.getInt(getString(R.string.fb_sane_light_key), DEFAULT_SANE_LIGHT);
        SeekBar energySeekBar = findViewById(R.id.fbEnergyseekBar);
        energySeekBar.setProgress(initial_init_energy * energySeekBar.getMax() / MAX_INIT_ENERGY);
        energySeekBar.setOnSeekBarChangeListener(this);
        initenergy = initial_init_energy;
        SeekBar splitSeekBar = findViewById(R.id.configFBSplitSeekBar);
        splitSeekBar.setProgress(initial_split_threshold * splitSeekBar.getMax() / MAX_SPLIT_THRESHOLD);
        splitSeekBar.setOnSeekBarChangeListener(this);
        split_threshold = initial_split_threshold;
        SeekBar maxAgeSeekBar = findViewById(R.id.configFBMaxAgeSeekBar);
        maxAgeSeekBar.setOnSeekBarChangeListener(this);
        maxAgeSeekBar.setProgress(initial_max_age * maxAgeSeekBar.getMax() / MAX_AGE);
        maxAge = initial_max_age;
        SeekBar saneLightSeekBar = findViewById(R.id.configFBSaneLightSeekBar);
        saneLightSeekBar.setOnSeekBarChangeListener(this);
        saneLightSeekBar.setProgress(initial_sane_light * saneLightSeekBar.getMax() / MAX_SANE_LIGHT);
        sane_light = initial_sane_light;
    }

    public void reportFBConfigData(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.fb_init_energy_key), initenergy);
        editor.putInt(getString(R.string.fb_split_threshold_key), split_threshold);
        editor.putInt(getString(R.string.fb_max_age_key), maxAge);
        editor.putInt(getString(R.string.fb_sane_light_key), sane_light);
        editor.commit();
        Log.w(TAG, "reportFBConfigData: set FB init energy to " + initenergy);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // add lower bound to all so no 0 values
        if (seekBar.getId() == R.id.fbEnergyseekBar) {
            initenergy = (progress * MAX_INIT_ENERGY) / seekBar.getMax() + DEFAULT_INIT_ENERGY / 100;
        }
        if (seekBar.getId() == R.id.configFBSplitSeekBar) {
            split_threshold = progress * MAX_SPLIT_THRESHOLD / seekBar.getMax() + DEFAULT_SPLIT_THRESHOLD / 100;
        }
        if (seekBar.getId() == R.id.configFBMaxAgeSeekBar) {
            maxAge = progress * MAX_AGE / seekBar.getMax() + DEFAULT_AGE / 100;  // no zero age please
        }
        if (seekBar.getId() == R.id.configFBSaneLightSeekBar) {
            sane_light = progress * MAX_SANE_LIGHT / seekBar.getMax() + 1;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStartTrackingTouch: ");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStopTrackingTouch: ");
    }
}
