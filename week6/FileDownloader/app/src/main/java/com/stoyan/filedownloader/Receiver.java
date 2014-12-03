package com.stoyan.filedownloader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int resultCode = bundle.getInt("RESULT_CODE");
            String fileName = bundle.getString("FILE_NAME");

            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "Download finished: " + fileName, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Download error, try to turn your wifi on.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
