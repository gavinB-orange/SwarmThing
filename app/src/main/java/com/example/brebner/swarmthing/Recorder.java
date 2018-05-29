package com.example.brebner.swarmthing;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

public class Recorder {

    private ArrayList<DataItem> items;

    private static final String TAG = "Recorder";
    private static final int TEXT_SIZE = 40;
    private static final int PADDING = 30;
    private static final int FBCOLOUR = Color.argb(128, 0, 255, 0);
    private static final int NBCOLOUR = Color.argb(255, 255, 0, 0);
    private static final int BKCOLOUR = Color.argb(255, 32, 32, 32);
    private static final int BBCOLOUR = Color.argb(255, 0, 0, 255);
    private static final int AVCOLOUR = Color.argb(128, 200, 32, 32);

    public static final int CULL_SPLIT_SCALE = 2;

    private Context context;
    private int maxnbeasts;
    private int nbeastssum;

    private int challengeChoice;

    Recorder(Context context) {
        this.context = context;
        items = new ArrayList<>();
        maxnbeasts = 0;
        nbeastssum = 0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        challengeChoice = sharedPreferences.getInt(context.getString(R.string.challenge_choice_key), ChallengeActivity.NO_CHALLENGE_SELECTED);
    }

    public void putData(int nfb, int nbeasts, int nsplit, int ncull) {
        Log.d(TAG, "putData:  nfb = " + nfb + " nbeasts = " + nbeasts + " nsplit = " + nsplit + " ncull " + ncull);
        items.add(new DataItem(nfb, nbeasts, CULL_SPLIT_SCALE * nsplit, CULL_SPLIT_SCALE * ncull));
        if (nbeasts > maxnbeasts) {  // keep a running max value for the y axis
            maxnbeasts = nbeasts;
        }
        nbeastssum += nbeasts;
    }

    public Context getContext() {
        return context;
    }

    public int getMaxnbeasts() {
        return maxnbeasts;
    }

    public float getAverageNBeasts() {
        return nbeastssum / items.size();
    }

    public ArrayList<DataItem> getData() {
        return items;
    }

    private Path getBKPath(int rawwidth, int rawheight, int padding) {
        long xrange = items.size();
        int width = rawwidth - 2 * padding;
        int height = rawheight - 2 * padding;
        if ((width <= 0)) throw new AssertionError("width must be > 0");
        if ((height <= 0)) throw new AssertionError("height must be > 0");
        if (xrange <= 0) throw new AssertionError("xrange must be > 0");
        Path path = new Path();
        float xval;
        if (items.size() <= 0) {
            return null;
        }
        for (int i = 0; i < items.size(); i++) {
            xval = (i * width) / xrange + padding;
            // scale using maxnbeasts as that will always be >= nbk
            // and invert yval as y = 0 is the top
            float yval = height - (items.get(i).ncull * height / maxnbeasts) + padding;
            if (i == 0) {
                path.moveTo(xval, yval);
            }
            else {
                path.lineTo(xval, yval);
                path.moveTo(xval, yval);
            }
        }
        return path;
    }

    private Path getBBPath(int rawwidth, int rawheight, int padding) {
        long xrange = items.size();
        int width = rawwidth - 2 * padding;
        int height = rawheight - 2 * padding;
        if ((width <= 0)) throw new AssertionError("width must be > 0");
        if ((height <= 0)) throw new AssertionError("height must be > 0");
        if (xrange <= 0) throw new AssertionError("xrange must be > 0");
        Path path = new Path();
        float xval;
        if (items.size() <= 0) {
            return null;
        }
        for (int i = 0; i < items.size(); i++) {
            xval = (i * width) / xrange + padding;
            // scale using maxnbeasts as that will always be >= nsplit
            // and invert yval as y = 0 is the top
            float yval = height - (items.get(i).nsplit * height / maxnbeasts) + padding;
            if (i == 0) {
                path.moveTo(xval, yval);
            }
            else {
                path.lineTo(xval, yval);
                path.moveTo(xval, yval);
            }
        }
        return path;
    }

    private Path getFBPath(int rawwidth, int rawheight, int padding) {
        long xrange = items.size();
        int width = rawwidth - 2 * padding;
        int height = rawheight - 2 * padding;
        if ((width <= 0)) throw new AssertionError("width must be > 0");
        if ((height <= 0)) throw new AssertionError("height must be > 0");
        if (xrange <= 0) throw new AssertionError("xrange must be > 0");
        Path path = new Path();
        float xval;
        if (items.size() <= 0) {
            return null;
        }
        for (int i = 0; i < items.size(); i++) {
            xval = (i * width) / xrange + padding;
            // scale using maxnbeasts as that will always be >= nfb
            // and invert yval as y = 0 is the top
            float yval = height - (items.get(i).nfb * height / maxnbeasts) + padding;
            if (i == 0) {
                path.moveTo(xval, yval);
            }
            else {
                path.lineTo(xval, yval);
                path.moveTo(xval, yval);
            }
        }
        return path;
    }

