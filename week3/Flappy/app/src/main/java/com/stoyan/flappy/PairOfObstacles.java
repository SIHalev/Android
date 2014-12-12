package com.stoyan.flappy;

/**
 * Created by Stoyan on 11/14/2014.
 */
public class PairOfObstacles {
    private Obstacle topObstacle;
    private Obstacle bottomObstacle;

    public PairOfObstacles(Obstacle topObstacle, Obstacle bottomObstacle) {
        //topObstacle = new Obstacle(obstacleBitmap, 200 * obstacles.size(), randNum - BASE_OBSTECLE_HEIGHT_GAP, 0, true);
        //obstacles.add(new Obstacle(obstacleBitmap, 200 * obstacles.size(), screenHeight, randNum + BASE_OBSTECLE_HEIGHT_GAP, false));
    }

    public Obstacle getTopObstacle() {
        return topObstacle;
    }

    public Obstacle getBottomObstacle() {
        return bottomObstacle;
    }
}
