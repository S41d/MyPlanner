package com.myplanner.myplanner.controllers;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.R;

public class AlarmActivity extends AppCompatActivity {

    Button btnFermer;
    TextView titreView, descView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        btnFermer = findViewById(R.id.btnFermer);
        titreView = findViewById(R.id.titre);
        descView = findViewById(R.id.description);

        Intent intent = getIntent();
        String titre = intent.getStringExtra("titre");
        String description = intent.getStringExtra("description");
        titreView.setText(titre);
        descView.setText(description);

        btnFermer.setOnClickListener(view -> {
            startActivity(new Intent(this, AccueilActivity.class));
            finish();
        });

    }
}
