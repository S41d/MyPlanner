package com.myplanner.myplanner.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import com.myplanner.myplanner.R;
import com.myplanner.myplanner.adapter.RecyclerViewAdapter;
import com.myplanner.myplanner.adapter.RecyclerViewInterface;
import com.myplanner.myplanner.database.TacheDBHelper;
import com.myplanner.myplanner.model.Tache;

import java.util.ArrayList;

public class AccueilActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<Tache> taches = new ArrayList<>();
    ArrayList<Tache> tachesView = new ArrayList<>();

    TacheDBHelper helper = new TacheDBHelper(this);
    RecyclerViewAdapter adapter;
    CalendarView calendarView;
    RecyclerView recyclerView;
    Button ajouterTache;

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        recyclerView = findViewById(R.id.recycler_view);
        calendarView = findViewById(R.id.calendrier);

        taches = helper.getAllTaches();
        tachesView = new ArrayList<>(taches);
        adapter = new RecyclerViewAdapter(this, tachesView, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // appel de la vue de l'ajout d'une nouvelle tâche
        ajouterTache = findViewById(R.id.ajouterTache);
        ajouterTache.setOnClickListener(view -> startActivity(new Intent(this, AjouterTaches.class)));

        calendarView.setOnDateChangeListener(this::changeDate);
    }

    private void changeDate(CalendarView calendarView, int year, int month, int day) {
        ArrayList<Tache> filtered = new ArrayList<>();
        for (int i = 0, tachesSize = taches.size(); i < tachesSize; i++) {
            Tache tache = taches.get(i);
            String tacheDate = tache.getJourTache();
            int d = Integer.parseInt(tacheDate.substring(0, tacheDate.indexOf("/")));
            int m = Integer.parseInt(tacheDate.substring(tacheDate.indexOf("/") + 1, tacheDate.lastIndexOf("/")));
            int y = Integer.parseInt(tacheDate.substring(tacheDate.lastIndexOf("/") + 1));
            if (y == year && m == (month + 1) && d == day) {
                filtered.add(tache);
            }
        }
        adapter.filterList(filtered);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(AccueilActivity.this, DetailsActivity.class);
        intent.putExtra("id", String.valueOf(taches.get(position).getId()));
        intent.putExtra("titre", taches.get(position).getTitreTache());
        intent.putExtra("description", taches.get(position).getDescriptionTache());
        intent.putExtra("date", taches.get(position).getJourTache());
        intent.putExtra("heure", taches.get(position).getHeureTache());
        startActivity(intent);
    }
}