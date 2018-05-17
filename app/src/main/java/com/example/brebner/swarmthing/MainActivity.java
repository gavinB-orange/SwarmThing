package com.example.brebner.swarmthing;

// TODO fix post-split overlap
// TODO FB energy formula needs settings etc.
// TODO store historical data and display
// TODO Make beasts "walk"
// TODO User interaction - fingerBeast!
// TODO Moving sun?
// TODO allow user to create barriers.
// TODO challenges :
// TODO    most creatures killed in 5 mins
// TODO    most creatures born in 5 mins.
// TODO    most iterations in beast number over 5 mins
// TODO    smallest number of creatures killed in 5 mins
// TODO    skirting death - the lower trough of numbers.
// TODO high score
// TODO DNA exchange on collision with same type.


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public final static int MAXBEASTS = 100;

    private static final String TAG = "MainActivity";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }


    public void runOnClick(View view) {
        Log.d(TAG, "runOnClick");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(getString(R.string.start_time_key), System.currentTimeMillis());
        editor.commit();
        Intent playIntent = new Intent(this, SwarmPlaygroundActivity.class);
        startActivity(playIntent);
    }

    public void doFBConfigButtonClick(View view) {
        Intent intent = new Intent(this, ConfigureFoodBeast.class);
        startActivity(intent);
    }

    public void doPreferencesConfigButtonOnClick(View view) {
        Intent intent = new Intent(this, ConfigureOtherActivity.class);
        startActivity(intent);
    }

    public void doGBConfigButtonOnClick(View view) {
        Intent intent = new Intent(this, ConfigureGrazingBeastActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("ApplySharedPref")
    public void resetAllParams(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // other
        editor.putInt(getString(R.string.other_ratio_key), ConfigureOtherActivity.DEFAULT_RATIO);
        editor.putInt(getString(R.string.other_nbeasts_key), ConfigureOtherActivity.DEFAULT_N_BEASTS);
        editor.putLong(getString(R.string.other_maxtime_key), ConfigureOtherActivity.DEFAULT_TIME);
        editor.putBoolean(getString(R.string.other_show_time), ConfigureOtherActivity.DEFAULT_SHOW_TIME);
        editor.putBoolean(getString(R.string.other_show_fps), ConfigureOtherActivity.DEFAULT_SHOW_FPS);
        // grazing
        editor.putInt(getString(R.string.gb_init_energy_key), ConfigureGrazingBeastActivity.DEFAULT_INIT_ENERGY);
        editor.putInt(getString(R.string.gb_split_threshold_key), ConfigureGrazingBeastActivity.DEFAULT_SPLIT_THRESHOLD);
        editor.putInt(getString(R.string.gb_max_age_key), ConfigureGrazingBeastActivity.DEFAULT_AGE);
        // food
        editor.putInt(getString(R.string.fb_init_energy_key), ConfigureFoodBeast.DEFAULT_INIT_ENERGY);
        editor.putInt(getString(R.string.fb_split_threshold_key), ConfigureFoodBeast.DEFAULT_SPLIT_THRESHOLD);
        editor.putInt(getString(R.string.fb_max_age_key), ConfigureFoodBeast.DEFAULT_AGE);
        editor.putInt(getString(R.string.fb_sane_light_key), ConfigureFoodBeast.DEFAULT_SANE_LIGHT);
        // and now commit
        editor.commit();
        Toast.makeText(this, "Parameters Reset!", Toast.LENGTH_LONG).show();

    }
}
