package com.stoyan.flappy;

import android.os.Handler;

import com.stoyan.flappy.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class GameClock {

    private static long FRAMERATE_CONSTANT;
    private List<GameClockListener> clockListeners = new ArrayList<GameClockListener>();

    public static interface GameClockListener {
        public void onGameEvent(GameEvent gameEvent);
    }

    private Handler handler = new Handler();

    public GameClock() {
        handler.post(new ClockRunnable());
    }

    public void subscribe(GameClockListener listener) {
        clockListeners.add(listener);
    }

    public void clearSubscribers() {
        clockListeners.clear();
    }

    private class ClockRunnable implements Runnable {
        @Override
        public void run() {
            onTimerTick();
            handler.postDelayed(this, FRAMERATE_CONSTANT);
        }

        private void onTimerTick() {
            for (GameClockListener listener : clockListeners) {
                listener.onGameEvent(new GameEvent());
            }
        }
    }
}