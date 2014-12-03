package com.stoyan.filedownloader;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class DownloadService extends IntentService {

    private int result = Activity.RESULT_CANCELED;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String fileName = intent.getStringExtra("FILE_NAME");
        File output = new File(Environment.getExternalStorageDirectory(), fileName);

        try {
            String urlPath = intent.getStringExtra("URL");
            URL url = new URL(urlPath);

            InputStream stream  = url.openConnection().getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            FileOutputStream fos = new FileOutputStream(output.getPath());

            int next = -1;
            while ((next = reader.read()) != -1) {
                fos.write(next);
            }

            result = Activity.RESULT_OK;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent resultIntent = new Intent("RESULT_SET");
        intent.putExtra("FILE_NAME", fileName);
        intent.putExtra("RESULT_CODE", result);
        sendBroadcast(resultIntent);
    }
}
