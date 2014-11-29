package com.stoyan.multipleactivities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
    //TODO: make better patterns.
    public static final String PHONE_NUMBER_REGEX = "[0-9\\+]{3,20}";
    public static final String WEB_PAGE_REGEX= "([-\\w\\.]+)+(:\\d+)?(/([\\w/_\\.]*(\\?\\S+)?)?)?";
    public static final String ALARM_REGEX= "[0-2][0-9]:[0-6][0-9]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dialButton = (Button) findViewById(R.id.dialButton);
        Button browseButton = (Button) findViewById(R.id.browseButton);
        Button setAlarmButton = (Button) findViewById(R.id.alarmButton);

        dialButton.setOnClickListener(onClickListener);
        browseButton.setOnClickListener(onClickListener);
        setAlarmButton.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String input = ((EditText) findViewById(R.id.editText)).getText().toString();

            switch(v.getId()){
                case R.id.dialButton:
                    if(input.matches(PHONE_NUMBER_REGEX)) {
                        dialNumber(input);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid number format.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.browseButton:
                    if(input.matches(WEB_PAGE_REGEX)) {
                        openPage(input);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid web page format.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.alarmButton:
                    if(input.matches(ALARM_REGEX)) {
                        setAlarmClock(input);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid alarm format.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void dialNumber(String input) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + input));
        startActivity(intent);
    }

    private void openPage(String input) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + input));
        startActivity(intent);
    }

    private void setAlarmClock(String input) {
        Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);
        String[] parts = input.split(":");

        alarm.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(parts[0]));
        alarm.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(parts[1]));
        startActivity(alarm);
    }
}
