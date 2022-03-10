package com.myplanner.myplanner.controllers;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.adapter.AlarmReceiver;
import com.myplanner.myplanner.database.TacheDBHelper;
import com.myplanner.myplanner.model.Tache;
import com.myplanner.myplanner.R;

import java.util.Calendar;

public class AjouterTaches extends AppCompatActivity {

    private DatePickerDialog jourDatePicker;
    private TimePickerDialog heureTimePicker;

    LinearLayout layout;
    EditText titreTache;
    EditText descriptionTache;
    EditText jourTache;
    EditText heureTache;
    Button btnAjoutTache;

    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_taches);
        createNotificationChannel();

        // desapparaitre le clavier quand on clique en dehors de editText
        layout = findViewById(R.id.layout_ajouter);
        layout.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });


        // recherche du contenu de chaque vue d'une tâche à ajouter
        titreTache = findViewById(R.id.editTextTitre);
        descriptionTache = findViewById(R.id.editTextDescription);
        jourTache = findViewById(R.id.editTextDate);
        heureTache = findViewById(R.id.editTextHeure);
        btnAjoutTache = findViewById(R.id.btnAjouterTache);

        TacheDBHelper data = new TacheDBHelper(this);

        //Affichage du calendrier
        jourTache.setOnClickListener(view -> {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            calendar.set(year, month, day);

            // date picker Dialogue
            jourDatePicker = new DatePickerDialog(this, (datePicker, year1, month1, dayOfMonth) -> {
                jourTache.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month1);
                calendar.set(Calendar.YEAR, year1);

            }, year, month, day);

            jourDatePicker.show();
        });

        //Affichage de la montre
        heureTache.setOnClickListener(view -> {
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            heureTimePicker = new TimePickerDialog(this, (timePicker, currentHour1, currentMinute1) -> {
                heureTache.setText(String.format("%02d:%02d ", currentHour1, currentMinute1));
                calendar.set(Calendar.HOUR_OF_DAY, currentHour1);
                calendar.set(Calendar.MINUTE, currentMinute1);
                calendar.set(Calendar.SECOND, 0);
            }, currentHour, currentMinute, false);
            heureTimePicker.show();
        });

        // action du bouton pour ajouter une tâche dans la base de données
        btnAjoutTache.setOnClickListener(view -> {
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                Toast.makeText(this, "Vous ne pouvez pas créer de tache dans le passé", Toast.LENGTH_SHORT).show();
            } else {
                Tache tache = new Tache(titreTache.getText().toString(),
                        descriptionTache.getText().toString(),
                        jourTache.getText().toString(),
                        heureTache.getText().toString());

                data.insertTache(tache);
                setAlarm(tache);
                Intent afficheListe = new Intent(AjouterTaches.this, AccueilActivity.class);
                startActivity(afficheListe);
            }
        });
    }

    public void setAlarm(Tache tache) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("titre", tache.getTitreTache());
        intent.putExtra("description", tache.getDescriptionTache());
        Log.d("alarm:titre", tache.getTitreTache());
        Log.d("alarm:desc", tache.getDescriptionTache());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "myplannerAlarmChannel";
            String description = "Channel des alarmes de MyPlanner";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("myplanner", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
