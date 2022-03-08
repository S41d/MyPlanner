package com.myplanner.myplanner.Controllers;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.Database.TacheDBHelper;
import com.myplanner.myplanner.Model.Tache;
import com.myplanner.myplanner.R;

import java.util.Calendar;

public class AjouterTaches extends AppCompatActivity {

    private DatePickerDialog jourDatePicker;
    private TimePickerDialog heureTimePicker;

    EditText titreTache;
    EditText descriptionTache;
    EditText jourTache;
    EditText heureTache;
    Button btnAjoutTache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_taches);

        // recherche du contenu de chaque vue d'une tâche à ajouter
        titreTache = findViewById(R.id.editTextTitre);
        descriptionTache = findViewById(R.id.editTextDescription);
        jourTache = findViewById(R.id.editTextDate);
        heureTache = findViewById(R.id.editTextHeure);
        btnAjoutTache = findViewById(R.id.bntAjouterTache);

        TacheDBHelper data = new TacheDBHelper(this);

        //Affichage du calendrier
        jourTache.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            calendar.set(year, month, day);

            // date picker Dialogue
            jourDatePicker = new DatePickerDialog(this, (datePicker, year1, month1, dayOfMonth) -> {
                jourTache.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
            }, year, month, day);

            jourDatePicker.show();
        });

        //Affichage de la montre
        heureTache.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            heureTimePicker = new TimePickerDialog(this, (timePicker, currentHour1, currentMinute1) -> {
                String amPm;
                if (currentHour1 >= 12) {
                    amPm = "AM";
                } else {
                    amPm = "PM";
                }
                heureTache.setText(String.format("%02d:%02d", currentHour1, currentMinute1) + amPm);
                //heureTache.setText(currentHour + ":" + currentMinute + amPm);
            }, currentHour, currentMinute, false);
            heureTimePicker.show();
        });

        // action du bouton pour ajouter une tâche dans la base de données
        btnAjoutTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tache tache = new Tache(titreTache.getText().toString(),
                        descriptionTache.getText().toString(),
                        jourTache.getText().toString(),
                        heureTache.getText().toString());

                data.insertTache(tache);
                Intent afficheListe = new Intent(AjouterTaches.this, ListeTaches.class);
                startActivity(afficheListe);
            }
        });
    }
}
