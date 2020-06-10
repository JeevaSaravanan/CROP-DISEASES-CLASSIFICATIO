package com.insight.farmlenz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.insight.farmlenz.Login.loginpage;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView img;
    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img = findViewById(R.id.img);
        frombottom=AnimationUtils.loadAnimation(this,R.anim.frombottom);
        img.setAnimation(frombottom);
        img.animate().alpha(4000).setDuration(0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp = new Intent(SplashActivity.this, loginpage.class);
                finish();
                startActivity(dsp);
            }
        },4000);
    }
}