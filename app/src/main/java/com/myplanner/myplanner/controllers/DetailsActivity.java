package com.myplanner.myplanner.controllers;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.R;
import com.myplanner.myplanner.adapter.AlarmReceiver;
import com.myplanner.myplanner.database.TacheDBHelper;
import com.myplanner.myplanner.model.Tache;

import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    TacheDBHelper dbHelper;
    EditText titre, description, date, heure;
    Button modifier, supprimer;
    int idTache;
    LinearLayout layout;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        this.dbHelper = new TacheDBHelper(this);

        // desapparaitre le clavier quand on clique en dehors de editText
        layout = findViewById(R.id.layout_details);
        titre = findViewById(R.id.editTextTitre);
        description = findViewById(R.id.editTextDescription);
        date = findViewById(R.id.editTextDate);
        heure = findViewById(R.id.editTextHeure);
        modifier = findViewById(R.id.btnModifierTache);
        supprimer = findViewById(R.id.btnSupprimerTache);

        date.setOnClickListener(this::showDateDialog);
        heure.setOnClickListener(this::showHourDialog);
        layout.setOnClickListener(this::hideKeyboard);
        modifier.setOnClickListener(this::modifier);
        supprimer.setOnClickListener(this::supprimer);

        Intent intent = getIntent();
        if (intent != null) {
            this.titre.setText(intent.getStringExtra("titre"));
            this.description.setText(intent.getStringExtra("description"));
            this.date.setText(intent.getStringExtra("date"));
            this.heure.setText(intent.getStringExtra("heure"));
            this.idTache = Integer.parseInt(intent.getStringExtra("id"));
        }
    }

    private void showHourDialog(View view) {
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog heureTimePicker = new TimePickerDialog(this, (timePicker, currentHour1, currentMinute1) -> {
            heure.setText(String.format("%02d:%02d ", currentHour1, currentMinute1));
            calendar.set(Calendar.HOUR_OF_DAY, currentHour1);
            calendar.set(Calendar.MINUTE, currentMinute1);
            calendar.set(Calendar.SECOND, 0);
        }, currentHour, currentMinute, false);
        heureTimePicker.show();
    }

    private void showDateDialog(View view) {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, day);

        // date picker Dialogue
        DatePickerDialog datePicker = new DatePickerDialog(this, (datePickerArg, year1, month1, dayOfMonth) -> {
            date.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.MONTH, month1);
            calendar.set(Calendar.YEAR, year1);

        }, year, month, day);
        datePicker.show();
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void modifier(View view) {
        Tache tache = new Tache();
        tache.setId(idTache);
        tache.setTitreTache(this.titre.getText().toString());
        tache.setDescriptionTache(this.description.getText().toString());
        tache.setJourTache(this.date.getText().toString());
        tache.setHeureTache(this.heure.getText().toString());
        dbHelper.updateTache(tache);
        updateAlarm(tache);
        finish();
    }

    private void updateAlarm(Tache tache) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("titre", tache.getTitreTache());
        intent.putExtra("description", tache.getDescriptionTache());
        Log.d("alarm:titre", tache.getTitreTache());
        Log.d("alarm:desc", tache.getDescriptionTache());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, tache.getId(), intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void supprimerAlarm(Tache tache) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("titre", tache.getTitreTache());
        intent.putExtra("description", tache.getDescriptionTache());
        Log.d("alarm:titre", tache.getTitreTache());
        Log.d("alarm:desc", tache.getDescriptionTache());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, tache.getId(), intent, 0);
        alarmManager.cancel(pendingIntent);

    }

    private void supprimer(View view) {
        Tache tache = new Tache();
        tache.setId(idTache);
        tache.setTitreTache(this.titre.getText().toString());
        tache.setDescriptionTache(this.description.getText().toString());
        tache.setJourTache(this.date.getText().toString());
        tache.setHeureTache(this.heure.getText().toString());
        Log.d("tache", "tache supprimé: " + idTache);
        dbHelper.deleteTache(idTache);
        supprimerAlarm(tache);
        finish();
    }
}
