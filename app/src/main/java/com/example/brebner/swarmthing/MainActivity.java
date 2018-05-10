package com.example.brebner.swarmthing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final int MAXBEASTS = 100;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent playIntent = new Intent(this, SwarmPlaygroundActivity.class);
        playIntent.putExtra(SwarmPlaygroundActivity.BEAST_NUMBER_KEY, number);
        startActivity(playIntent);
    }
}
