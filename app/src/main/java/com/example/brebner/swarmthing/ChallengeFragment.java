package com.example.brebner.swarmthing;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChallengeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChallengeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChallengeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ChallengeFragment";

    public static final String DESCRIPTION_KEY = "challenge_fragment_description_key";
    public static final String CHOICE_KEY = "challenge_fragment_choice_key";
    private OnFragmentInteractionListener mListener;
    private String description;
    private int choice;

    public ChallengeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance
     *
     * @return A new instance of fragment ChallengeFragment.
     */
    public static ChallengeFragment newInstance(int choice, String description) {
        ChallengeFragment fragment = new ChallengeFragment();
        fragment.description = description;
        fragment.choice = choice;
        Bundle args = new Bundle();
        args.putString(DESCRIPTION_KEY, description);
        args.putInt(CHOICE_KEY, choice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            description = getArguments().getString(DESCRIPTION_KEY);
            choice = getArguments().getInt(CHOICE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        // make this fragment a listener for the button click
        ImageButton choiceButton = (ImageButton)view.findViewById(R.id.challengeChoiceFragmentButton);
        choiceButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.fragmentDescriptionTextView);
        tv.setText(description);
        tv = view.findViewById(R.id.fragmentChoiceTextView);
        tv.setText("" + choice + " : ");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onFragmentInteraction(Uri uri) {
        Log.w(TAG, "onFragmentInteraction: stuff");
    }

    @Override
    public void onClick(View v) {
        ImageButton imageButton = (ImageButton)v;
        imageButton.setImageResource(android.R.drawable.checkbox_on_background);
        // save the choice
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.challenge_choice_key), choice);
        // all challenges currently work with 5 mins, so ...
        editor.putLong(getString(R.string.other_maxtime_key), ChallengeChecker.MINS_5);
        editor.commit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
