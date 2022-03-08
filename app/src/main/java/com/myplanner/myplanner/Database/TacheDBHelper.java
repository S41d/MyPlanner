package com.myplanner.myplanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.myplanner.myplanner.Model.Tache;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TacheDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myplanner";
    public static final String TABLE_TACHE = "tache";

    public static final String TACHE_ID = "_id";
    public static final String TITRE_TACHE = "titre_tache";
    public static final String DESCRIPTION_TACHE = "descripton_tache";
    public static final String JOUR_TACHE = "jour_tache";
    public static final String HEURE_TACHE = "heure_tache";


    public TacheDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* La requête de création de la structure de la base de données. */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE table_tache(" + TACHE_ID + " INTEGER PRIMARY KEY, " +
                TITRE_TACHE + " TEXT NOT NULL, " +
                DESCRIPTION_TACHE + " TEXT NOT NULL, " +
                JOUR_TACHE + " TEXT NOT NULL, " +
                HEURE_TACHE + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS table_tache");
        onCreate(db);
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
        Cursor cursor = db.rawQuery("SELECT * FROM table_tache", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(new Tache(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)));
        }
        cursor.close();
        return arrayList;
    }

    public Tache getTache(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("table_tache",
                new String[]{TACHE_ID, TITRE_TACHE, DESCRIPTION_TACHE, JOUR_TACHE, HEURE_TACHE},
                TACHE_ID + " = ? ",
                new String[]{String.valueOf(id)}, null, null, "heureTache");
        c.moveToFirst();
        Tache tache = new Tache(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
        c.close();
        return tache;
    }
}
