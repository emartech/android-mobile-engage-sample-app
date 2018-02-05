package com.emarsys.mobileengage.sample.testutils;

import android.os.Handler;

import com.emarsys.mobileengage.MobileEngage;
import com.emarsys.mobileengage.sample.SampleApplication;

import java.lang.reflect.Field;

public class InstrumentedApplication extends SampleApplication {
    @Override
    public void onCreate() {
        deleteDatabase(DatabaseTestUtils.EMARSYS_CORE_QUEUE_DB);
        deleteDatabase(DatabaseTestUtils.EMARSYS_MOBILE_ENGAGE_DB);

        super.onCreate();

        try {
            Field handlerField = MobileEngage.class.getDeclaredField("coreSdkHandler");
            handlerField.setAccessible(true);
            Handler handler = (Handler) handlerField.get(null);
            handler.getLooper().quit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