    private Path getNBeastsAveragePath(int rawwidth, int rawheight, int padding) {
        long xrange = items.size();
        int width = rawwidth - 2 * padding;
        int height = rawheight - 2 * padding;
        if ((width <= 0)) throw new AssertionError("width must be > 0");
        if ((height <= 0)) throw new AssertionError("height must be > 0");
        if (xrange <= 0) throw new AssertionError("xrange must be > 0");
        Path path = new Path();
        if (items.size() <= 0) {
            return null;
        }
        float yval;
        yval = height - (getAverageNBeasts() * height / maxnbeasts) + padding;
        path.moveTo(padding, yval);
        path.lineTo(width + padding, yval);
        return path;
    }

    private Path getNbeastPath(int rawwidth, int rawheight, int padding) {
        int width = rawwidth - 2 * padding;
        int height = rawheight - 2 * padding;
        if ((width <= 0)) throw new AssertionError(TAG + " getNbeastPath: width must be > 0");
        if ((height <= 0)) throw new AssertionError(TAG + " getNbeastPath: height must be > 0");
        long xrange = items.size();
        if (xrange <= 0) throw new AssertionError(TAG + " getNbestPath: xrange must be > 0");
        // TODO replace larger dump below with the above
        Path path = new Path();
        float xval;
        for (int i = 0; i < items.size(); i++) {
            xval = (i * width) / xrange + padding;
            float yval = height - (items.get(i).nbeasts * height / maxnbeasts) + padding;
            if (i == 0) {
                path.moveTo(xval, yval);
            }
            else {
                path.lineTo(xval, yval);
                // path.moveTo(xval, yval);
            }
        }
        // path.lineTo(xval, 0);
        // path.moveTo(xval, 0);
        // path.lineTo(0, 0);
        //path.close();
        return path;
    }

    private Path getAxes(int width, int height, int padding) {
        Path path = new Path();
        path.moveTo(padding, height - padding);
        path.lineTo(width - padding, height - padding);
        path.moveTo(padding, height - padding);
        path.lineTo(padding, padding);
        return path;
    }

    public Bitmap createBitmapDrawing(int width, int height, long seconds) {
        if (items.size() < 1) {
            AssertionError e = new AssertionError();
            Log.e(TAG, "createBitmapDrawing: items is 0", e );
            throw e;
        }
        Canvas canvas = new Canvas();
        Bitmap bitmap;
        Bitmap srcbitmap;
        // start with an existing bitmap of the right size
        srcbitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_2_50);
        srcbitmap = Bitmap.createScaledBitmap(srcbitmap, width, height, false);
        // make a mutable copy
        bitmap = srcbitmap.copy(srcbitmap.getConfig(), true);
        // set as bitmap for the canvas
        canvas.setBitmap(bitmap);
        // draw stuff
        Paint paint = new Paint();
        canvas.drawColor(Color.argb(255,255 , 255, 255));
        paint.setColor(Color.argb(255, 255, 0, 0));
        paint.setTextSize(TEXT_SIZE);
        // canvas.drawText("" + items.size() + " items", PADDING, height - 3 * PADDING, paint);
        Path nbpath = getNbeastPath(width, height, PADDING);
        Path fbpath = getFBPath(width, height, PADDING);
        Path bkpath = getBKPath(width, height, PADDING);
        Path bbpath = getBBPath(width, height, PADDING);
        // paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        // BK
        paint.setColor(BKCOLOUR);
        if (bkpath != null) {
            canvas.drawPath(bkpath, paint);
        }
        canvas.drawText(context.getString(R.string.beasts_died_title) + CULL_SPLIT_SCALE, PADDING, PADDING * 4, paint);
        // BB
        paint.setColor(BBCOLOUR);
        if (bbpath != null) {
            canvas.drawPath(bbpath, paint);
        }
        canvas.drawText(context.getString(R.string.beasts_born_title) + CULL_SPLIT_SCALE, PADDING, PADDING * 3, paint);
        // FB
        paint.setColor(FBCOLOUR);
        if (fbpath != null) {
            canvas.drawPath(fbpath, paint);
        }
        canvas.drawText(context.getString(R.string.number_fb_title), PADDING, PADDING * 2, paint);
        // NB
        paint.setColor(NBCOLOUR);
        if (nbpath != null) {
            canvas.drawPath(nbpath, paint);
        }
        canvas.drawText(context.getString(R.string.number_beasts_title), PADDING, PADDING, paint);

        paint.setColor(Color.BLACK);
        canvas.drawPath(getAxes(width, height, PADDING), paint);
        canvas.drawText("" + maxnbeasts, 0, PADDING, paint);
        int multiplier = 0;
        long toto = seconds;
        while (toto > 0) {
            multiplier ++;
            toto /= 10;
        }
        canvas.drawText("" + seconds, width - TEXT_SIZE * multiplier, height, paint);
        paint.setColor(AVCOLOUR);
        canvas.drawPath(getNBeastsAveragePath(width, height, PADDING), paint);
        Log.d(TAG, "createBitmapDrawing: returning bitmap");
        return bitmap;
    }

}
