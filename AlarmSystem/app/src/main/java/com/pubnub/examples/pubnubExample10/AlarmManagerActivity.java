package com.pubnub.examples.pubnubExample10;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Dari on 2/16/2015.
 */
public class AlarmManagerActivity extends Activity {
    Button stopButton;
   // AlarmManager myAlarmManager (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    AlarmManager alarmManager;
    PendingIntent alarmIntent;
    Intent intentToFire;
    @Override
  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_cancel);
       alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to wake the device if sleeping.
        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
// Trigger the device immediately.

        long timeOrLengthofWait = -10;
// Create a Pending Intent that will broadcast and action
        String ALARM_ACTION = "ALARM_ACTION";
        intentToFire = new Intent(ALARM_ACTION);
        alarmIntent = PendingIntent.getBroadcast(this,0,intentToFire, 0);
        // Set the alarm
        alarmManager.set(alarmType, timeOrLengthofWait, alarmIntent);
        stopButton =(Button)findViewById(R.id.Stopbutton);
        stopButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        StopAlarm();
                    }
                });
    }
    public void StopAlarm(){
        alarmManager.cancel(alarmIntent);
    }
}
