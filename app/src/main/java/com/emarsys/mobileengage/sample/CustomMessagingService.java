package com.emarsys.mobileengage.sample;

import com.emarsys.mobileengage.MobileEngage;
import com.emarsys.mobileengage.service.MessagingServiceUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        MobileEngage.setPushToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        boolean handledByEmarsysSDK = MessagingServiceUtils.handleMessage(
                this,
                remoteMessage,
                MobileEngage.getConfig().getOreoConfig());

        if (!handledByEmarsysSDK) {
            //handle your custom push message here
        }
    }
}