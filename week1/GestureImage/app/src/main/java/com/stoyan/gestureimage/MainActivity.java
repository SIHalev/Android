package com.stoyan.gestureimage;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private float prevLineDistance;
    private float currLineDistance;
    private float deltaDistance;

    private PointF prevFinger1;
    private PointF prevFinger2;

    private ImageView image;
    private List<Animator> anamiationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: FIX BUG - there is a bug where when you rotate/scale with low value the translate operation breaks.

        anamiationList = new ArrayList<Animator>();

        Button frameButton = (Button) findViewById(R.id.setFrameButton);
        Button playButton = (Button) findViewById(R.id.playButton);
        image = (ImageView) findViewById(R.id.nyan);

        View mainWindow = (View) findViewById(R.id.mainId);

        frameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", image.getScaleX());
                PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", image.getScaleY());
                PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation", image.getRotation());
                PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", image.getTranslationX());
                PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", image.getTranslationY());

                anamiationList.add(ObjectAnimator.ofPropertyValuesHolder(image, translationX, translationY, scaleX, scaleY, rotation));

                Toast.makeText(getApplicationContext(), "Frame " + anamiationList.size() + " was set", Toast.LENGTH_SHORT).show();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(anamiationList.size() != 0) {
                    AnimatorSet set = new AnimatorSet();
                    set.playSequentially(new ArrayList<Animator>(anamiationList));
                    set.start();

                    anamiationList.clear();
                } else {
                    Toast.makeText(getApplicationContext(), "Set frames first !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mainWindow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int pointersCount = event.getPointerCount();

                int pointerId1 = event.getPointerId(0);
                int pointerIndex1 = event.findPointerIndex(pointerId1);
                PointF finger1 = new PointF(event.getX(pointerIndex1), event.getY(pointerIndex1));
                int maskedAction = event.getActionMasked();

                if(pointersCount == 1) {
                    switch (maskedAction) {
                        case MotionEvent.ACTION_DOWN: {
                            prevFinger1 = finger1;
                            //prevRawX = event.getRawX();
                            break;
                        }

                        case MotionEvent.ACTION_MOVE: {
                            if(prevFinger1 != null) {
                                float distanceX = finger1.x - prevFinger1.x;
                                float distanceY = finger1.y - prevFinger1.y;

                                //v.setX(v.getX() + distanceX - v.getWidth());
                                //v.setY(v.getY() + distanceY - v.getHeight());
                                image.setTranslationX(image.getTranslationX() + distanceX);
                                image.setTranslationY(image.getTranslationY() + distanceY);

                                prevFinger1 = finger1;
                            }

                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            prevFinger1 = null;
                            break;
                        }
                    }
                }

                if (pointersCount == 2) {
                    int pointerId2 = event.getPointerId(1);
                    int pointerIndex2 = event.findPointerIndex(pointerId2);
                    //PointF finger2 = new PointF(event.getX(pointerIndex2), event.getY(pointerIndex2));
                    PointF finger2 = new PointF(event.getX(pointerIndex2), event.getY(pointerIndex2));

                    switch (maskedAction) {

                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_POINTER_DOWN: {

                            prevFinger1 = finger1;
                            prevFinger2 = finger2;
                            prevLineDistance = getDistance(finger1, finger2);

                            break;
                        }
                        case MotionEvent.ACTION_MOVE: {

                            currLineDistance = getDistance(finger1, finger2);
                            deltaDistance = currLineDistance / prevLineDistance;

                            image.setRotation(image.getRotation() + (int) angleBetweenLines(finger1, prevFinger1, finger2, prevFinger2));

                            image.setScaleX(image.getScaleX() * deltaDistance);
                            image.setScaleY(image.getScaleY() * deltaDistance);

                            /*
                            ImageView imageView = (ImageView) v;
                            Matrix matrix=new Matrix();
                            imageView.setScaleType(ImageView.ScaleType.MATRIX);
                            matrix.postRotate((float) angleBetweenLines(finger1, prevFinger1, finger2, prevFinger2), pivX, pivY);
                            imageView.setImageMatrix(matrix);*/

                            prevFinger1 = finger1;
                            prevFinger2 = finger2;
                            prevLineDistance = getDistance(finger1, finger2);

                            break;
                        }
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                        case MotionEvent.ACTION_CANCEL: {

                            prevFinger1 = null;
                            prevFinger2 = null;
                            break;
                        }
                    }
                }

                return true;
            }
        });

    }

    public static double angleBetweenLines(PointF finger1, PointF oldFinger1, PointF finger2, PointF oldFinger2) {
        double angle1 = Math.atan2(oldFinger2.y - oldFinger1.y, oldFinger1.x - oldFinger2.x);
        double angle2 = Math.atan2(finger2.y - finger1.y, finger1.x - finger2.x);

        return Math.toDegrees(angle1 - angle2);
        //return angle1 - angle2;
    }

    private float getDistance(PointF finger1, PointF finger2) {
        float width = finger2.x - finger1.x;
        float height = finger2.y - finger1.y;

        float distance = (float) Math.sqrt(width * width + height * height);
        return distance;
    }

    private PointF getCenter(PointF finger1, PointF finger2) {
        PointF center = new PointF();
        center.x = (finger1.x + finger2.x) / 2;
        center.y = (finger1.y + finger2.y) / 2;

        return center;
    }
}
