package com.stoyan.filedownloader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String URL = "http://95.111.103.224:8080/static/";
    private static final String RESOURCE1 = "diablo_vs_imperius.mp4";
    private static final String RESOURCE2 = "obicham.mp3";
    private static final String RESOURCE3 = "parking_genius.mp4";
    private static final String RESOURCE4 = "penny.mp4";

    private BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new Receiver();

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(receiver, new IntentFilter("RESULT_SET"));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("URL", URL);

        switch(v.getId()) {
            case R.id.button1:
                intent.putExtra("FILE_NAME", RESOURCE1);
                break;
            case R.id.button2:
                intent.putExtra("FILE_NAME", RESOURCE2);
                break;
            case R.id.button3:
                intent.putExtra("FILE_NAME", RESOURCE3);
                break;
            case R.id.button4:
                intent.putExtra("FILE_NAME", RESOURCE4);
                break;
        }

        startService(intent);
    }
}
