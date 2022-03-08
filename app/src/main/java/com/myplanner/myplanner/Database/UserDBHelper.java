package com.myplanner.myplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.myplanner.myplanner.Model.User;

import java.util.ArrayList;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myplanner.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "utilisateurs";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "username";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    public UserDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USER + "(" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT, " +
                USER_EMAIL + " TEXT UNIQUE, " +
                USER_PASSWORD + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean insert(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.putNull(USER_ID);
        contentValues.put(USER_NAME, "\"" + username + "\"");
        contentValues.put(USER_EMAIL, "\"" + email + "\"");
        contentValues.put(USER_PASSWORD, "\"" + password + "\"");
        long result = db.insert(TABLE_USER, null, contentValues);
        return result != -1;
    }

    public User get(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { USER_ID, USER_EMAIL, USER_NAME, };
        String whereClause = USER_EMAIL + " = ? AND " + USER_PASSWORD + " = ?";
        String[] whereArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, columns, whereClause, whereArgs, null, null, null);
        User user = new User(cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)));
        cursor.close();
        return user;
    }

    public ArrayList<User> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> listUser = new ArrayList<>();
        String[] columns = {
                USER_ID,
                USER_EMAIL,
                USER_NAME,
        };
        Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listUser.add(new User(cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL))));
            cursor.moveToNext();
        }
        cursor.close();
        return listUser;
    }
}
