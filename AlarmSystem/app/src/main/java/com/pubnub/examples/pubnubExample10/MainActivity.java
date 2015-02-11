package com.pubnub.examples.pubnubExample10;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends Activity {

    Pubnub pubnub = new Pubnub("pub-c-ef34a7ed-52a5-4e3b-8d12-39a96d416b02","sub-c-930dd836-acd1-11e4-815e-0619f8945a4f");
    GoogleCloudMessaging gcm;
    SharedPreferences prefs;
    Context context;
    public static String SENDER_ID;
    public static String REG_ID;
    private static final String APP_VERSION = "3.6.1";
    Button pinButton;
    String PUBLISH_KEY = "pub-c-ef34a7ed-52a5-4e3b-8d12-39a96d416b02";//pub-c-ef34a7ed-52a5-4e3b-8d12-39a96d416b02";
    String SUBSCRIBE_KEY = "sub-c-930dd836-acd1-11e4-815e-0619f8945a4f";//sub-c-930dd836-acd1-11e4-815e-0619f8945a4f";
    String CIPHER_KEY = "";
    String SECRET_KEY = "";
    String ORIGIN = "Home Owner";
    String AUTH_KEY;
    String UUID;
    Boolean SSL = false;


    Button mButton;
    EditText pin;
    Intent mainPanelIntent;
//    pubnub = new Pubnub(
//            PUBLISH_KEY,
//            SUBSCRIBE_KEY);
//    pubnub.setCacheBusting(false);
//    pubnub.setOrigin(ORIGIN);
//    pubnub.setAuthKey(AUTH_KEY);
    static final String TAG = "Register Activity";

    private void notifyUser(Object message) {
        try {
            if (message instanceof JSONObject) {
                final JSONObject obj = (JSONObject) message;
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), obj.toString(),
                                Toast.LENGTH_LONG).show();

                        Log.i("Received msg : ", String.valueOf(obj));
                    }
                });

            } else if (message instanceof String) {
                final String obj = (String) message;
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), obj,
                                Toast.LENGTH_LONG).show();
                        Log.i("Received msg : ", obj.toString());
                    }
                });

            } else if (message instanceof JSONArray) {
                final JSONArray obj = (JSONArray) message;
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), obj.toString(),
                                Toast.LENGTH_LONG).show();
                        Log.i("Received msg : ", obj.toString());
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(
                "HOME ALARM", Context.MODE_PRIVATE);
        init();
        setContentView(R.layout.accesspin);
        pin = (EditText)findViewById(R.id.pinText);
        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent intent) {
                pubnub.disconnectAndResubscribe();

            }

        },new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        //registerReceiver()
    }


    private void init() {

        Map<String, String> map = getCredentials();

//        PUBLISH_KEY = map.get("PUBLISH_KEY");
//        SUBSCRIBE_KEY = map.get("SUBSCRIBE_KEY");
//        SECRET_KEY = map.get("SECRET_KEY");
//        CIPHER_KEY = map.get("CIPHER_KEY");
//        SSL = (map.get("SSL") == "true")?true:false;
//        SENDER_ID = map.get("SENDER_ID");
//        AUTH_KEY = map.get("AUTH_KEY");
//        ORIGIN = map.get("ORIGIN");
//        REG_ID = map.get("REG_ID");

        // The following hardcodes this demo app to run against our beta environment and config.

//        PUBLISH_KEY = "demo-36";
//        SUBSCRIBE_KEY = "demo-36";
//        SECRET_KEY = "demo-36";
//        CIPHER_KEY = map.get("CIPHER_KEY");
//        SSL = (map.get("SSL") == "true")?true:false;
//        SENDER_ID = map.get("SENDER_ID");
//        AUTH_KEY = map.get("AUTH_KEY");
//        ORIGIN = "dara24.devbuild";
//        REG_ID = map.get("REG_ID");
//        SENDER_ID = "506053237730";



        pubnub = new Pubnub(
                PUBLISH_KEY,
                SUBSCRIBE_KEY
                //SECRET_KEY,
                //CIPHER_KEY,
               // SSL
        );
        pubnub.setCacheBusting(false);
        pubnub.setOrigin(ORIGIN);
        pubnub.setAuthKey(AUTH_KEY);

        // A SENDER_ID corresponds with a Server API Key with GCM.
        // The above Sender ID (506053237730) corresponds to this Server API Key:
        // AIzaSyBNHRBzCKW9oUtTItl9qmLEVmRgG4SBys4

        // If you use the PubNub demo-36 API keys, we've already associated it on the server-side,
        // you can use this Sender ID in your demo app without needing to config anything server-side (with Google or PubNub)

        // If you want to use your own keys, you can use this SenderID,
        // But you will need to upload AIzaSyBNHRBzCKW9oUtTItl9qmLEVmRgG4SBys4 as your
        // GCM API Key to your Web Portal

        // Or, you use your own PN keyset, replace the above SENDER_ID with your own Sender ID, and upload to the web
        // portal your own associated Server API Key

        // More info on this process here: http://developer.android.com/google/gcm/gs.html

    }

    private void saveCredentials() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PUBLISH_KEY", PUBLISH_KEY);
        editor.putString("SUBSCRIBE_KEY", SUBSCRIBE_KEY);
        editor.putString("SECRET_KEY", SECRET_KEY);
        editor.putString("AUTH_KEY", AUTH_KEY);
        editor.putString("CIPHER_KEY", CIPHER_KEY);
        editor.putString("ORIGIN", ORIGIN);
        editor.putString("UUID", UUID);
        editor.putString("SSL", SSL.toString());
        editor.putString("SENDER_ID", SENDER_ID);
        editor.commit();
    }

    private Map<String, String> getCredentials() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("PUBLISH_KEY", prefs.getString("PUBLISH_KEY", PUBLISH_KEY));
        map.put("SUBSCRIBE_KEY", prefs.getString("SUBSCRIBE_KEY", "SUBSCRIBE_KEY"));
        map.put("SECRET_KEY", prefs.getString("SECRET_KEY", "demo"));
        map.put("CIPHER_KEY", prefs.getString("CIPHER_KEY", ""));
        map.put("AUTH_KEY", prefs.getString("AUTH_KEY", null));
        map.put("ORIGIN", prefs.getString("ORIGIN", "Home Owner"));
        map.put("UUID", prefs.getString("UUID", null));
        map.put("SSL", prefs.getString("SSL", "false"));
        map.put("SENDER_ID", prefs.getString("SENDER_ID", null));
        return map;
    }
 private void gcmUnregister() {
        //TODO add unregister code
    }


    private String gcmRegister() {

        context = getApplicationContext();
        gcm = GoogleCloudMessaging.getInstance(this);

        if (TextUtils.isEmpty(SENDER_ID)) {
            Toast.makeText(getApplicationContext(),
                    "GCM Sender ID not set.",
                    Toast.LENGTH_LONG).show();
            return null;
        }

        REG_ID = getRegistrationId(context);

        if (TextUtils.isEmpty(REG_ID)) {

            registerInBackground();

            Log.d("RegisterActivity",
                    "registerGCM - successfully registered with GCM server - regId: "
                            + REG_ID);
        } else {
            Toast.makeText(getApplicationContext(),
                    "RegId already available. RegId: " + REG_ID,
                    Toast.LENGTH_LONG).show();
        }
        return REG_ID;
    }

    private String getRegistrationId(Context context) {
        String registrationId = prefs.getString("REG_ID", "");
        if (registrationId.length() <= 0) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

    private void registerInBackground() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    REG_ID = gcm.register(SENDER_ID);
                    Log.d("RegisterActivity", "registerInBackground - regId: "
                            + REG_ID);
                    msg = "Device registered, registration ID=" + REG_ID;

                    storeRegistrationId(context, REG_ID);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d("RegisterActivity", "Error: " + msg);
                }
                Log.d("RegisterActivity", "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Toast.makeText(getApplicationContext(),
                        "Registered with GCM Server." + msg, Toast.LENGTH_LONG)
                        .show();
            }
        }.execute(null, null, null);
    }

    private void storeRegistrationId(Context context, String regId) {
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("REG_ID", regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }


    public void subscribe() {


        try {
            pubnub.subscribe(SUBSCRIBE_KEY, new Callback() {

                        @Override
                        public void connectCallback(String channel, Object message) {
                            Log.d("RPi","connecting: " + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        @Override
                        public void disconnectCallback(String channel, Object message) {
                            Log.d("RPi","Disconnecting: " + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        public void reconnectCallback(String channel, Object message) {
                            Log.d("RPi","Reconnecting: " + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        @Override
                        public void successCallback(String channel, Object message) {
                            Log.d("RPi","Connection sccessful : " + channel + " : "
                                    + message.getClass() + " : " + message.toString());
                        }

                        @Override
                        public void errorCallback(String channel, PubnubError error) {
                            Log.d("RPi","Connection error: " + channel
                                    + " : " + error.toString());
                        }
                    }
            );
        } catch (PubnubException e) {
            Log.d("RPi",e.toString());
        }

//        Callback callback = new Callback() {
//            public void successCallback(String channel, Object response) {
//                Log.d("RPi",response.toString());
//            }
//            public void errorCallback(String channel, PubnubError error) {
//                Log.d("RPi",error.toString());
//            }
//        };
        //pubnub.publish(PUBLISH_KEY, "Hello World !!" , callback);
    }

    private void _state(final String channel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set STATE");
        builder.setMessage("Enter state (JSON Object)");
        final EditText etMessage = new EditText(this);
        builder.setView(etMessage);
        builder.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Callback setMetaDataCallback = new Callback() {
                            @Override
                            public void successCallback(String channel,
                                                        Object message) {
                                notifyUser("SET STATE : " + message);
                            }

                            @Override
                            public void errorCallback(String channel,
                                                      PubnubError error) {
                                notifyUser("SET STATE : " + error);
                            }
                        };

                        String message = etMessage.getText().toString();
                        JSONObject js = null;
                        try {
                            js = new JSONObject(message);
                            pubnub.setState(channel, pubnub.getUUID(), js, setMetaDataCallback);
                            return;
                        } catch (Exception e) {
                        }

                    }

                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void _publish(final String channel) {

                        Callback publishCallback = new Callback() {
                            @Override
                            public void successCallback(String channel,
                                                        Object message) {
                                notifyUser("PUBLISH : " + message);
                            }

                            @Override
                            public void errorCallback(String channel,
                                                      PubnubError error) {
                                notifyUser("PUBLISH : " + error);
                            }
                        };

                        //String message = etMessage.getText().toString();

                        try {
                            //Integer i = Integer.parseInt(message);
                            pubnub.publish(channel,"", publishCallback);
                            return;
                        } catch (Exception e) {
                        }

                        try {
                            //Double d = Double.parseDouble(message);
                            pubnub.publish(channel, "", publishCallback);
                            return;
                        } catch (Exception e) {
                        }


                        try {
                           // JSONArray js = new JSONArray(message);
                            pubnub.publish(channel, "", publishCallback);
                            return;
                        } catch (Exception e) {
                        }

                        try {
                            //JSONObject js = new JSONObject(message);
                            pubnub.publish(channel, "", publishCallback);
                            return;
                        } catch (Exception e) {
                        }

                        pubnub.publish(channel, "", publishCallback);
                    }

 public void publish() {

     _publish("demo");
    }



  public void unsubscribe() {

//      pubnub = new Pubnub(
//              PUBLISH_KEY,
//              SUBSCRIBE_KEY
//              //SECRET_KEY,
//              //CIPHER_KEY,
//              // SSL
//      );
//      pubnub.setCacheBusting(false);
//      pubnub.setOrigin(ORIGIN);
//      pubnub.setAuthKey(AUTH_KEY);
          pubnub.unsubscribe("sub-c-930dd836-acd1-11e4-815e-0619f8945a4f");
         Log.v("Android:","You are disconnected from RPi");
    }

}
