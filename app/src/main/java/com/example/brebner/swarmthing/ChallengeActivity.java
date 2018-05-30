package com.example.brebner.swarmthing;

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

}
