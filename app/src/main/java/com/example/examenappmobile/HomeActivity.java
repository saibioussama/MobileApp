package com.example.examenappmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class HomeActivity extends AppCompatActivity {

    LottieAnimationView ReadMoreAnim;
    LottieAnimationView MyAnimation;
    TextView ReadMoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.orange));

        MyAnimation = findViewById(R.id.MyAnimation);
        ReadMoreAnim = findViewById(R.id.ReadMoreAnim);
        ReadMoreTextView = findViewById(R.id.ReadMoreTextView);
        ReadMoreAnim.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ReadMoreTextView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyAnimation.setProgress(0);
        MyAnimation.animate();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyAnimation.setProgress(0);
        MyAnimation.animate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyAnimation.setProgress(0);
        MyAnimation.animate();
    }
}