package com.example.brebner.swarmthing;

// TODO Complete implementation of config selections
// TODO Connect up config info for GrazingBeast and main preferences.


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public final static String WHOAMI_KEY = "com.example.brebner.swarmthing.WHOAMI";

    public final static int MAXBEASTS = 100;

    private static final String TAG = "MainActivity";

    private Bundle knownConfigInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate: savedInstanceState is not null");
            knownConfigInfo = savedInstanceState;
        }
        else {
            knownConfigInfo = new Bundle();
        }
        Intent intent = getIntent();
        if (intent == null || intent.getAction() == "android.intent.action.MAIN") {
            Log.d(TAG, "onCreate: intent for initial startup");
        }
        else {
            Log.d(TAG, "onCreate: intent receiving config info");
            // coming back from a config
            Bundle updated = intent.getBundleExtra(ConfigureFoodBeast.FB_KEY);
            String whoami = updated.getString(WHOAMI_KEY);
            Log.d(TAG, "onCreate: whoami = " + whoami);
            if (whoami.equals(ConfigureFoodBeast.FB_KEY)) {
                Log.d(TAG, "onCreate: Update from configure FoodBeast");
                int fbie = updated.getInt(ConfigureFoodBeast.FB_INIT_ENERGY);
                knownConfigInfo.putInt(ConfigureFoodBeast.FB_INIT_ENERGY, fbie);
            }
            else {
                Log.d(TAG, "onCreate: unknown whoami");
            }
        }
    }


    public void runOnClick(View view) {
        Log.d(TAG, "runOnClick");
        for (String k: knownConfigInfo.keySet()) {
            Log.d(TAG, "runOnClick: " + k + " = " + knownConfigInfo.get(k));
        }
        EditText et = (EditText)findViewById(R.id.userInputNBeasts);
        String answer = et.getText().toString();
        int number;
        try {
            number = Integer.parseInt(answer);
        }
        catch (NumberFormatException e) {
            Log.d(TAG, "runOnClick: NumberFormatException");
            Toast.makeText(this, "Must be a number - try again!", Toast.LENGTH_LONG).show();
            return;
        }
        if (number <= 0 || number > MAXBEASTS) {
            Log.d(TAG, "runOnClick: number too large");
            Toast.makeText(this, "Must be a number between 1 and 100", Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(TAG, "runOnClick: Have a valid number");
        knownConfigInfo.putInt(SwarmPlaygroundActivity.BEAST_NUMBER_KEY, number);
        // TODO add stuff to hand knownConfigInfo and pass data over
        // probably want to update knownConfigInfo with number of beasts
        // and send the bundle over to SwarmPlaygroupActivity
        Intent playIntent = new Intent(this, SwarmPlaygroundActivity.class);
        playIntent.putExtra(SwarmPlaygroundActivity.CONFIG_BUNDLE_KEY, knownConfigInfo);
        startActivity(playIntent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged: " + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStartTrackingTouch: ");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStopTrackingTouch: ");
    }

    public void doFBConfigButtonClick(View view) {
        Intent intent = new Intent(this, ConfigureFoodBeast.class);
        startActivity(intent);
    }

    public void doPreferencesConfigButtonOnClick(View view) {
    }

    public void doGBConfigButtonOnClick(View view) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putAll(knownConfigInfo);
        super.onSaveInstanceState(outState);
    }
}
