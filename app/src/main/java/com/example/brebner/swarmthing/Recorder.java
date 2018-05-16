package com.example.brebner.swarmthing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;

public class Recorder {

    private ArrayList<DataItem> items;

    private static final String TAG = "Recorder";
    private static final int TEXT_SIZE = 40;
    private static final int PADDING = 30;
    private static final int FBCOLOUR = Color.argb(128, 0, 255, 0);
    private static final int NBCOLOUR = Color.argb(255, 255, 0, 0);

    private int maxnbeasts;

    Recorder() {
        items = new ArrayList<>();
        maxnbeasts = 0;
    }

    public void putData(long cycle, int nfb, int nbeasts) {
        Log.d(TAG, "putData: cycle = " + cycle + " nfb = " + nfb + " nbeasts = " + nbeasts);
        items.add(new DataItem(cycle, nfb, nbeasts));
        if (nbeasts > maxnbeasts) {  // keep a running max value for the y axis
            maxnbeasts = nbeasts;
        }
    }

    private Path getFBPath(int rawwidth, int rawheight, int padding) {
        long xstart = items.get(0).getCycle();
        long xend = items.get(items.size() - 1).getCycle();
        long xrange = xend - xstart;
        int width = rawwidth - 2 * padding;
        int height = rawheight - 2 * padding;
        if ((width <= 0)) throw new AssertionError();
        if ((height <= 0)) throw new AssertionError();
        Path path = new Path();
        float xval;
        if (items.size() <= 0) {
            return null;
        }
        for (int i = 0; i < items.size(); i++) {
            xval = (items.get(i).cycle - xstart) * width / xrange + padding;
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
        // path.lineTo(xval, 0);
        // path.moveTo(xval, 0);
        // path.lineTo(0, 0);
        // path.close();
        return path;

    }

    private Path getNbeastPath(int rawwidth, int rawheight, int padding) {
        int width = rawwidth - 2 * padding;
        int height = rawheight - 2 * padding;
        if ((width <= 0)) throw new AssertionError();
        if ((height <= 0)) throw new AssertionError();
        long xstart = items.get(0).getCycle();
        long xend = items.get(items.size() - 1).getCycle();
        long xrange = xend - xstart;
        Path path = new Path();
        float xval;
        if (xrange == 0) {
            AssertionError e = new AssertionError();
            Log.w(TAG, "getNbeastPath: xend = " + xend, null);
            Log.w(TAG, "getNbeastPath: xstart = " + xstart, null);
            Log.e(TAG, "getNbeastPath: xrange is 0!", e);;
            throw e;
        }
        for (int i = 0; i < items.size(); i++) {
            xval = (items.get(i).cycle - xstart) * width / xrange + padding;
            float yval = height - (items.get(i).nbeasts * height / maxnbeasts) + padding;
            if (i == 0) {
                path.moveTo(xval, yval);
            }
            else {
                path.lineTo(xval, yval);
                path.moveTo(xval, yval);
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

    public Bitmap createBitmapDrawing(Context context, int width, int height, long seconds) {
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
        canvas.drawText("" + items.size() + " items", PADDING, height - 3 * PADDING, paint);
        Path nbpath = getNbeastPath(width, height, PADDING);
        Path fbpath = getFBPath(width, height, PADDING);
        // paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(FBCOLOUR);
        if (fbpath != null) {
            canvas.drawPath(fbpath, paint);
        }
        canvas.drawText("FoodBeast numbers", PADDING, height - PADDING * 2, paint);
        paint.setColor(NBCOLOUR);
        if (nbpath != null) {
            canvas.drawPath(nbpath, paint);
        }
        canvas.drawText("Overall Beast numbers", PADDING, height - PADDING, paint);
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
        Log.d(TAG, "createBitmapDrawing: returning bitmap");
        return bitmap;
    }

}
