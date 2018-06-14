package com.example.brebner.swarmthing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShowHistoryActivity extends AppCompatActivity {

    ScoreLogger scoreLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);
        scoreLogger = new ScoreLogger(this);
        TextView textView = findViewById(R.id.historyDataGoesHereTextView);
        textView.setText(scoreLogger.get_all_score_data());
    }

    public void doHistoryPlayAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void doResetScores(View view) {
        scoreLogger.reset_score_data();
        Toast.makeText(this, "Scores reset", Toast.LENGTH_LONG).show();
        TextView textView = findViewById(R.id.historyDataGoesHereTextView);
        textView.setText(scoreLogger.get_all_score_data());
    }
}
