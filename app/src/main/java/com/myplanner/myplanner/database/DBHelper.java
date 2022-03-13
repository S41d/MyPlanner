package com.myplanner.myplanner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.myplanner.myplanner.model.Tache;
import com.myplanner.myplanner.model.User;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myplanner";
    public static final String TABLE_TACHE = "tache";
    public static final String TABLE_USER = "user";

    public static final String TACHE_ID = "_id";
    public static final String TITRE_TACHE = "titre_tache";
    public static final String DESCRIPTION_TACHE = "descripton_tache";
    public static final String JOUR_TACHE = "jour_tache";
    public static final String HEURE_TACHE = "heure_tache";

    public static final String USER_ID = "id";
    public static final String USER_NAME = "username";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* La requête de création de la structure de la base de données. */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TACHE + "(" +
                TACHE_ID + " INTEGER PRIMARY KEY, " +
                TITRE_TACHE + " TEXT NOT NULL, " +
                DESCRIPTION_TACHE + " TEXT NOT NULL, " +
                JOUR_TACHE + " TEXT NOT NULL, " +
                HEURE_TACHE + " TEXT NOT NULL);");

        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT NOT NULL, " +
                USER_EMAIL + " TEXT UNIQUE NOT NULL, " +
                USER_PASSWORD + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TACHE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.putNull(USER_ID);
        contentValues.put(USER_NAME, username);
        contentValues.put(USER_EMAIL, email);
        contentValues.put(USER_PASSWORD, password);
        long result = db.insert(TABLE_USER, null, contentValues);
    }


    public User getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = USER_EMAIL + " LIKE ? AND " + USER_PASSWORD + " LIKE ?";
        String[] whereArgs = new String[]{email, password};
        String[] columns = {
                USER_ID,
                USER_EMAIL,
                USER_NAME,
        };

        Cursor cursor = db.query(TABLE_USER, columns, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) return null;
        cursor.moveToFirst();
        User user = new User(cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)));
        cursor.close();
        return user;
    }

    public ArrayList<User> getAllUsers() {
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

    public void insertTache(Tache t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITRE_TACHE, t.getTitreTache());
        cv.put(DESCRIPTION_TACHE, t.getDescriptionTache());
        cv.put(JOUR_TACHE, t.getJourTache());
        cv.put(HEURE_TACHE, t.getHeureTache());
        db.insert(TABLE_TACHE, null, cv);
        db.close();
    }

    public void updateTache(Tache t) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITRE_TACHE, t.getTitreTache());
        cv.put(DESCRIPTION_TACHE, t.getDescriptionTache());
        cv.put(JOUR_TACHE, t.getJourTache());
        cv.put(HEURE_TACHE, t.getHeureTache());
        db.update(TABLE_TACHE, cv, "_id=?", new String[]{String.valueOf(t.getId())});
        db.close();
    }

    public void deleteTache(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TACHE, "_id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<Tache> getAllTaches() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Tache> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TACHE + ";", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(new Tache(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)));
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public Tache getTache(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_TACHE,
                new String[]{TACHE_ID, TITRE_TACHE, DESCRIPTION_TACHE, JOUR_TACHE, HEURE_TACHE},
                TACHE_ID + " = ? ",
                new String[]{String.valueOf(id)}, null, null, HEURE_TACHE);
        c.moveToFirst();
        Tache tache = new Tache(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
        c.close();
        return tache;
    }
}
