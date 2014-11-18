package com.stoyan.drawablebrush;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    private ImageButton crossButton;
    private ImageButton pencilButton;
    private ImageButton rocketButton;
    private CustomView drawableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crossButton = (ImageButton) findViewById(R.id.greenCrossButton);
        pencilButton = (ImageButton) findViewById(R.id.redPencilButton);
        rocketButton = (ImageButton) findViewById(R.id.blackRocketButton);

        drawableView = (CustomView) findViewById(R.id.drawGround);
        setDrawable(crossButton);
        setSelection(true, false, false);

        crossButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setDrawable(crossButton);
                setSelection(true, false, false);
            }
        });

        pencilButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setDrawable(pencilButton);
                setSelection(false, true, false);
            }
        });

        rocketButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setDrawable(rocketButton);
                setSelection(false, false, true);
            }
        });
    }

    private void setDrawable(ImageButton button) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) button.getDrawable();
        drawableView.setImageDrawable(bitmapDrawable);
    }

    private void setSelection(boolean crossButtonSelected, boolean pencilButtonSelected, boolean rocketButtonSelected) {
        crossButton.setSelected(crossButtonSelected);
        pencilButton.setSelected(pencilButtonSelected);
        rocketButton.setSelected(rocketButtonSelected);
    }
}
