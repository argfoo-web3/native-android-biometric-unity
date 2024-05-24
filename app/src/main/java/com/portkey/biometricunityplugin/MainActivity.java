package com.portkey.biometricunityplugin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.portkey.biometricunityactivity.BiometricUnityActivity;

public class MainActivity extends AppCompatActivity {

    public class BiometricCallback implements BiometricUnityActivity.Callback {
        public void onResult(Boolean success) {

        }
        public void onError(Exception e) {

        }
        public void canAuthenticate(Boolean enabled) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BiometricUnityActivity.title = "Title";
        BiometricUnityActivity.negativeButtonText = "Cancel";
        BiometricUnityActivity.description = "Description";
        BiometricUnityActivity.subtitle = "Subtitle";
        BiometricUnityActivity.SetCallback(new BiometricCallback());
        BiometricUnityActivity.CanAuthenticate(this.getApplicationContext());
        BiometricUnityActivity.Call(this);
    }
}