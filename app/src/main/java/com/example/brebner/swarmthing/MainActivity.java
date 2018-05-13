package com.example.brebner.swarmthing;

// TODO Complete implementation of config selections
// TODO Connect up config info for GrazingBeast and main preferences.
// TODO implement persistent storage across activities.


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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.beast_number_key), number);
        editor.commit();
        Log.w(TAG, "runOnClick: set beast number to " + number, null);
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
