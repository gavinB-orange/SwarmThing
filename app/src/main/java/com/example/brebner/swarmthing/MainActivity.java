package com.example.brebner.swarmthing;

// TODO Capture time sequence of beast numbers and graph at end.
// TODO restart button in EndActivity
// TODO store historical data and display


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public final static String WHOAMI_KEY = "com.example.brebner.swarmthing.WHOAMI";

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
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.w("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
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
