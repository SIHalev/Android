package com.stoyan.puzzlegame;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
    private final int COLUMN_COUNT = 4;
    private final int ROW_COUNT = 4;
    private boolean isVictorius = false;
    private GridLayout gridLayout;

    private List<DrawableInfo> drawables;
    private List<Drawable> shuffledDrawables;

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
        //final GridLayout mainLayout = (GridLayout) findViewById(R.id.mainGrid); // If we get it form the xml

        final TypedArray images = getResources().obtainTypedArray(R.array.images);

        drawables = new ArrayList<DrawableInfo>();
        for (int i = 0; i < images.length(); i++) {
            drawables.add(new DrawableInfo(images.getDrawable(i), String.valueOf(i)));
        }
        images.recycle();

        Collections.shuffle(drawables);

        for (int i = 0; i < drawables.size(); i++) {
            final ImageView imgView = new ImageView(this);

            imgView.setImageDrawable(drawables.get(i).getImageDrawable());
            imgView.setTag(drawables.get(i).getTag());
            imgView.setMaxWidth(imageWidth);
            imgView.setAdjustViewBounds(true);

            imgView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (!isVictorius) {
                        View.DragShadowBuilder builder = new View.DragShadowBuilder(view);
                        imgView.startDrag(null, builder, imgView, 0);

                    } else {
                        Toast.makeText(getApplicationContext(), "VICTORY", Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });

            imgView.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View view, final DragEvent dragEvent) {
                    if (dragEvent.getAction() == DragEvent.ACTION_DROP) {

                        final ImageView dragSource = (ImageView) dragEvent.getLocalState();

                        Drawable tempDrawable = dragSource.getDrawable();
                        dragSource.setImageDrawable(imgView.getDrawable());
                        imgView.setImageDrawable(tempDrawable);

                        String tempTag = dragSource.getTag().toString();
                        dragSource.setTag(imgView.getTag());
                        imgView.setTag(tempTag);

                        if(checkVictory()) {
                            Toast.makeText(getApplicationContext(), "VICTORY", Toast.LENGTH_LONG).show();
                            isVictorius = true;
                        }
                    }
                    return true;
                }
            });

            gridLayout.addView(imgView);
        }
    }

    private boolean checkVictory() {
        boolean result = true;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            String tag = gridLayout.getChildAt(i).getTag().toString();

            if (Integer.parseInt(tag) != i) {
                result = false;
                break;
            }
        }
        return result;
    }
}
