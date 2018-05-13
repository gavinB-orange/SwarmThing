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
    public final int DEFAULT_INIT_ENERGY = 8000;

    private int initenergy = DEFAULT_INIT_ENERGY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_grazing_beast);
        SeekBar energySeekBar = (SeekBar)findViewById(R.id.gbEnergyseekBar);
        energySeekBar.setOnSeekBarChangeListener(this);
    }

    public void reportGBConfigData(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.gb_init_energy), initenergy);
        editor.commit();
        Log.w(TAG, "reportGBConfigData: set GB init energy to " + initenergy);
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
