package com.pubnub.examples.pubnubExample10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Dari on 2/14/2015.
 */
public class AlarmPanel extends Activity {
    Button mButton;
    Intent mainPanelIntent;
    ImageButton lockButton,unLockButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panelmain);
       lockButton = (ImageButton)findViewById(R.id.imageLockButton);
       unLockButton = (ImageButton)findViewById(R.id.imageUnlockButton);
        lockButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                       new MainActivity().subscribe();
                    }
                });
        unLockButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                    Intent myIntent = new Intent(getApplicationContext(),AlarmManagerActivity.class);
                        startActivity(myIntent);
                        //new MainActivity().unsubscribe();
                    }
                });
    }
}
