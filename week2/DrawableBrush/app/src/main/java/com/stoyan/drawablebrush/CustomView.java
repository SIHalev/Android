package com.stoyan.drawablebrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {
    private static final int DEFFAULT_IMAGE_ALPHA = 120;
    private Bitmap filledBitmap;
    private Canvas canvasForDraw; // So we can draw to filledBitmap.
    private BitmapDrawable imageDrawable;
    private Paint imageRefferences;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        filledBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvasForDraw = new Canvas(filledBitmap);
        imageRefferences = new Paint();
        imageRefferences.setAlpha(DEFFAULT_IMAGE_ALPHA);

        this.setOnTouchListener(new MyTouchListener());
    }

    public CustomView(Context context) { super(context); }
    public CustomView(Context context, AttributeSet attrs) { super(context, attrs); }
    public CustomView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    public void setImageDrawable(BitmapDrawable imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(filledBitmap, 0, 0, null);
    }


    class MyTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Bitmap imageBitmap = imageDrawable.getBitmap();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE: {
                    canvasForDraw.drawBitmap(imageBitmap, event.getX(), event.getY(), imageRefferences);
                    CustomView.this.invalidate();
                    break;
                }
            }

            return true;
        }
    }
}
