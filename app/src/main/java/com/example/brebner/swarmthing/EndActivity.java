package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.Locale;


public class EndActivity extends AppCompatActivity {

    private static final String TAG = "EndActivity";
    private ScoreLogger scoreLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Intent intent = getIntent();
        int nfb = intent.getIntExtra(getString(R.string.final_fb_value_key), 0);
        int ngb = intent.getIntExtra(getString(R.string.final_gb_value_key), 0);
        boolean timeup = intent.getBooleanExtra(getString(R.string.final_time_up_key), false);
        TextView titleTextView = findViewById(R.id.endActivityTitleTextView);
        if (timeup) {
            titleTextView.setText(R.string.endActivityTimeUpTitle);
        }
        else {
            titleTextView.setText(R.string.endActivityGameOverTitle);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_name), Context.MODE_PRIVATE);
        long start = sharedPreferences.getLong(getString(R.string.start_time_key), 0);
        long now = System.currentTimeMillis();
        long seconds = (now - start) / 1000;
        TextView durationTextView = findViewById(R.id.gameDurationValueTextView);
        durationTextView.setText(String.format(Locale.UK, "%d seconds", seconds));
        TextView paramsTextView = findViewById(R.id.paramSummaryTextView);
        String paramsinfo = "Number beasts = " + sharedPreferences.getInt(getString(R.string.other_nbeasts_key), ConfigureOtherActivity.DEFAULT_N_BEASTS) + "\n" +
                            "Ratio value = " + sharedPreferences.getInt(getString(R.string.other_ratio_key), ConfigureOtherActivity.DEFAULT_RATIO) + "\n" +
                            "Max FB age = " + sharedPreferences.getInt(getString(R.string.fb_max_age_key), ConfigureFoodBeast.DEFAULT_AGE) + "\n" +
                            "Max GB age = " + sharedPreferences.getInt(getString(R.string.gb_max_age_key), ConfigureGrazingBeastActivity.DEFAULT_AGE) + "\n" +
                            "FoodBeast energy = " + sharedPreferences.getInt(getString(R.string.fb_init_energy_key), ConfigureFoodBeast.DEFAULT_INIT_ENERGY) + "\n" +
                            "FoodBeast split = " + sharedPreferences.getInt(getString(R.string.fb_split_threshold_key), ConfigureFoodBeast.DEFAULT_SPLIT_THRESHOLD) + "\n" +
                            "GrazingBeast energy = " + sharedPreferences.getInt(getString(R.string.gb_init_energy_key), ConfigureGrazingBeastActivity.DEFAULT_INIT_ENERGY) + "\n" +
                            "GrazingBeast split = " + sharedPreferences.getInt(getString(R.string.gb_split_threshold_key), ConfigureGrazingBeastActivity.DEFAULT_SPLIT_THRESHOLD) + "\n";
        paramsTextView.setText(paramsinfo);
        // add graph
        ImageView imageView = findViewById(R.id.graphImageView);
        Bitmap graph = null;
        try {
            graph = BitmapFactory.decodeStream(this.openFileInput(this.getString(R.string.recorder_graph_file_name)));
            imageView.setImageBitmap(graph);
            Log.d(TAG, "onCreate: graph image injected to ImageView");
            this.deleteFile(this.getString(R.string.recorder_graph_file_name));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "onCreate: failed to read file", e);
        }
        if (graph == null) {
            Log.e(TAG, "onCreate: Graph is null!!!", null);
        }
        else {
            imageView.setImageBitmap(graph);
        }
        String challengeResult = intent.getStringExtra(getString(R.string.challenge_result_key));
        TextView challengeResultTextView = findViewById(R.id.challengeResultValue);
        challengeResultTextView.setText(challengeResult);
        TextView challengeResultPointsTextView = findViewById(R.id.challengeResultPointsTextView);
        int points = intent.getIntExtra(getString(R.string.challenge_result_points_key), 0);
        challengeResultPointsTextView.setText(String.format("%d", points));
        scoreLogger = new ScoreLogger(this);
        scoreLogger.add_score_data(System.currentTimeMillis(), sharedPreferences.getInt(getString(R.string.challenge_choice_key), ChallengeActivity.NO_CHALLENGE_SELECTED), points);
        TextView overallScoreTextView = findViewById(R.id.overallScoreValueTextView);
        overallScoreTextView.setText(scoreLogger.getOverallScore());
    }

    public void doPlayAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void doShowHistory(View view) {
        Intent intent = new Intent(this, ShowHistoryActivity.class);
        startActivity(intent);
    }
}
