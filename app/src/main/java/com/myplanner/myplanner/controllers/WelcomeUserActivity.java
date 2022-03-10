package com.myplanner.myplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.R;

public class WelcomeUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);
        Button accueil = (Button) findViewById(R.id.accueil);

        accueil.setOnClickListener(this::accueil);

    }


    //Method for beginning app
    public void accueil(View view) {
        Intent accueil= new Intent(this, AccueilActivity.class);
        startActivity(accueil);

    }


}
