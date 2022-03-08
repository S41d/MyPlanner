package com.myplanner.myplanner.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.R;

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
    String pass1, email1, link, reply, code;
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
        email1 = "";
        pass1 = "";
    }
    // Method to start registration activity.
    public void register(View view) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }

    // Method for login.
    public void login(View view) {
        // Get the inputs from the user.
        email1 = email.getText().toString();
        pass1 = pass.getText().toString();

        if(email1.isEmpty() || pass1.isEmpty()) {
            Toast.makeText(this, "Fields cannot be blank", Toast.LENGTH_SHORT).show();  // Check whether the fields are not blank
        }
        else {
            // Create various messages to display in the app.
            Toast failed_toast = Toast.makeText(this, "Request failed", Toast.LENGTH_SHORT);
            Toast incorrect_toast = Toast.makeText(this, "Credentials are incorrect", Toast.LENGTH_SHORT);
            Toast logged_toast = Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT);
            // Create a worker thread for sending HTTP requests.
            Thread thread = new Thread(() -> {
                link = "http://ip-address:8080/users/login";                                   // The private IP address of the machine is used
                try {
                    URL url = new URL (link);                                                       // new url object is created
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();              // HTTP connection object is created
                    conn.setRequestMethod("POST");                                                  // POST method
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");             // JSON format is specified
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    JSONObject input = new JSONObject();                                           // New JSON object is created
                    // Give data to the json object
                    input.put("email", email1);
                    input.put("password", pass1);
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());             // Output stream object for HTTP connection is created
                    os.writeBytes(input.toString());                                                // JSON object is serialized and sent over the HTTP connection to the listening server
                    os.flush();                                                                     // Flushing the output buffers
                    os.close();                                                                     // Closing the output stream
                    InputStream is = conn.getInputStream();                                         // Input stream object for HTTP connection is created
                    StringBuilder sb = new StringBuilder();                                           // String buffer object is created
                    // Fetch and append the incoming bytes until no more comes over the input stream.
                    try {
                        int chr;
                        while ((chr = is.read()) != -1) {
                            sb.append((char) chr);
                        }
                        reply = sb.toString();
                    } finally {
                        is.close();                                                                 // Closing the input stream
                    }
                    code = String.valueOf(conn.getResponseCode());                                  // Get the HTTP status code
                    conn.disconnect();                                                              // Disconnecting
                    // For unreachable network or other network related failures.
                    if (!code.equals("200")) {
                        failed_toast.show();
                    }
                    else {
                        // Generate an error message when the database has no matching data.
                        if (reply.equals("\"FAILURE\"")) {
                            incorrect_toast.show();
                        }
                        // Successfully login into the app.
                        else {
                            logged_toast.show();
                            Intent user = new Intent(this, WelcomeUserActivity.class);
                            user.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Destroy previous activities and clear Activity stack.
                            startActivity(user);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
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
