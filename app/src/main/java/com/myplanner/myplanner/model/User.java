package com.myplanner.myplanner.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class User {
    private static User userConnecte;
    private int id;
    private String username;
    private String email;
    private static final String sharedFileName = "User";

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User() {}

    public static User getUserConnecte(Context context) {
        if(userConnecte != null) {
            return userConnecte;
        } else {
            User user = new User();
            SharedPreferences preferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE);
            int id = preferences.getInt("id", -1);
            if (id == -1) {
                return null;
            }
            user.setId(preferences.getInt("id", -1));
            user.setUsername(preferences.getString("username", null));
            user.setEmail(preferences.getString("email", null));
            return user;
        }
    }

    public static void setUserConnecte(User userConnecte, Context context) {
        User.userConnecte = userConnecte;
        SharedPreferences preferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("username", userConnecte.getUsername());
        editor.putString("email", userConnecte.getEmail());
        editor.putInt("id", userConnecte.getId());
        editor.apply();
        Log.d("Connexion", "User connecté: " + userConnecte.username);
    }

    public static void deconnecter(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
        userConnecte = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
