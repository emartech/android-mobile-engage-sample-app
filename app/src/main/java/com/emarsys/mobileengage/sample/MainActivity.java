package com.emarsys.mobileengage.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                Log.e(TAG, e.getMessage(), e);
                statusLabel.append(e.getMessage());
            }

            @Override
            public void onStatusLog(String id, String message) {
                Log.i(TAG, message);
                statusLabel.append(message);
            }
        });

        setContentView(R.layout.activity_main);

        statusLabel = (TextView) findViewById(R.id.statusLabel);

        appLogingAnonymous = (Button) findViewById(R.id.appLoginAnonymous);
        appLogin = (Button) findViewById(R.id.appLogin);
        appLogout = (Button) findViewById(R.id.appLogout);
        customEvent = (Button) findViewById(R.id.customEvent);
        messageOpen = (Button) findViewById(R.id.messageOpen);

        applicationId = (EditText) findViewById(R.id.contactFieldId);
        applicationSecret = (EditText) findViewById(R.id.contactFieldValue);
        eventName = (EditText) findViewById(R.id.eventName);
        eventAttributes = (EditText) findViewById(R.id.eventAttributes);
        messageId = (EditText) findViewById(R.id.messageId);

        appLogingAnonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusLabel.setText("Anonymous login: ");
                MobileEngage.appLogin();
            }
        });

        appLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(applicationId.getText().toString());
                String secret = applicationSecret.getText().toString();
                MobileEngage.appLogin(id, secret);
                statusLabel.setText("Login: ");
            }
        });

        appLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileEngage.appLogout();
                statusLabel.setText("Logout: ");
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
                }

                MobileEngage.trackCustomEvent(name, attributes);
                statusLabel.setText("Custom event: ");
            }
        });

        messageOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String id = messageId.getText().toString();
                Bundle payload = new Bundle();
                payload.putString("key1", "value1");
                payload.putString("u", String.format("{\"sid\": \"%s\"}", id));
                intent.putExtra("payload", payload);
                statusLabel.setText("Message open: ");
                MobileEngage.trackMessageOpen(intent);
            }
        });
    }

}
