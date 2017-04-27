package com.emarsys.mobileengage.sample;

import android.app.Application;
import android.util.Log;

import com.emarsys.mobileengage.MobileEngage;
import com.emarsys.mobileengage.MobileEngageConfig;


public class SampleApplication extends Application {

    private static final String TAG = "SampleApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Core version: " + com.emarsys.core.BuildConfig.VERSION_NAME);
        Log.i(TAG, "MobileEngage version: " + com.emarsys.mobileengage.BuildConfig.VERSION_NAME);
        MobileEngageConfig config = new MobileEngageConfig.Builder()
                .credentials("14C19-A121F", "PaNkfOD90AVpYimMBuZopCpm8OWCrREu")
                .build();
        MobileEngage.setup(this, config);
    }
}
