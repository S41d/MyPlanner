package com.myplanner.myplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.R;

public class WelcomeAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_app);
        Button connect = (Button) findViewById(R.id.connect);
        Button accueil = (Button) findViewById(R.id.accueil);

        connect.setOnClickListener(this::connect);
        accueil.setOnClickListener(this::accueil);

    }

    public void connect(View view) {
        Intent connect= new Intent(this, LoginActivity.class);
        startActivity(connect);
    }

    public void accueil(View view) {
        Intent accueil= new Intent(this, AccueilActivity.class);
        startActivity(accueil);

    }
}
