package com.example.thanhnguyen.firebase_remote_config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        firebaseRemoteConfig = MyApplication.get(context).getFirebaseRemoteConfig();
        final TextView tv = (TextView) findViewById(R.id.tvHello);
        Button btnRemoteConfig = (Button) findViewById(R.id.btnRemoteConfig);
        btnRemoteConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseRemoteConfig.fetch(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseRemoteConfig.activateFetched();
                            tv.setText(firebaseRemoteConfig.getString(MyApplication.KEY_UPDATE_REQUIRED));
                        }
                    }
                });

            }
        });

    }
}
