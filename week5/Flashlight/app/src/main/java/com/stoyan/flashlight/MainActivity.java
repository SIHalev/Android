package com.stoyan.flashlight;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        turnFlashLightOn(true);
    }

    @Override
    protected void onStop() {
        super.onStop();

        turnFlashLightOn(false);
    }

    private void turnFlashLightOn(boolean state) {
        camera = Camera.open();
        Camera.Parameters p = camera.getParameters();
        if(state == true) {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        } else {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }

        camera.setParameters(p);
        camera.startPreview();
    }
}
