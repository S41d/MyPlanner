package com.myplanner.myplanner.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.Database.TacheDBHelper;
import com.myplanner.myplanner.Model.Tache;
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

//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(ListeTaches.this, R.layout.item_taches, allTaches,
//                new String[]{allTaches.getColumnName(0), allTaches.getColumnName(1), allTaches.getColumnName(2), allTaches.getColumnName(3), allTaches.getColumnName(4)},
//                new int[]{R.id.idTache, R.id.titreTache, R.id.descriptionTache, R.id.jourTache, R.id.heureTache}, 1);

//        listView.setAdapter(adapter);


        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            TextView idView = view.findViewById(R.id.idTache);
            Intent affichageDetails = new Intent(ListeTaches.this, DetailsActivity.class);
            affichageDetails.putExtra("id", idView.getText().toString());
            startActivity(affichageDetails);
        });


        // appel de la vue de l'ajout d'une nouvelle tâche
        TextView eTextAjoutTache = findViewById(R.id.ajouterTache);
        eTextAjoutTache.setOnClickListener(view -> {
            Intent intentActivity1 = new Intent(getApplicationContext(), AjouterTaches.class);
            startActivity(intentActivity1);
        });
    }
}
