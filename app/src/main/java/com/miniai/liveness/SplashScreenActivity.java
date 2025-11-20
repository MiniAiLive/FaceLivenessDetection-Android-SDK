package com.miniai.liveness;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_TIME = 3000L;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable launchNext = new Runnable() {
        @Override public void run() {
            Intent intent = new Intent(SplashScreenActivity.this, UserActivity.class);
            // Make the next Activity the root (clears Splash from back stack)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            // Safety: also finish Splash explicitly
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(launchNext, SPLASH_DISPLAY_TIME);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(launchNext); // avoid leaks
        super.onDestroy();
    }
}
