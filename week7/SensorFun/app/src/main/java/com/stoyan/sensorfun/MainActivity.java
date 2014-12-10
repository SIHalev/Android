package com.stoyan.sensorfun;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements SensorEventListener {

    private long lastUpdateTime = 0;
    private final int SPEED_NEEDED = 1500;
    private float lastX, lastY, lastZ;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor proximitySensor;
    private MediaPlayer gun;
    private MediaPlayer roar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sensorManager.registerListener(this, accelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        gun = MediaPlayer.create(this, R.raw.ray_gun);
        roar = MediaPlayer.create(this, R.raw.roar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        gun.pause();
        roar.pause();
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastUpdateTime) > 150) {
                long timeDifference = (currentTime - lastUpdateTime);
                lastUpdateTime = currentTime;

                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / timeDifference * 10000;

                if (speed > SPEED_NEEDED) {
                    //gun.seekTo(0);
                    gun.start();
                }

                lastX = x;
                lastY = y;
                lastZ = z;
            }
        }

        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if(event.values[0] == 0) {
                if(roar.isPlaying()) {
                    roar.pause();
                }
                roar.seekTo(0);
                roar.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
