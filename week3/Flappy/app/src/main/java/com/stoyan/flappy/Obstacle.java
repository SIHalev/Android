package com.stoyan.flappy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;

public class Obstacle {
    private Bitmap bitmap;

    private PointF topLeftPoint;
    private PointF topRightPoint;
    private PointF bottomLeftPoint;
    private PointF bottomRightPoint;

    //private final float pipeGap = 100;
    private final float OBSTACLE_WIDTH = 50;

    public Obstacle(Bitmap bitmap, float x, float bottomLeftY, float topLeftY,  boolean mirrored) {
        this.topLeftPoint = new PointF(x, topLeftY);
        this.bottomLeftPoint = new PointF(x, bottomLeftY);
        this.topRightPoint = new PointF(x + OBSTACLE_WIDTH, topLeftY);
        this.bottomRightPoint = new PointF(x + OBSTACLE_WIDTH, bottomLeftY);

        //Log.v("topLeftPoint:", topLeftPoint.x + " " + topLeftPoint.y);
        //Log.v("topRightPoint:", topRightPoint.x + " " + topRightPoint.y);
        //Log.v("bottomLeftPoint:", bottomLeftPoint.x + " " + bottomLeftPoint.y);
        //Log.v("bottomRightPoint:", bottomRightPoint.x + " " + bottomRightPoint.y);

        this.bitmap = resizedBitmap(bitmap, OBSTACLE_WIDTH, bottomLeftY - topLeftY);
        if(mirrored) {
            this.bitmap = rotateBitmapBy180(this.bitmap);
        }

    }

    public Bitmap resizedBitmap(Bitmap bitmap, float newWidth, float newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth =  newWidth / width;
        float scaleHeight = newHeight / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }

    public Bitmap rotateBitmapBy180(Bitmap bitmap) { // TODO: make one method with resize;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(180);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public void recycleBitmap() {
        if (this.bitmap != null) {
            this.bitmap.recycle();
            this.bitmap = null;
        }
    }


    protected void draw(Canvas canvas) {

        canvas.drawBitmap(getBitmap(), getTopLeftPoint().x, getTopLeftPoint().y, null);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public PointF getTopLeftPoint() {
        return topLeftPoint;
    }

    public void setTopLeftPoint(PointF topLeftPoint) {
        this.topLeftPoint = topLeftPoint;
    }

    public PointF getTopRightPoint() {
        return topRightPoint;
    }

    public void setTopRightPoint(PointF topRightPoint) {
        this.topRightPoint = topRightPoint;
    }

    public PointF getBottomLeftPoint() {
        return bottomLeftPoint;
    }

    public void setBottomLeftPoint(PointF bottomLeftPoint) {
        this.bottomLeftPoint = bottomLeftPoint;
    }

    public PointF getBottomRightPoint() {
        return bottomRightPoint;
    }

    public void setBottomRightPoint(PointF bottomRightPoint) {
        this.bottomRightPoint = bottomRightPoint;
    }

    public void moveLeft(float value) {
        this.bottomRightPoint.x -= value;
        this.bottomLeftPoint.x -= value;
        this.topRightPoint.x -= value;
        this.topLeftPoint.x -= value;
    }
}
