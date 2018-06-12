package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class ScoreLogger {

    public final static String SCORE_FILE = "swarm_thing_score_history";

    private static final String TAG = "ScoreLogger";

    private Context context;

    // access to shared preferences for overall score data
    private SharedPreferences sharedPreferences;

    public ScoreLogger(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
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
        try {
            File file = new File(context.getFilesDir(), SCORE_FILE);
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.print(line);
            fileOutputStream.close();
        }
        catch (IOException e) {
            Log.e(TAG, "add_score_data: ", e);
        }
    }

    public String getOverallScore() {
        return String.format("%d", sharedPreferences.getInt(context.getString(R.string.overall_score_key), 0));
    }

    public String get_all_score_data() {
        File file = new File(context.getFilesDir(), SCORE_FILE);
        if (! file.exists()) {
            return "No Scores Saved";
        }
        StringBuilder results = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // TODO process line to swap challenge name for int value. Not storing like
                // TODO that to save file space.
                results.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            Log.e(TAG, "get_all_score_data: ", e);
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
