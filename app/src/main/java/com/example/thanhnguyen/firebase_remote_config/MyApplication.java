package com.example.thanhnguyen.firebase_remote_config;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thanhnguyen on 2/21/17.
 */

public class MyApplication extends Application {

    public static final String KEY_UPDATE_REQUIRED = "force_update_required";
    private FirebaseRemoteConfig firebaseRemoteConfig;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public FirebaseRemoteConfig getFirebaseRemoteConfig() {
        return firebaseRemoteConfig;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupRemoteConfig();
    }

    private void setupRemoteConfig() {
        long cacheExpiration = 3600;
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings.Builder builder = new FirebaseRemoteConfigSettings.Builder();
        builder.setDeveloperModeEnabled(true);
        FirebaseRemoteConfigSettings configSettings = builder.build();

        if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        firebaseRemoteConfig.setConfigSettings(configSettings);
        Map<String, Object> remoteConfigDefaults = new HashMap<>();
        remoteConfigDefaults.put(KEY_UPDATE_REQUIRED, false);
        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);
        firebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseRemoteConfig.activateFetched();
                }
            }
        });
    }
}
