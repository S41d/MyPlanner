package com.myplanner.myplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.R;
import com.myplanner.myplanner.model.User;

public class WelcomeAppActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        User user = User.getUserConnecte(this);

        if (user != null) {
            setContentView(R.layout.activity_welcome_user);
            TextView usernameTextView = findViewById(R.id.username_placeholder);
            animateText(usernameTextView);
            usernameTextView.setText(user.getUsername());
            new Handler().postDelayed(() -> {
                Intent i = new Intent(this, AccueilActivity.class);
                startActivity(i);
                finish();
            }, 2000);

        } else {
            setContentView(R.layout.activity_welcome_app);
            Button connect = findViewById(R.id.connect);
            Button accueil = findViewById(R.id.accueil);

            connect.setOnClickListener(this::connect);
            accueil.setOnClickListener(this::accueil);
        }

        textView = findViewById(R.id.welcome_message);
        animateText(textView);

    }

    private void animateText(TextView textView) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        textView.startAnimation(animation);
    }

    public void connect(View view) {
        Intent connect = new Intent(this, LoginActivity.class);
        startActivity(connect);
        finish();
    }

    public void accueil(View view) {
        Intent accueil = new Intent(this, AccueilActivity.class);
        startActivity(accueil);
        finish();
    }
}
