package com.example.brebner.swarmthing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Beast extends DrawableThing {

    private Bitmap bitmap;

    public Beast(int x, int y, Context context) {
        super();
        this.xpos = x;
        this.ypos = y;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beast_1);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, xpos, ypos, paint);
    }
}
