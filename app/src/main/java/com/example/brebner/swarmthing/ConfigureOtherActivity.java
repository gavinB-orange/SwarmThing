package com.example.brebner.swarmthing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class ConfigureOtherActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public final static int DEFAULT_N_BEASTS = 20;
    public final static int MAX_BEASTS = 100;
    public static final int MAX_RATIO = 20;
    public final static int DEFAULT_RATIO = 8;
    public final static long MAX_TIME = 60 * 60 * 1000;  // 60 minutes
    public final static long DEFAULT_TIME = 5 * 60 * 1000;  // 5 minutes
    public final static long DEFAULT_MIN_TIME = 20 * 1000;  // 20 seconds min run
    public final static boolean DEFAULT_SHOW_FPS = false;
    public final static boolean DEFAULT_SHOW_TIME = true;
    public final static boolean DEFAULT_UNLIMITED_TIME = false;
    public final static boolean DEFAULT_SHOW_ELAPSED_TIME = false;
    public static final boolean DEFAULT_SOUND_EFFECTS_ON = false;

    private static final String TAG = "ConfigureOtherActivity";

    private int nbeasts = DEFAULT_N_BEASTS;
    private int ratio = DEFAULT_RATIO;
    private long max_time = MAX_TIME;
    private boolean unlimited_time;
    private boolean elapsed_time;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_other);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SeekBar nbeastSeekBar = findViewById(R.id.numberBeastSeekBar);
        nbeastSeekBar.setProgress(DEFAULT_N_BEASTS * nbeastSeekBar.getMax() / MAX_BEASTS);
        nbeastSeekBar.setOnSeekBarChangeListener(this);
        SeekBar ratioSeekBar = findViewById(R.id.ratioSeekBar);
        ratioSeekBar.setProgress(DEFAULT_RATIO * ratioSeekBar.getMax() / MAX_RATIO);
        ratioSeekBar.setOnSeekBarChangeListener(this);
        SeekBar timeSeekBar = findViewById(R.id.maxTimeSeekBar);
        timeSeekBar.setProgress((int)(DEFAULT_TIME * timeSeekBar.getMax() / MAX_TIME));
        timeSeekBar.setOnSeekBarChangeListener(this);
        RadioButton showTimeRadioButton = findViewById(R.id.showTimeRadioButton);
        showTimeRadioButton.setChecked(DEFAULT_SHOW_TIME);
        RadioButton showFpsRadioButton = findViewById(R.id.showFpsRadioButton);
        showFpsRadioButton.setChecked(DEFAULT_SHOW_FPS);
        RadioButton showElapsedRadioButton = findViewById(R.id.showElapsedTimeRadioButton);
        showElapsedRadioButton.setChecked(DEFAULT_SHOW_ELAPSED_TIME);
        Switch unlimitedTimeSwitch = findViewById(R.id.unlimitedTimeSwitch);
        unlimitedTimeSwitch.setChecked(DEFAULT_UNLIMITED_TIME);
        RadioButton soundEffectsOnRadioButton = findViewById(R.id.soundOnRadioButton);
        soundEffectsOnRadioButton.setChecked(DEFAULT_SOUND_EFFECTS_ON);

        nbeasts = DEFAULT_N_BEASTS;
        ratio = DEFAULT_RATIO;
        max_time = DEFAULT_TIME;
        unlimited_time = DEFAULT_UNLIMITED_TIME;
    }

    public void doSimDone(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.other_ratio_key), ratio);
        editor.putInt(getString(R.string.other_nbeasts_key), nbeasts);
        editor.putLong(getString(R.string.other_maxtime_key), max_time);
        RadioButton showTimeRadioButton = findViewById(R.id.showTimeRadioButton);
        RadioButton showFpsRadioButton = findViewById(R.id.showFpsRadioButton);
        RadioButton showElapsedRadioButton = findViewById(R.id.showElapsedTimeRadioButton);
        RadioButton soundEffectsOnRadioButton = findViewById(R.id.soundOnRadioButton);
        Switch unlimitedTimeSwitch = findViewById(R.id.unlimitedTimeSwitch);
        // unlimited time => disable time countdown.
        editor.putBoolean(getString(R.string.other_show_time), showTimeRadioButton.isChecked() && ! unlimitedTimeSwitch.isChecked());
        editor.putBoolean(getString(R.string.other_show_fps), showFpsRadioButton.isChecked());
        editor.putBoolean(getString(R.string.other_unlimited_time_key), unlimitedTimeSwitch.isChecked());
        editor.putBoolean(getString(R.string.other_elapsed_time_key), showElapsedRadioButton.isChecked());
        editor.putBoolean(getString(R.string.sound_effects_on_key), soundEffectsOnRadioButton.isChecked());
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
            max_time = DEFAULT_MIN_TIME + (progress * MAX_TIME) / seekBar.getMax();
            Log.d(TAG, "onProgressChanged: max_time = " + max_time);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
