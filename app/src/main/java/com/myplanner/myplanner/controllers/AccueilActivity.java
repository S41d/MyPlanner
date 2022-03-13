package com.myplanner.myplanner.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.myplanner.myplanner.R;
import com.myplanner.myplanner.adapter.RecyclerViewAdapter;
import com.myplanner.myplanner.adapter.RecyclerViewInterface;
import com.myplanner.myplanner.database.DBHelper;
import com.myplanner.myplanner.model.Tache;
import com.myplanner.myplanner.model.User;

import java.util.ArrayList;
import java.util.Calendar;

public class AccueilActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<Tache> taches = new ArrayList<>();
    ArrayList<Tache> tachesView = new ArrayList<>();

    DBHelper helper = new DBHelper(this);
    RecyclerViewAdapter adapter;
    CalendarView calendarView;
    RecyclerView recyclerView;
    Button ajouterTache;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onRestart() {
        super.onRestart();
        userConnectedCheck();
        tachesView = new ArrayList<>(taches);
        updateList(calendarView, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        recyclerView = findViewById(R.id.recycler_view);
        calendarView = findViewById(R.id.calendrier);
        ajouterTache = findViewById(R.id.ajouterTache);

        userConnectedCheck();

        tachesView = new ArrayList<>(taches);
        adapter = new RecyclerViewAdapter(this, tachesView, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // appel de la vue de l'ajout d'une nouvelle tâche
        ajouterTache.setOnClickListener(view -> startActivity(new Intent(this, AjouterTaches.class)));

        updateList(calendarView, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendarView.setOnDateChangeListener(this::updateList);
    }

    private void userConnectedCheck() {
        if (User.getUserConnecte(this) == null) {
            ajouterTache.setVisibility(View.INVISIBLE);
            taches = helper.getAllTaches();
        } else {
            ajouterTache.setVisibility(View.VISIBLE);
            taches = helper.getAllTaches(User.getUserConnecte(this).getId());
        }
    }

    private void updateList(CalendarView calendarView, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
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
        recyclerView.startLayoutAnimation();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(AccueilActivity.this, DetailsActivity.class);
        ArrayList<Tache> tachesAdapter = adapter.getTaches();
        intent.putExtra("id", String.valueOf(tachesAdapter.get(position).getId()));
        intent.putExtra("titre", tachesAdapter.get(position).getTitreTache());
        intent.putExtra("description", tachesAdapter.get(position).getDescriptionTache());
        intent.putExtra("date", tachesAdapter.get(position).getJourTache());
        intent.putExtra("heure", tachesAdapter.get(position).getHeureTache());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button_compte, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_open_login) {
            User user = User.getUserConnecte(this);
            if (user == null) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, ProfileActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
}