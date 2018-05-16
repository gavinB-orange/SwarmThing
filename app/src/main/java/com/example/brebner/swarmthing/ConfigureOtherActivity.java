package com.example.brebner.swarmthing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

public class ConfigureOtherActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public final static int DEFAULT_MAX_AGE = 20000;
    public final static int DEFAULT_AGE = 8000;
    public final static int DEFAULT_N_BEASTS = 20;
    public final static int DEFAULT_MAX_BEASTS = 100;
    public static final int DEFAULT_MAX_RATIO = 20;
    public final static int DEFAULT_RATIO = 8;
    public final static long DEFAULT_MAX_TIME = 10 * 60 * 1000;  // 10 minutes
    public final static long DEFAULT_TIME = 5 * 60 * 1000;  // 5 minutes
    public final static long DEFAULT_MIN_TIME = 20 * 1000;  // 20 seconds min run

    private static final String TAG = "ConfigureOtherActivity";

    private int maxage = DEFAULT_MAX_AGE;
    private int nbeasts = DEFAULT_N_BEASTS;
    private int ratio = DEFAULT_RATIO;
    private long max_time = DEFAULT_MAX_TIME;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_other);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SeekBar ageSeekBar = (SeekBar)findViewById(R.id.ageBeastSeekBar);
        ageSeekBar.setProgress(DEFAULT_AGE * ageSeekBar.getMax() / DEFAULT_MAX_AGE);
        ageSeekBar.setOnSeekBarChangeListener(this);
        SeekBar nbeastSeekBar = (SeekBar)findViewById(R.id.numberBeastSeekBar);
        nbeastSeekBar.setProgress(DEFAULT_N_BEASTS * nbeastSeekBar.getMax() / DEFAULT_MAX_BEASTS);
        nbeastSeekBar.setOnSeekBarChangeListener(this);
        SeekBar ratioSeekBar = (SeekBar)findViewById(R.id.ratioSeekBar);
        ratioSeekBar.setProgress(DEFAULT_RATIO * ratioSeekBar.getMax() / DEFAULT_MAX_RATIO);
        ratioSeekBar.setOnSeekBarChangeListener(this);
        SeekBar timeSeekBar = (SeekBar)findViewById(R.id.maxTimeSeekBar);
        timeSeekBar.setProgress((int)(DEFAULT_TIME * timeSeekBar.getMax() / DEFAULT_MAX_TIME));
        timeSeekBar.setOnSeekBarChangeListener(this);
        maxage = DEFAULT_AGE;
        nbeasts = DEFAULT_N_BEASTS;
        ratio = DEFAULT_RATIO;
        max_time = DEFAULT_TIME;
    }

    public void doSimDone(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.other_ratio_key), ratio);
        editor.putInt(getString(R.string.other_maxage_key), maxage);
        editor.putInt(getString(R.string.other_nbeasts_key), nbeasts);
        editor.putLong(getString(R.string.other_maxtime_key), max_time);
        RadioButton showTimeRadioButton = findViewById(R.id.showTimeRadioButton);
        RadioButton showFpsRadioButton = findViewById(R.id.showFpsRadioButton);
        editor.putBoolean(getString(R.string.other_show_time), showTimeRadioButton.isChecked());
        editor.putBoolean(getString(R.string.other_show_fps), showFpsRadioButton.isChecked());
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged: Changed seek bar");
        if (seekBar.getId() == R.id.ageBeastSeekBar) {
            maxage = seekBar.getProgress() * DEFAULT_MAX_AGE / seekBar.getMax();
            Log.d(TAG, "onProgressChanged: maxage = " + maxage);
        }
        if (seekBar.getId() == R.id.numberBeastSeekBar) {
            nbeasts = seekBar.getProgress() * DEFAULT_MAX_BEASTS / seekBar.getMax();
            if (nbeasts < 1) {
                nbeasts = 1;  // want at least 1
            }
            Log.d(TAG, "onProgressChanged: nbeasts = " + nbeasts);
        }
        if (seekBar.getId() == R.id.ratioSeekBar) {
            ratio = seekBar.getProgress() * DEFAULT_MAX_RATIO / seekBar.getMax();
            Log.d(TAG, "onProgressChanged: ratio = " + ratio);
        }
        if (seekBar.getId() == R.id.maxTimeSeekBar) {
            max_time = DEFAULT_MIN_TIME + (seekBar.getProgress() * DEFAULT_MAX_TIME) / seekBar.getMax();
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
