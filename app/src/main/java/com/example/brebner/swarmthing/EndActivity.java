package com.example.brebner.swarmthing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Intent intent = getIntent();
        int nfb = intent.getIntExtra(getString(R.string.final_fb_value_key), 0);
        int ngb = intent.getIntExtra(getString(R.string.final_gb_value_key), 0);
        TextView fbTextView = findViewById(R.id.nFBResultTextView);
        TextView gbTextView = findViewById(R.id.nGBTextView);
        fbTextView.setText("" + nfb);
        gbTextView.setText("" + ngb);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long start = sharedPreferences.getLong(getString(R.string.start_time_key), 0);
        long now = System.currentTimeMillis();
        long seconds = (now - start) / 1000;
        TextView durationTextView = findViewById(R.id.gameDurationValueTextView);
        durationTextView.setText("" + seconds + " seconds");
    }
}
