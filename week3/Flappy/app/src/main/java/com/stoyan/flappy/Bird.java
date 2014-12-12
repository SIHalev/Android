package com.stoyan.flappy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


//public class Bird implements GameClock.GameClockListener{
public class Bird {
    private Bitmap bitmap;
    private float x;
    private float y;

    public Bird(Bitmap bitmap,float x,float y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    /*@Override
    public void onGameEvent(GameEvent gameEvent) {
        //ViewViewViewsadfasdfVasldkfjsadfLog.v("BIRD", "is free");
        //invalidate();
    }*/

    public void jump() {
        moveUp(4); //TODO: make is a const
    }

    public void moveRight(float value) {
        this.x += value;
    }

    public void moveLeft(float value) {
        this.x -= value;
    }

    public void moveUp(float value) {
        this.y -= value;
    }

    public void moveLDown(float value) {
        this.y += value;
    }


    protected void draw(Canvas canvas) {
        canvas.drawBitmap(getBitmap(), getX(), getY(), null);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }
}
