package com.myplanner.myplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.adapter.TacheListAdapter;
import com.myplanner.myplanner.database.TacheDBHelper;
import com.myplanner.myplanner.model.Tache;
import com.myplanner.myplanner.R;

import java.util.ArrayList;

public class ListeTaches extends AppCompatActivity {

    ListView listView;
    TacheDBHelper data = new TacheDBHelper(ListeTaches.this);


    @Override
    protected void onResume() {
        super.onResume();
        render();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_taches);
        render();
    }

    public void render() {
        listView = findViewById(R.id.listeTache);

        ArrayList<Tache> allTaches = data.getAllTaches();
        TacheListAdapter adapter = new TacheListAdapter(this, R.layout.layout_tache, allTaches);
        for (int i = 0; i < listView.getChildCount(); i++) {
            View child = listView.getChildAt(i);
            child.setClickable(true);
            child.setFocusable(true);
            child.setEnabled(true);
            child.setOnClickListener(view -> Log.d("child", "clicked"));
        }
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("parent", "clicked");
        });
        listView.setFocusable(true);
        listView.setEnabled(true);

        listView.setAdapter(adapter);

//        listView.setOnItemClickListener((adapterView, view, i, l) -> {
//            int id = allTaches.get(view.getId()).getId();
//            Intent affichageDetails = new Intent(ListeTaches.this, DetailsActivity.class);
////            affichageDetails.putExtra("id", String.valueOf(id));
//            startActivity(affichageDetails);
//        });


        // appel de la vue de l'ajout d'une nouvelle tâche
        TextView eTextAjoutTache = findViewById(R.id.ajouterTache);
        eTextAjoutTache.setOnClickListener(view -> {
            Intent intentActivity1 = new Intent(getApplicationContext(), AjouterTaches.class);
            startActivity(intentActivity1);
        });
    }
}
