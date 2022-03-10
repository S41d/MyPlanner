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

public class RegisterActivity extends AppCompatActivity {
    // Declaration of global variables.
    EditText name, pass, email;
    Button register;
    ImageView eye;
    boolean state = false;
    private View login;

    // Default function call on creation of an activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);                                  // Setting the content view
        // Instantiate the View elements
        email = findViewById(R.id.email2);
        pass = findViewById(R.id.pass2);
        name = findViewById(R.id.user2);
        eye = findViewById(R.id.toggle_view2);
        login = findViewById(R.id.login);

        register.setOnClickListener(this::register);
        login.setOnClickListener(this::login);
    }

    // Method for registration.

    public void register(View view) {
        UserDBHelper helper = new UserDBHelper(this);
        boolean create = helper.insert(name.getText().toString(), email.getText().toString(), pass.getText().toString());

    }

    // For toggling visibility of password.
    public void toggle(View v){
        if(!state){
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pass.setSelection(pass.getText().length());
            // eye.setImageResource(R.drawable.eye);
        }
        else{
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pass.setSelection(pass.getText().length());
            //eye.setImageResource(R.drawable.eye_off);
        }
        state = !state;
    }
    // Method to start login activity.
    public void login(View view) {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }
}
