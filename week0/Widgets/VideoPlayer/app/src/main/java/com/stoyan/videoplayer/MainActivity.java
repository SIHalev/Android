package com.stoyan.videoplayer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;
import java.io.File;

public class MainActivity extends Activity {
    private final int SKIP_DURATION = 3000;
    private String Path = "YThis/A.mp4"; // Select different path.
    //private Boolean isPaused = true; // The first implementation was with bool checker.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Uri uri = Uri.parse ( "/sdcard/YThis/A.mp4" ); // Also works.
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), Path));

        final VideoView video = (VideoView) findViewById(R.id.videoView);
        video.setVideoURI(uri);

        ImageButton prevButton = (ImageButton) findViewById(R.id.prevButton);
        final ImageButton playButton = (ImageButton) findViewById(R.id.playPauseButton);
        ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);


        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.seekTo(video.getCurrentPosition() - SKIP_DURATION);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.seekTo(video.getCurrentPosition() + SKIP_DURATION);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video.isPlaying()) {
                    video.pause();
                    playButton.setImageResource(R.drawable.play);
                } else {
                    video.start();
                    //playButton.setImageDrawable();
                    playButton.setImageResource(R.drawable.pause);
                }
            }
        });
    }
}
