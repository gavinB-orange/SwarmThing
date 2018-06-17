package com.example.brebner.swarmthing;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ChallengeActivity extends AppCompatActivity implements ChallengeFragment.OnFragmentInteractionListener {

    private static final String TAG = "ChallengeActivity";

    public static final int NO_CHALLENGE_SELECTED = -1;
    String challengeNoneDescR;
    public static final int N_CHALLENGES = 5;
    public static final int CHALLENGE_MOST_DEAD = 0;
    String challengeMostDeadDescR;
    public static final int CHALLENGE_MOST_BORN = 1;
    String challengeMostBornDescR;
    public static final int CHALLENGE_MOST_BEAST_ITERATIONS = 2;
    String challengeMostBeastIterationsDescR;
    public static final int CHALLENGE_MOST_PROTECTED= 3;
    String challengeMostProtectedDescR;
    public static final int CHALLENGE_PHOENIX= 4;
    String challengePhoenixDescR;

    private String[] c_descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        challengeNoneDescR = getString(R.string.challenge_none_description);
        challengeMostDeadDescR = getString(R.string.challenge_most_dead_5m_description);
        challengeMostBornDescR = getString(R.string.challenge_most_born_5m_description);
        challengeMostBeastIterationsDescR = getString(R.string.challenge_most_beast_iterations_5m_description);
        challengeMostProtectedDescR = getString(R.string.challenge_most_protected_5m_description);
        challengePhoenixDescR = getString(R.string.challenge_phoenix_5m_description);
        c_descriptions = new String[N_CHALLENGES];
        c_descriptions[CHALLENGE_MOST_DEAD] = challengeMostDeadDescR;
        c_descriptions[CHALLENGE_MOST_BORN] = challengeMostBornDescR;
        c_descriptions[CHALLENGE_MOST_BEAST_ITERATIONS] = challengeMostBeastIterationsDescR;
        c_descriptions[CHALLENGE_MOST_PROTECTED] = challengeMostProtectedDescR;
        c_descriptions[CHALLENGE_PHOENIX] = challengePhoenixDescR;
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
