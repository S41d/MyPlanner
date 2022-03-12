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

public class LoginActivity extends AppCompatActivity {
    // Declaration of global variables.

    TextInputLayout emailLayout, passwordLayout;
    TextInputEditText email, password;
    Button login;
    TextView register;
    boolean mdpVisible = false;

    // Default function call on creation of an activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        // Instantiate the View elements
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

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
        register = findViewById(R.id.register2);
        login = findViewById(R.id.login);

        register.setOnClickListener(this::register);
        login.setOnClickListener(this::login);
    }

    // Method to start registration activity.
    public void register(View view) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }

    public void login(View view) {
        DBHelper helper = new DBHelper(this);
        Editable emailVal = email.getText();
        Editable passVal = password.getText();
        if (emailVal == null || emailVal.toString().equals("")) {
            emailLayout.setError(getResources().getString(R.string.champ_vide));
        } else if (passVal == null || passVal.toString().equals("")) {
            passwordLayout.setError(getResources().getString(R.string.champ_vide));
        } else {
            passwordLayout.setErrorEnabled(false);
            emailLayout.setErrorEnabled(false);
            User user = helper.getUser(emailVal.toString(), passVal.toString());
            if (user == null) {
                Toast.makeText(this, R.string.compte_inexistant, Toast.LENGTH_SHORT).show();
            } else {
                User.setUserConnecte(user, this);
                finish();
            }
        }
    }
}
