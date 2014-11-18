package com.stoyan.animatedpuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.TypedArray;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final int COLUMN_COUNT = 4;
    private final int ROW_COUNT = 4;
    private GridLayout gridLayout;
    private boolean isVictorius = false;
    private List<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main); // Made with code.

        gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(COLUMN_COUNT);
        gridLayout.setRowCount(ROW_COUNT);

        FrameLayout.LayoutParams gridLayoutParam =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        setContentView(gridLayout, gridLayoutParam);

        Display display = getWindowManager().getDefaultDisplay();
        Point screenMetrix = new Point();
        display.getSize(screenMetrix);
        int imageWidth = screenMetrix.x / COLUMN_COUNT;

        final List<Drawable> images = new ArrayList<Drawable>(ROW_COUNT * COLUMN_COUNT);

        TypedArray imagesData = getResources().obtainTypedArray(R.array.images);
        for (int i = 0; i < imagesData.length(); i++) {
            images.add(imagesData.getDrawable(i));
        }
        imagesData.recycle();

        imageViews = new ArrayList<ImageView>(ROW_COUNT * COLUMN_COUNT);
        for (int i = 0; i < ROW_COUNT * COLUMN_COUNT; i++) {
            ImageView imageView = new ImageView(this);

            imageView.setImageDrawable(images.get(i));
            imageView.setTag(String.valueOf(i));
            imageView.setAdjustViewBounds(true);
            imageView.setMaxWidth(imageWidth);
            imageViews.add(imageView);
        }

        Collections.shuffle(imageViews);

        for (int i = 0; i < ROW_COUNT * COLUMN_COUNT; i++) {
            ImageView imageView = imageViews.get(i);
            gridLayout.addView(imageViews.get(i));

            imageView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (!isVictorius) {
                        View.DragShadowBuilder builder = new View.DragShadowBuilder(view);
                        view.startDrag(null, builder, view, 0);
                    } else {
                        Toast.makeText(getApplicationContext(), "VICTORY", Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });
            imageView.setOnDragListener(new OnDragListener() {
                @Override
                public boolean onDrag(View view, DragEvent event) {
                    if (event.getAction() == DragEvent.ACTION_DROP) {

                        ImageView targetView = (ImageView) event.getLocalState();

                        final AnimatorSet set = new AnimatorSet();
                        final ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "x", view.getX(), targetView.getX());
                        animatorX.setDuration(1000);
                        final ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "y", view.getY(), targetView.getY());
                        animatorY.setDuration(1000);

                        final ObjectAnimator animatorX2 = ObjectAnimator.ofFloat(targetView, "x", targetView.getX(), view.getX());
                        animatorX2.setDuration(1000);
                        final ObjectAnimator animatorY2 = ObjectAnimator.ofFloat(targetView, "y", targetView.getY(), view.getY());
                        animatorY2.setDuration(1000);

                        set.playTogether(animatorX, animatorY, animatorX2, animatorY2);
                        set.start();

                        /*
                        Drawable tempDrawable = dragSource.getDrawable();
                        dragSource.setImageDrawable(imgView.getDrawable());
                        imgView.setImageDrawable(tempDrawable);

                        String tempTag = dragSource.getTag().toString();
                        dragSource.setTag(imgView.getTag());
                        imgView.setTag(tempTag);*/
                        Collections.swap(imageViews, imageViews.indexOf(view), imageViews.indexOf(targetView));

                        if (checkVictory()) {
                            Toast.makeText(getApplicationContext(), "VICTORY", Toast.LENGTH_LONG).show();
                            isVictorius = true;
                        }
                    }
                    return true;
                }
            });
        }
    }

    private boolean checkVictory() {
        boolean result = true;
        for(int i=0; i < imageViews.size(); i++) {
            String tag = imageViews.get(i).getTag().toString();

            if (Integer.valueOf(tag) != i) {
                result = false;
                break;
            }
        }
        return result;
    }
}
