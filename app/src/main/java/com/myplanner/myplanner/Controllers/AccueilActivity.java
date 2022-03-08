package com.myplanner.myplanner.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.myplanner.myplanner.R;

import java.util.ArrayList;

public class AccueilActivity extends AppCompatActivity {
    ArrayList<String> taches = new ArrayList<>();
    ArrayAdapter<String> adapter;
    int counter = 0;

    RecyclerView tachesComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        tachesComponent = findViewById(R.id.tachesComponent);
        adapter = new ArrayAdapter<>(this, R.layout.layout_tache, taches);
    }

    void ajouterTaches(View view) {

    }
}