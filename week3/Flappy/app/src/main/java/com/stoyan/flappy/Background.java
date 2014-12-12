package com.stoyan.flappy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by Stoyan on 11/12/2014.
 */
public class Background {
    private Bitmap bitmap;
    private float x;

    public Background(Bitmap bitmap, float x) {
        this.bitmap = bitmap;
        this.x = x;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float value) {
        this.x = value;
    }

    public void moveLeft(float value) {
        this.x -= value;
    }

    protected void draw(Canvas canvas) {
        canvas.drawBitmap(getBitmap(), x, 0, null);
    }
}
