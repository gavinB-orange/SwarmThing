package com.example.brebner.swarmthing;

// TODO SANE_LIGHT_LEVEL as parameter
// TODO fix post-split overlap
// TODO separate age for FB and GB
// TODO unlimited time switch
// TODO fix bug where rotation to landscape loses EndActivity graph
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
// TODO high score
// TODO DNA exchange on collision with same type.


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


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
}
