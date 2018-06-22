package com.example.brebner.swarmthing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Switch;

public class ConfigureOtherActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public final static int DEFAULT_N_BEASTS = 20;
    public final static int MAX_BEASTS = 100;
    public static final int MAX_RATIO = 20;
    public final static int DEFAULT_RATIO = 8;
    public final static long MAX_TIME = 60 * 60 * 1000;  // 60 minutes
    public final static long DEFAULT_TIME = 5 * 60 * 1000;  // 5 minutes
    public final static long DEFAULT_MIN_TIME = 60 * 1000;  // 60 seconds min run
    public final static boolean DEFAULT_SHOW_FPS = false;
    public final static boolean DEFAULT_SHOW_TIME = true;
    public final static boolean DEFAULT_UNLIMITED_TIME = false;
    public final static boolean DEFAULT_SHOW_ELAPSED_TIME = false;
    public static final boolean DEFAULT_SOUND_EFFECTS_ON = false;

    private static final String TAG = "ConfigureOtherActivity";

    private int nbeasts = DEFAULT_N_BEASTS;
    private int ratio = DEFAULT_RATIO;
    private long maxTime = MAX_TIME;

    private CheckBox showTimeCheckBox;
    private CheckBox showFpsCheckBox;
    private CheckBox showElapsedCheckBox;
    private CheckBox soundEffectsOnCheckBox;
    private Switch unlimitedTimeSwitch;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_other);
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE);

        int initial_default_n_beasts = sharedPreferences.getInt(getString(R.string.other_nbeasts_key), DEFAULT_N_BEASTS);
        int initial_ratio = sharedPreferences.getInt(getString(R.string.other_ratio_key), DEFAULT_RATIO);
        long initial_max_time = sharedPreferences.getLong(getString(R.string.other_maxtime_key), DEFAULT_TIME);

        boolean initial_show_remaining_time = sharedPreferences.getBoolean(getString(R.string.other_show_time), DEFAULT_SHOW_TIME);
        boolean initial_show_fps = sharedPreferences.getBoolean(getString(R.string.other_show_fps), DEFAULT_SHOW_FPS);
        boolean initial_unlimited_time = sharedPreferences.getBoolean(getString(R.string.other_unlimited_time_key), DEFAULT_UNLIMITED_TIME);
        boolean initial_show_elapsed_time = sharedPreferences.getBoolean(getString(R.string.other_elapsed_time_key), DEFAULT_SHOW_ELAPSED_TIME);
        boolean initial_sound_effects_on = sharedPreferences.getBoolean(getString(R.string.sound_effects_on_key), DEFAULT_SOUND_EFFECTS_ON);

        SeekBar nbeastSeekBar = findViewById(R.id.numberBeastSeekBar);
        nbeastSeekBar.setProgress(initial_default_n_beasts * nbeastSeekBar.getMax() / MAX_BEASTS);
        nbeastSeekBar.setOnSeekBarChangeListener(this);
        nbeasts = initial_default_n_beasts;

        SeekBar ratioSeekBar = findViewById(R.id.ratioSeekBar);
        ratioSeekBar.setProgress(initial_ratio * ratioSeekBar.getMax() / MAX_RATIO);
        ratioSeekBar.setOnSeekBarChangeListener(this);
        ratio = initial_ratio;

        SeekBar timeSeekBar = findViewById(R.id.maxTimeSeekBar);
        timeSeekBar.setProgress((int)(initial_max_time * timeSeekBar.getMax() / MAX_TIME));
        timeSeekBar.setOnSeekBarChangeListener(this);
        maxTime = initial_max_time;

        showTimeCheckBox = findViewById(R.id.showTimeCheckBox);
        showTimeCheckBox.setChecked(initial_show_remaining_time);
        showFpsCheckBox = findViewById(R.id.showFpsCheckBox);
        showFpsCheckBox.setChecked(initial_show_fps);
        showElapsedCheckBox = findViewById(R.id.showElapsedTimeCheckBox);
        showElapsedCheckBox.setChecked(initial_show_elapsed_time);
        unlimitedTimeSwitch = findViewById(R.id.unlimitedTimeSwitch);
        unlimitedTimeSwitch.setChecked(initial_unlimited_time);
        soundEffectsOnCheckBox = findViewById(R.id.soundOnCheckBox);
        soundEffectsOnCheckBox.setChecked(initial_sound_effects_on);

    }

    @SuppressLint("ApplySharedPref")
    public void doSimDone(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.other_ratio_key), ratio);
        editor.putInt(getString(R.string.other_nbeasts_key), nbeasts);
        editor.putLong(getString(R.string.other_maxtime_key), maxTime);
        // unlimited time => disable time countdown.
        editor.putBoolean(getString(R.string.other_show_time), showTimeCheckBox.isChecked() && ! unlimitedTimeSwitch.isChecked());
        editor.putBoolean(getString(R.string.other_show_fps), showFpsCheckBox.isChecked());
        editor.putBoolean(getString(R.string.other_unlimited_time_key), unlimitedTimeSwitch.isChecked());
        editor.putBoolean(getString(R.string.other_elapsed_time_key), showElapsedCheckBox.isChecked());
        editor.putBoolean(getString(R.string.sound_effects_on_key), soundEffectsOnCheckBox.isChecked());
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged: Changed seek bar");
        if (seekBar.getId() == R.id.numberBeastSeekBar) {
            nbeasts = progress * MAX_BEASTS / seekBar.getMax();
            if (nbeasts < 1) {
                nbeasts = 1;  // want at least 1
            }
            Log.d(TAG, "onProgressChanged: nbeasts = " + nbeasts);
        }
        if (seekBar.getId() == R.id.ratioSeekBar) {
            ratio = progress * MAX_RATIO / seekBar.getMax();
            Log.d(TAG, "onProgressChanged: ratio = " + ratio);
        }
        if (seekBar.getId() == R.id.maxTimeSeekBar) {
            maxTime = DEFAULT_MIN_TIME + (progress * MAX_TIME) / seekBar.getMax();
            Log.d(TAG, "onProgressChanged: maxTime = " + maxTime);
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
