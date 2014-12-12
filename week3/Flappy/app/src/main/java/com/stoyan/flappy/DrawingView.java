package com.stoyan.flappy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingView extends View {
    private final int BASE_OBSTECLES_COUNT = 10; // Make it dinamic later.
    private final int BASE_OBSTECLE_WIDTH_GAP = 300;
    private final int BASE_OBSTECLE_HEIGHT_GAP = 50;
    private Bird bird;
    private Background background;
    private Background secondBackground;
    private int screenWidth, screenHeight;


    private List<Obstacle> obstacles;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        obstacles = new ArrayList<Obstacle>();
        addObstacles(BASE_OBSTECLES_COUNT);

        Bitmap birdBitmap = getBirdBitmap();
        bird = new Bird(birdBitmap, screenWidth / 2 - birdBitmap.getWidth()  , screenHeight / 2 - birdBitmap.getHeight());

        Bitmap backgroundBitmap = getBackGroundBitmap(screenWidth, screenHeight);
        background = new Background(backgroundBitmap, 0);
        secondBackground = new Background(backgroundBitmap, backgroundBitmap.getWidth());
    }

    public void checkObstacles() {
        //Log.v("SIZE: ", ""+ obstacles.size());
        if(obstacles.size() <= 5) {
            addObstacles(5);

        }
        removeCompletedObstacles();

    }

    private void addObstacles(int count) {
       Bitmap obstacleBitmap = getObstacleBitmap();
       int obsCount = obstacles.size() / 2;
        for(int i=0; i <= count; i++) {
            //obstacles.add(new Obstacle());
            //Log.v("WIDTH:", ""+ screenWidth);
            //Log.v("HEIGHT:", ""+ screenHeight);
            //150 change
            int min = BASE_OBSTECLE_HEIGHT_GAP + 1;
            int max = screenHeight - BASE_OBSTECLE_HEIGHT_GAP;

            Random r = new Random();
            int randNum = r.nextInt(max - min + 1) + min;

            obstacles.add(new Obstacle(obstacleBitmap, 200 * obstacles.size(), randNum - BASE_OBSTECLE_HEIGHT_GAP, 0, true));
            obstacles.add(new Obstacle(obstacleBitmap, 200 * (obstacles.size() - 1), screenHeight, randNum + BASE_OBSTECLE_HEIGHT_GAP, false));

        }
    }

    private void removeCompletedObstacles() {
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle currentObstacle = obstacles.get(i);
            if(currentObstacle.getBottomLeftPoint().x <= 0) {
                obstacles.remove(i);
            }
        }
    }

    private Bitmap getObstacleBitmap() {
        return  BitmapFactory.decodeResource(getResources(), R.drawable.pipe2);
    }

    private Bitmap getBirdBitmap() {
        return  BitmapFactory.decodeResource(getResources(), R.drawable.nyan_cat);
    }

    private Bitmap getObstacleBitmap(float newWidth, float newHeight) {
        return resizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background), newWidth, newHeight);
    }

    private Bitmap getBackGroundBitmap(float newWidth, float newHeight) {
        return resizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background), newWidth, newHeight);
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

    public void moveBackGround(float value) {

        background.moveLeft(value);
        if(background.getX() + background.getBitmap().getWidth() <= 0) {
            background.setX(secondBackground.getBitmap().getWidth());
        }

        secondBackground.moveLeft(value);
        if(secondBackground.getX() + secondBackground.getBitmap().getWidth() <= 0) {
            secondBackground.setX(secondBackground.getBitmap().getWidth());
        }
    }

    public void moveObstacles(float value) {
        for(int i=0; i < obstacles.size(); i++) {
            obstacles.get(i).moveLeft(value);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        background.draw(canvas);
        secondBackground.draw(canvas);
        bird.draw(canvas);
        for(int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).draw(canvas);
        }
    }


    public Bird getBird() {
        return this.bird;
    }

    public Background getBackgroundObject() {
        return this.background;
    }

    public void applyGravity(float value) {
        bird.moveLDown(value);
    }

    public boolean checkLost() {
        if(didBirdFelt()) {
            return true;
        }

        if(didBirdHitObstacle()) {
            return true;
        }

        return false;
    }

    private boolean didBirdHitObstacle() {

        for (int i=0; i <= 3; i++) {
            if(didHit(obstacles.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean didHit(Obstacle obstacle) {
        //if(bird.getX() )
        return false;
    }

    private boolean didBirdFelt() {
        //Log.v("asdf:", "" + bird.getY());
        if(screenHeight <= bird.getY() + bird.getBitmap().getHeight() || bird.getY() <= 0) {
            return true;
        }

        return false;
    }

    public void restart() {
        bird.setY(screenHeight / 2 - bird.getBitmap().getHeight());
        obstacles.clear();
        addObstacles(BASE_OBSTECLES_COUNT);
    }





    /*public Obstacle getObstacle(){
        return obstacle;
    }*/
}
