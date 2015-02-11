package com.pubnub.examples.pubnubExample10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dari on 2/13/2015.
 */
public class Accessibility extends Activity {
    Button mButton;
    Intent mainPanelIntent;
   // TextView _view;
    ViewGroup root;
    private int xDelta;
    private int yDelta;
    EditText pin;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.accesspin);
      pin = (EditText)findViewById(R.id.pinText);
        mButton = (Button)findViewById(R.id.Sendbutton);
         mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                      if(!(pin.getText().toString().matches("[0-9]{4}")))
                       Toast.makeText(getApplicationContext(), "Enter a valid pin to continue.", Toast.LENGTH_SHORT).show();
                     else{
                          mainPanelIntent = new Intent(getApplication(),AlarmPanel.class);
                           startActivity( mainPanelIntent);
                      }
                    }
                });
        }

}

