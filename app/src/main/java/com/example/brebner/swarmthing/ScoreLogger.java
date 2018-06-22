package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidParameterException;

public class ScoreLogger {

    public final static String SCORE_FILE = "swarm_thing_score_history";

    private static final String TAG = "ScoreLogger";

    private Context context;

    // access to shared preferences for overall score data
    private SharedPreferences sharedPreferences;

    public ScoreLogger(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preference_name), Context.MODE_PRIVATE);
    }

    public void add_score_data(long timestamp, int challenge, int score) {
        /*
        Adds info from a run to the persistent storage.
        Also updates the overall_score which is stored in the shared preferences.
        long timestamp : the timestamp from the (end of) the run.
        int challenge : an int representing which challenge was chose.
        int score : the score for the run.
        */
        Log.d(TAG, "add_score_data: ts = " + timestamp + " challenge = " + challenge + " score = " + score);
        int total = sharedPreferences.getInt(context.getString(R.string.overall_score_key), 0);
        total += score;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.overall_score_key), total);
        editor.commit();
        String line = String.format("%tc %d %d\n", timestamp, challenge, score);
        PrintStream printStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(context.getFilesDir(), SCORE_FILE);
            fileOutputStream = new FileOutputStream(file, true);
            printStream = new PrintStream(fileOutputStream);
            printStream.print(line);
            printStream.flush();
        }
        catch (IOException e) {
            Log.e(TAG, "add_score_data: ", e);
        }
        finally {
            if (printStream != null) {
                printStream.close();
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch (IOException e) {
                    Log.e(TAG, "add_score_data: exception during fileOutputStream close", e);
                }
            }
        }
    }

    public String getOverallScore() {
        return String.format("%d", sharedPreferences.getInt(context.getString(R.string.overall_score_key), 0));
    }

    private String getDescription(int which) {
        if (which < 0) {
            return context.getString(R.string.challenge_none_description);
        }
        if (which == ChallengeActivity.CHALLENGE_MOST_DEAD) {
            return context.getString(R.string.challenge_most_dead_5m_description);
        }
        if (which == ChallengeActivity.CHALLENGE_MOST_BORN){
            return context.getString(R.string.challenge_most_born_5m_description);
        }
        if (which == ChallengeActivity.CHALLENGE_MOST_BEAST_ITERATIONS) {
            return context.getString(R.string.challenge_most_beast_iterations_5m_description);
        }
        if (which == ChallengeActivity.CHALLENGE_MOST_PROTECTED) {
            return context.getString(R.string.challenge_most_protected_5m_description);
        }
        if (which == ChallengeActivity.CHALLENGE_PHOENIX) {
            return context.getString(R.string.challenge_phoenix_5m_description);
        }
        throw new InvalidParameterException("Unknown choice provided");
    }

    public String get_all_score_data() {
        File file = new File(context.getFilesDir(), SCORE_FILE);
        if (! file.exists()) {
            return "No Scores Saved";
        }
        StringBuilder results = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                String challengenumberstr = parts[parts.length - 2];
                int which = -1;
                try {
                    which = Integer.parseInt(challengenumberstr);
                }
                catch (NumberFormatException e){
                    Log.e(TAG, "get_all_score_data: ", e);
                    throw e;
                }
                parts[parts.length - 2] = getDescription(which);
                StringBuilder sb = new StringBuilder();
                for (String p: parts) {
                    sb.append(p);
                    sb.append(" ");
                }
                results.append(sb.toString() + "\n");
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            Log.e(TAG, "get_all_score_data: ", e);
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    Log.e(TAG, "get_all_score_data: exception closing bufferedReader", e);
                }
            }
        }
        return results.toString();
    }

    public void reset_score_data() {
        File file = new File(context.getFilesDir(), SCORE_FILE);
        file.delete();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.overall_score_key), 0);
        editor.commit();
    }
}
