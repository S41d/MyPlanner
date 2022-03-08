package com.myplanner.myplanner;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.myplanner.myplanner.Database.UserDBHelper;
import com.myplanner.myplanner.Model.User;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
//    EditText username, password, email;
//    Button validate;
    UserDBHelper helper;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        username = findViewById(R.id.username);
//        password = findViewById(R.id.password);
//        email = findViewById(R.id.email);
//        validate = findViewById(R.id.validate);

//        helper = new UserDBHelper(this);

//        validate.setOnClickListener(event -> {
//            helper.insert(username.getText().toString(), email.getText().toString(), password.getText().toString());
//        });

//        ArrayList<String> listEntries = new ArrayList<>();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listEntries);
//        listView = findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
//        ArrayList<User> rep = helper.getAll();
//        for (User user: rep) {
//            listEntries.add(user.getUsername());
//        }
    }
}
