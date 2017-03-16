package com.emarsys.mobileengage.sample;

import android.app.Application;
import android.util.Log;

import com.emarsys.mobileengage.MobileEngage;
import com.emarsys.mobileengage.MobileEngageConfig;
import com.emarsys.mobileengage.MobileEngageStatusListener;


public class SampleApplication extends Application {

    private static final String TAG = "SampleApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        MobileEngageConfig config = new MobileEngageConfig.Builder()
                .credentials("14C19-A121F", "PaNkfOD90AVpYimMBuZopCpm8OWCrREu")
                .statusListener(getStatusListener())
                .build();
        MobileEngage.setup(this, config);
    }

    private MobileEngageStatusListener getStatusListener() {
        return new MobileEngageStatusListener() {
            @Override
            public void onError(String id, Exception error) {
                Log.e(TAG, error.getMessage(), error);
            }

            @Override
            public void onStatusLog(String id, String message) {
                Log.i(TAG, message);
            }
        };
    }
}
