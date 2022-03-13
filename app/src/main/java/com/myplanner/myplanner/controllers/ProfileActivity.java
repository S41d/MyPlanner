package com.myplanner.myplanner.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.myplanner.myplanner.R;
import com.myplanner.myplanner.model.User;

public class ProfileActivity extends AppCompatActivity {

    Button btnDeconnexion;
    TextInputEditText email, username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        btnDeconnexion = findViewById(R.id.deconnexion);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);

        User user = User.getUserConnecte(this);
        email.setText(user.getEmail());
        username.setText(user.getUsername());

        btnDeconnexion.setOnClickListener(this::deconnexion);
    }

    private void deconnexion(View view) {
        User.deconnecter(this);
        finish();
    }
}