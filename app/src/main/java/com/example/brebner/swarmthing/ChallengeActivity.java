package com.example.brebner.swarmthing;

// TODO challenges :
// TODO    most creatures killed in 5 mins
// TODO    most creatures born in 5 mins.
// TODO    most iterations in beast number over 5 mins
// TODO    smallest number of creatures killed in 5 mins
// TODO    skirting death - the lower trough of numbers.

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChallengeActivity extends AppCompatActivity implements ChallengeFragment.OnFragmentInteractionListener {

    private static final String TAG = "ChallengeActivity";

    public static final int NO_CHALLENGE_SELECTED = -1;
    public static final String CHALLENGE_NONE_DESC = "No challenge selected";
    public static final int N_CHALLENGES = 5;
    public static final int CHALLENGE_MOST_DEAD = 0;
    public static final String CHALLENGE_MOST_DEAD_DESC = "Most beasts killed in 5 minutes";
    public static final int CHALLENGE_MOST_BORN = 1;
    public static final String CHALLENGE_MOST_BORN_DESC = "Most beasts born in 5 minutes";
    public static final int CHALLENGE_MOST_BEAST_ITERATIONS = 2;
    public static final String CHALLENGE_MOST_BEAST_ITERATIONS_DESC = "Most peaks of beast numbers in 5 minutes";
    public static final int CHALLENGE_MOST_PROTECTED= 3;
    public static final String CHALLENGE_MOST_PROTECTED_DESC = "Least beasts killed in 5 minutes";
    public static final int CHALLENGE_PHOENIX= 4;
    public static final String CHALLENGE_PHOENIX_DESC = "Recovery from the lowest beast number in 5 mins";

    private String[] c_descriptions;
    private int choice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        c_descriptions = new String[N_CHALLENGES];
        c_descriptions[CHALLENGE_MOST_DEAD] = CHALLENGE_MOST_DEAD_DESC;
        c_descriptions[CHALLENGE_MOST_BORN] = CHALLENGE_MOST_BORN_DESC;
        c_descriptions[CHALLENGE_MOST_BEAST_ITERATIONS] = CHALLENGE_MOST_BEAST_ITERATIONS_DESC;
        c_descriptions[CHALLENGE_MOST_PROTECTED] = CHALLENGE_MOST_PROTECTED_DESC;
        c_descriptions[CHALLENGE_PHOENIX] = CHALLENGE_PHOENIX_DESC;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        ft = fm.beginTransaction();
        String tagbase = "Fragment";
        for (int i = 0; i < N_CHALLENGES; i++){
            Log.d(TAG, "onCreate: adding fragment " + i + " with description " + c_descriptions[i]);
            Fragment frag = ChallengeFragment.newInstance(i, c_descriptions[i]);
            ft.add(R.id.fragmentPlaceLayout, frag, tagbase + i);
        }
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.w(TAG, "onFragmentInteraction: stuff");
    }

    public void validateChallengeChoice(View view) {
        EditText editText = findViewById(R.id.challengeChoiceEditText);
        String rawinput = editText.getText().toString();
        try {
            choice = Integer.parseInt(rawinput);
        }
        catch (NumberFormatException e) {
            Toast.makeText(this, "Must be a valid number", Toast.LENGTH_LONG).show();
            return;
        }
        if (choice >= N_CHALLENGES) {
            Toast.makeText(this, "Please choose a valid challenge", Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.challenge_choice_key), choice);
        // all challenges currently work with 5 mins, so ...
        editor.putLong(getString(R.string.other_maxtime_key), ChallengeChecker.MINS_5);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
