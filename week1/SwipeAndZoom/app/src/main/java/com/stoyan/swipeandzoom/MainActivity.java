package com.stoyan.swipeandzoom;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    private int currentImage = 0;
    private boolean isZoomed = false;
    private GestureDetector detector;
    private ImageView imageView;
    private TextView imagePositionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TypedArray images = getResources().obtainTypedArray(R.array.images);

        imageView = (ImageView) findViewById(R.id.imageHolder);

        imagePositionText = (TextView) findViewById(R.id.imageHolderPosition);
        imagePositionText.setText(getImagePositionText(currentImage, images.length()));

        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                if (isZoomed) {
                    zoomOut(imageView);
                } else {
                    zoomIn(imageView);
                }
                isZoomed = !isZoomed;

                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                try {
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        if (currentImage < images.length() - 1) {
                            currentImage++;
                        }

                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        Log.d("SWIPE: ", "LEFT TO RIGHT");
                        if (currentImage > 0) {
                            currentImage--;

                        }
                    }

                    imageView.setImageDrawable(images.getDrawable(currentImage));
                    imagePositionText.setText(getImagePositionText(currentImage, images.length()));
                } catch (Exception e) {

                }
                return true;
            }
        });

    }

    private void zoomOut(ImageView imageView) {
        imageView.setScaleX(1);
        imageView.setScaleY(1);
        imageView.setPadding(25, 0, 25, 0);
    }

    private String getImagePositionText(int currentImage, int maxImage) {
        String result = (currentImage + 1) + "/" + maxImage;
        return result;
    }

    private void zoomIn(ImageView imageView) {
        imageView.setScaleX(1.5f);
        imageView.setScaleY(1.5f);
        imageView.setPadding(0, 20, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return detector.onTouchEvent(e);
    }
}
