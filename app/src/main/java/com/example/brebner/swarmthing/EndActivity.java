package com.example.brebner.swarmthing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        TextView paramsTextView = findViewById(R.id.paramSummaryTextView);
        String paramsinfo = "Number beasts = " + sharedPreferences.getInt(getString(R.string.other_nbeasts_key), ConfigureOtherActivity.DEFAULT_N_BEASTS) + "\n" +
                            "Ratio value = " + sharedPreferences.getInt(getString(R.string.other_ratio_key) + "/" + ConfigureOtherActivity.DEFAULT_MAX_RATIO, ConfigureOtherActivity.DEFAULT_RATIO) + "\n" +
                            "Max beast age = " + sharedPreferences.getInt(getString(R.string.other_maxage_key), ConfigureOtherActivity.DEFAULT_AGE) + "\n" +
                            "FoodBeast energy = " + sharedPreferences.getInt(getString(R.string.fb_init_energy_key), ConfigureFoodBeast.DEFAULT_INIT_ENERGY) + "\n" +
                            "FoodBeast split = " + sharedPreferences.getInt(getString(R.string.fb_split_threshold_key), ConfigureFoodBeast.DEFAULT_SPLIT_THRESHOLD) + "\n" +
                            "GrazingBeast energy = " + sharedPreferences.getInt(getString(R.string.gb_init_energy_key), ConfigureGrazingBeastActivity.DEFAULT_INIT_ENERGY) + "\n" +
                            "GrazingBeast split = " + sharedPreferences.getInt(getString(R.string.gb_split_threshold_key), ConfigureGrazingBeastActivity.DEFAULT_SPLIT_THRESHOLD);
        paramsTextView.setText(paramsinfo);

    }

    public void doPlayAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
