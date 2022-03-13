package com.myplanner.myplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.myplanner.myplanner.R;
import com.myplanner.myplanner.database.DBHelper;
import com.myplanner.myplanner.model.User;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    // Declaration of global variables.
    Button register;
    TextInputLayout emailLayout, passwordLayout, usernameLayout;
    TextInputEditText email, password, username;
    TextView loginView;
    boolean mdpVisible = false;

    // Default function call on creation of an activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Instantiate the View elements
        emailLayout = findViewById(R.id.email_layout_register);
        passwordLayout = findViewById(R.id.password_layout_register);
        usernameLayout = findViewById(R.id.username_layout_register);

        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        username = findViewById(R.id.username_register);

        register = findViewById(R.id.register);
        loginView = findViewById(R.id.login2);

        passwordLayout.setEndIconOnClickListener(view -> {
            mdpVisible = !mdpVisible;
            if (mdpVisible) {
                passwordLayout.setEndIconDrawable(R.drawable.eye);
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                passwordLayout.setEndIconDrawable(R.drawable.eye_off);
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        register.setOnClickListener(this::register);
        loginView.setOnClickListener(this::toLogin);
    }

    // Method for registration.
    public void register(View view) {
        DBHelper helper = new DBHelper(this);
        Editable emailVal = email.getText();
        Editable passVal = password.getText();
        Editable userVal = username.getText();
        if (emailVal == null || emailVal.toString().equals("")) {
            emailLayout.setError(getResources().getString(R.string.champ_vide));
        } else if (passVal == null || passVal.toString().equals("")) {
            passwordLayout.setError(getResources().getString(R.string.champ_vide));
        } else if (userVal == null || userVal.toString().equals("")) {
            usernameLayout.setError(getResources().getString(R.string.champ_vide));
        } else {
            passwordLayout.setErrorEnabled(false);
            emailLayout.setErrorEnabled(false);
            User user = helper.getUser(emailVal.toString(), passVal.toString());
            if (user == null) {
                helper.insertUser(userVal.toString(), emailVal.toString(), passVal.toString());
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, R.string.compte_deja_existe, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to start login activity.
    public void toLogin(View view) {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }
}
