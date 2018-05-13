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
    public final int DEFAULT_INIT_ENERGY = 5000;
    private int initenergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_food_beast);
        SeekBar energySeekBar = (SeekBar)findViewById(R.id.fbEnergyseekBar);
        energySeekBar.setOnSeekBarChangeListener(this);
    }

    public void reportFBConfigData(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.fb_init_energy), initenergy);
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
