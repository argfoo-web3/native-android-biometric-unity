package com.portkey.biometricunityactivity;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import co.infinum.goldfinger.Goldfinger;

public class BiometricUnityActivity extends AppCompatActivity {

    public interface Callback {
        void onResult(Boolean success);
        void onError(Exception e);
        void canAuthenticate(Boolean enabled);
    }

    public static Callback callback;
    public static void SetCallback(Callback cb)
    {
        callback = cb;
    }

    public static String title;
    public static String negativeButtonText;
    public static String description;
    public static String subtitle;

    public static void Call(Activity activity)
    {
        // Creating an intent with the current activity and the activity we wish to start
        Intent myIntent = new Intent(activity, BiometricUnityActivity.class);
        activity.startActivity(myIntent);
    }

    public static void CanAuthenticate(Context context)
    {
        Goldfinger goldfinger = new Goldfinger.Builder(context).build();
        Boolean enabled = goldfinger.canAuthenticate(DEVICE_CREDENTIAL | BIOMETRIC_STRONG);
        callback.canAuthenticate(enabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_unity);

        Context context = this.getApplicationContext();
        Goldfinger goldfinger = new Goldfinger.Builder(context).build();
        if (goldfinger.canAuthenticate(DEVICE_CREDENTIAL | BIOMETRIC_STRONG)) {
            /* Authenticate */
            Goldfinger.PromptParams params = new Goldfinger.PromptParams.Builder(this)
                    .title(title)
                    .negativeButtonText(negativeButtonText)
                    .description(description)
                    .subtitle(subtitle)
                    .build();

            goldfinger.authenticate(params, new Goldfinger.Callback() {
                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                    finish();
                }

                @Override
                public void onResult(Goldfinger.Result result) {
                    if(result.type() == Goldfinger.Type.SUCCESS) {
                        callback.onResult(true);
                        finish();
                    }
                    else if(result.type() == Goldfinger.Type.ERROR) {
                        callback.onResult(false);
                        finish();
                    }
                }
            });
        }
    }
}