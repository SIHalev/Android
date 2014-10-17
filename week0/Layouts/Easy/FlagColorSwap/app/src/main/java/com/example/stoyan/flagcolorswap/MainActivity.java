package com.example.stoyan.flagcolorswap;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {
    private int[] currentPointers = new int[]{0, 0, 0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view1 = findViewById(R.id.view1);
        View view2 = findViewById(R.id.view2);
        View view3 = findViewById(R.id.view3);

        View.OnClickListener colorChangeListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.view1:
                        nextColor(view, 0);
                        break;

                    case R.id.view2:
                        nextColor(view, 1);
                        break;

                    case R.id.view3:
                        nextColor(view, 2);
                        break;
                }
            }
        };

        view1.setOnClickListener(
                colorChangeListener
        );
        view2.setOnClickListener(
                colorChangeListener
        );
        view3.setOnClickListener(
                colorChangeListener
        );
    }

    private void nextColor(View view, int id) {
        final int[] colors = getResources().getIntArray(R.array.colors);
        currentPointers[id]++;
        view.setBackgroundColor(colors[currentPointers[id] % colors.length]);
    }
}