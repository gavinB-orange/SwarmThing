package com.example.brebner.swarmthing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class ConfigureGrazingBeastActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    // TODO add input for gb_split_threshold

    private static final String TAG = "ConfigureGrazingBeast";
    public static final int DEFAULT_INIT_ENERGY = 8000;
    public static final int MAX_INIT_ENERGY = 12000;
    public static final int DEFAULT_SPLIT_THRESHOLD = 10;
    public static final int MAX_SPLIT_THRESHOLD = 40;
    public static final int MAX_AGE = 20000;
    public static final int DEFAULT_AGE = 8000;

    private int initenergy;
    private int split_threshold;
    private int max_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_grazing_beast);

        SeekBar energySeekBar = findViewById(R.id.gbEnergyseekBar);
        energySeekBar.setProgress(DEFAULT_INIT_ENERGY * energySeekBar.getMax() / MAX_INIT_ENERGY);
        energySeekBar.setOnSeekBarChangeListener(this);
        initenergy = DEFAULT_INIT_ENERGY;

        SeekBar splitSeekBar = findViewById(R.id.configGBSplitSeekBar);
        splitSeekBar.setProgress(DEFAULT_SPLIT_THRESHOLD * splitSeekBar.getMax() / MAX_SPLIT_THRESHOLD);
        splitSeekBar.setOnSeekBarChangeListener(this);
        split_threshold = DEFAULT_SPLIT_THRESHOLD;

        SeekBar maxAgeSeekBar = findViewById(R.id.configGBMaxAgeSeekBar);
        maxAgeSeekBar.setProgress(DEFAULT_AGE * maxAgeSeekBar.getMax() / MAX_AGE);
        maxAgeSeekBar.setOnSeekBarChangeListener(this);
        max_age = DEFAULT_AGE;
    }

    public void reportGBConfigData(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.gb_init_energy_key), initenergy);
        Log.d(TAG, "reportGBConfigData: set GB init energy to " + initenergy);
        editor.putInt(getString(R.string.gb_split_threshold_key), split_threshold);
        Log.d(TAG, "reportGBConfigData: set gb_split_threshold to " + split_threshold);
        editor.putInt(getString(R.string.gb_max_age_key), max_age);
        Log.d(TAG, "reportGBConfigData: set gb_max_age to " + max_age);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.gbEnergyseekBar) {
            initenergy = (progress * MAX_INIT_ENERGY) / seekBar.getMax();
        }
        if (seekBar.getId() == R.id.configGBSplitSeekBar) {
            split_threshold = (progress * MAX_SPLIT_THRESHOLD) / seekBar.getMax();
        }
        if (seekBar.getId() == R.id.configGBMaxAgeSeekBar) {
            max_age = (progress * MAX_AGE) / seekBar.getMax() + MAX_AGE / 10;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
