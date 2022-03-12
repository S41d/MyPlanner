package com.myplanner.myplanner.controllers;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.myplanner.myplanner.R;
import com.myplanner.myplanner.database.DBHelper;
import com.myplanner.myplanner.helper.Alarm;
import com.myplanner.myplanner.helper.dialog.Dialog;
import com.myplanner.myplanner.helper.dialog.DialogType;
import com.myplanner.myplanner.model.Tache;

import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextInputEditText titreEditText, descriptionEditText, dateEditText, heureEditText;
    Button modifier, supprimer;
    int idTache;
    LinearLayout layout;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();

        this.dbHelper = new DBHelper(this);

        layout = findViewById(R.id.layout_details);

        titreEditText = findViewById(R.id.edit_text_titre);
        descriptionEditText = findViewById(R.id.edit_text_description);
        dateEditText = findViewById(R.id.edit_text_date);
        heureEditText = findViewById(R.id.edit_text_heure);

        modifier = findViewById(R.id.btnModifierTache);
        supprimer = findViewById(R.id.btnSupprimerTache);

        // desapparaitre le clavier quand on clique en dehors de editText
        layout.setOnClickListener(this::hideKeyboard);

        dateEditText.setOnClickListener(this::showDateDialog);
        heureEditText.setOnClickListener(this::showHourDialog);
        modifier.setOnClickListener(this::modifier);
        supprimer.setOnClickListener(this::supprimer);

        Intent intent = getIntent();
        if (intent != null) {
            this.titreEditText.setText(intent.getStringExtra("titre"));
            this.descriptionEditText.setText(intent.getStringExtra("description"));
            this.dateEditText.setText(intent.getStringExtra("date"));
            this.heureEditText.setText(intent.getStringExtra("heure"));
            this.idTache = Integer.parseInt(intent.getStringExtra("id"));
        }
    }

    private void showHourDialog(View view) {
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        @SuppressLint("DefaultLocale")
        TimePickerDialog heureTimePicker = new TimePickerDialog(this, R.style.datepicker, (timePicker, currentHour1, currentMinute1) -> {
            heureEditText.setText(String.format("%02d:%02d ", currentHour1, currentMinute1));
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
        @SuppressLint("SetTextI18n")
        DatePickerDialog datePicker = new DatePickerDialog(this, R.style.datepicker, (datePickerArg, year1, month1, dayOfMonth) -> {
            dateEditText.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
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
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            Toast.makeText(this, R.string.toast_erreur_temps, Toast.LENGTH_SHORT).show();
        } else {
            Tache tache = new Tache();
            tache.setId(idTache);
            tache.setTitreTache(this.titreEditText.getText().toString());
            tache.setDescriptionTache(this.descriptionEditText.getText().toString());
            tache.setJourTache(this.dateEditText.getText().toString());
            tache.setHeureTache(this.heureEditText.getText().toString());
            Dialog.showDialog(this, DialogType.MODIFICATION, () -> {
                dbHelper.updateTache(tache);
                Alarm alarm = new Alarm(this, tache);
                alarm.setAlarm(calendar.getTimeInMillis());
                finish();
            });
        }
    }


    private void supprimer(View view) {
        Tache tache = new Tache();
        tache.setId(idTache);
        tache.setTitreTache(this.titreEditText.getText().toString());
        tache.setDescriptionTache(this.descriptionEditText.getText().toString());
        tache.setJourTache(this.dateEditText.getText().toString());
        tache.setHeureTache(this.heureEditText.getText().toString());
        Log.d("tache", "tache supprimé: " + idTache);
        Dialog.showDialog(this, DialogType.SUPPRESSION, () -> {
            dbHelper.deleteTache(idTache);
            Alarm alarm = new Alarm(this, tache);
            alarm.supprimerAlarm();
            finish();
        });
    }
}
