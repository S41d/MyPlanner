package com.myplanner.myplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.R;
import com.myplanner.myplanner.model.User;

public class WelcomeAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        User user = User.getUserConnecte(this);

        if (user != null) {
            setContentView(R.layout.activity_welcome_user);
            TextView usernameTextView = findViewById(R.id.username_placeholder);
            usernameTextView.setText(user.getUsername());
            new Handler().postDelayed(() -> {
                Intent i = new Intent(this, AccueilActivity.class);
                startActivity(i);
                finish();
            }, 2000);

        } else {
            setContentView(R.layout.activity_welcome_app);
            Button connect = (Button) findViewById(R.id.connect);
            Button accueil = (Button) findViewById(R.id.accueil);

            connect.setOnClickListener(this::connect);
            accueil.setOnClickListener(this::accueil);
        }
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
