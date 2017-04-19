package com.emarsys.mobileengage.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emarsys.mobileengage.MobileEngage;
import com.emarsys.mobileengage.MobileEngageStatusListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private Button appLogingAnonymous;
    private Button appLogin;
    private Button appLogout;
    private Button customEvent;
    private Button messageOpen;

    private EditText applicationId;
    private EditText applicationSecret;
    private EditText eventName;
    private EditText eventAttributes;
    private EditText messageId;

    private TextView statusLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileEngage.setStatusListener(new MobileEngageStatusListener() {
            @Override
            public void onError(String id, Exception e) {
                statusLabel.setText("Failure");
            }

            @Override
            public void onStatusLog(String id, String message) {
                statusLabel.setText("OK");
            }
        });

        setContentView(R.layout.activity_main);

        statusLabel = (TextView) findViewById(R.id.statusLabel);

        appLogingAnonymous = (Button) findViewById(R.id.appLoginAnonymous);
        appLogin = (Button) findViewById(R.id.appLogin);
        appLogout = (Button) findViewById(R.id.appLogout);
        customEvent = (Button) findViewById(R.id.customEvent);
        messageOpen = (Button) findViewById(R.id.messageOpen);

        applicationId = (EditText) findViewById(R.id.applicationId);
        applicationSecret = (EditText) findViewById(R.id.applicationSecret);
        eventName = (EditText) findViewById(R.id.eventName);
        eventAttributes = (EditText) findViewById(R.id.eventAttributes);
        messageId = (EditText) findViewById(R.id.messageId);

        appLogingAnonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileEngage.appLogin();
            }
        });

        appLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(applicationId.getText().toString());
                String secret = applicationSecret.getText().toString();
                MobileEngage.appLogin(id, secret);
            }
        });

        appLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileEngage.appLogout();
            }
        });

        customEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eventName.getText().toString();
                String attributesString = eventAttributes.getText().toString();

                Map<String, String> attributes = null;
                if (!attributesString.isEmpty()) {
                    try {
                        attributes = new HashMap<>();
                        JSONObject json = new JSONObject(attributesString);
                        Iterator<String> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            attributes.put(key, json.getString(key));
                        }
                    } catch (JSONException e) {
                        Log.w(TAG, "Event attributes edittext content is not a valid JSON!");
                    }
                } else {
                    attributesString = null;
                }

                MobileEngage.trackCustomEvent(name, attributes);
            }
        });

        messageOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String id = messageId.getText().toString();
                JSONObject json = null;
                try {
                    json = new JSONObject()
                            .put("u", "{\"sid\": \"" + id + "\"}");
                    intent.putExtra("pw_data_json_string", json.toString());
                    MobileEngage.trackMessageOpen(intent);
                } catch (JSONException je) {
                    Log.e(TAG, "Exception while creating JSONObject", je);
                }
            }
        });
    }


//    @Override
//    public void doOnRegistered(String registrationId) {
//        Log.i(TAG, "Registered for pushes: " + registrationId);
//        MobileEngage.setPushToken(registrationId);
//    }
//
//    @Override
//    public void doOnRegisteredError(String errorId) {
//        Log.e(TAG, "Failed to register for pushes: " + errorId);
//    }
//
//    @Override
//    public void doOnMessageReceive(String message) {
//        Log.i(TAG, "Notification opened: " + message);
//        MobileEngage.trackMessageOpen(message);
//        showToast(message);
//    }
//
//    @Override
//    public void doOnUnregistered(final String message) {
//        Log.i(TAG, "Unregistered from pushes: " + message);
//    }
//
//    @Override
//    public void doOnUnregisteredError(String errorId) {
//        Log.e(TAG, "Failed to unregister from pushes: " + errorId);
//    }

    private void showToast(String message) {
        try {
            JSONObject json = new JSONObject(message);
            String title = json.getString("title");
            if (title != null) {
                Toast.makeText(this, "Push received, title: " + title, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Push received, but no title was found. o_O", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Push received, but JSONException happened, check logs", Toast.LENGTH_LONG).show();
        }
    }
}
