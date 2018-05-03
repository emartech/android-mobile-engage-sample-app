package com.emarsys.mobileengage.sample;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.emarsys.core.util.log.EMSLoggerSettings;
import com.emarsys.mobileengage.MobileEngage;
import com.emarsys.mobileengage.config.MobileEngageConfig;
import com.emarsys.mobileengage.experimental.MobileEngageFeature;
import com.emarsys.mobileengage.iam.InAppMessageHandler;

import org.json.JSONObject;

public class SampleApplication extends Application implements InAppMessageHandler {

    private static final String TAG = "SampleApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        EMSLoggerSettings.enableLoggingForAllTopics();
        Log.i(TAG, "Core version: " + com.emarsys.core.BuildConfig.VERSION_NAME);
        Log.i(TAG, "MobileEngage version: " + com.emarsys.mobileengage.BuildConfig.VERSION_NAME);
        MobileEngageConfig config = new MobileEngageConfig.Builder()
                .application(this)
                .credentials("14C19-A121F", "PaNkfOD90AVpYimMBuZopCpm8OWCrREu")
                .enableDefaultChannel("default", "here is a description")
                .enableExperimentalFeatures(
                        MobileEngageFeature.IN_APP_MESSAGING,
                        MobileEngageFeature.USER_CENTRIC_INBOX
                )
                .setDefaultInAppMessageHandler(this)
                .build();

        createNotificationChannels();

        MobileEngage.setup(config);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel("ems_sample_news", "News", "News and updates go into this channel", NotificationManager.IMPORTANCE_DEFAULT);
            createNotificationChannel("ems_sample_messages", "Messages", "Important messages go into this channel", NotificationManager.IMPORTANCE_HIGH);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String id, String name, String description, int importance) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(description);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void handleApplicationEvent(String eventName, JSONObject payload) {
        Toast.makeText(this, eventName + " - " + payload.toString(), Toast.LENGTH_LONG).show();
    }
}