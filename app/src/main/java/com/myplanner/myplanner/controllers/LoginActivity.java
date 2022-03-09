package com.myplanner.myplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.R;
import com.myplanner.myplanner.database.UserDBHelper;
import com.myplanner.myplanner.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    // Declaration of global variables.
    EditText pass, email;
    Button register, login;
    ImageView eye;
    boolean state = false;

    // Default function call on creation of an activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Instantiate the View elements
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass1);
        eye = findViewById(R.id.toggle_view1);
        register = findViewById(R.id.register);
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
        UserDBHelper helper = new UserDBHelper(this);
        User user = helper.get(email.getText().toString(), pass.getText().toString());
        User.setUserConnecte(user);
    }

    // For toggling visibility of password.
    public void toggle(View v){
        if(!state){
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            // eye.setImageResource(R.drawable.eye);
        }
        else{
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            // eye.setImageResource(R.drawable.eye_off);
        }
        pass.setSelection(pass.getText().length());
        state = !state;
    }
}
