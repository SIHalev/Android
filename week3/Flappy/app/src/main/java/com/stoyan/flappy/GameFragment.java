package com.stoyan.flappy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


public class GameFragment extends Fragment {
    private DrawingView game;
    private int jumpLength = 0;
    private int gameWaitTimer = 0;
    private float timePassed = 0;
    private int gameScore = 0;

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        //TODO: finish obstacle colisions !
        final GameClock clock = new GameClock();

        game = (DrawingView) getView().findViewById(R.id.drawingView);

        game.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                jumpLength = 7; //TODO: make a constant
                return true;
            }
        });

        clock.subscribe(new GameClock.GameClockListener() {
            @Override
            public void onGameEvent(GameEvent gameEvent) {
                if(gameWaitTimer <= 0) {

                    //game.getBackgroundObject().moveLeft(1);
                    game.moveBackGround(0.5f);
                    game.moveObstacles(1 + timePassed / 1000);

                    if (jumpLength > 0) {
                        game.getBird().jump();
                        jumpLength--;
                    } else {
                        game.applyGravity(1);
                    }

                    //game.moveObstacles(); ------------------------
                    if (game.checkLost()) {
                        //sendScore();
                        game.restart();
                        timePassed = 0;
                        gameWaitTimer = 2000;

                        clock.clearSubscribers();

                        SendScoreFragment fragment = new SendScoreFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("score", gameScore);
                        fragment.setArguments(bundle);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainLayout, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }

                    gameScore += 2; // make better score logic
                    timePassed++;
                } else {
                    gameWaitTimer--;
                }

                game.checkObstacles();
                game.invalidate();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

}
