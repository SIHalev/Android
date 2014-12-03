package com.stoyan.splashscreen;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = this.getSharedPreferences("appInfo", 0);
        boolean firstTime = settings.getBoolean("firstTime", true);
        if (firstTime) {
            //This is lazy way, we can do it in a different activity.
            final ImageView logo = (ImageView) findViewById(R.id.hackLogo);
            logo.setAlpha(1.0f);

            logo.postDelayed(new Runnable() {
                public void run() {
                    logo.setAlpha(0f);
                }
            } ,3000);
            //We can make smooth fade out here.

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
        }
     }
}
