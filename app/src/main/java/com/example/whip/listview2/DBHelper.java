package com.example.whip.listview2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.whip.listview2.toutv.LineupItems;
import com.example.whip.listview2.toutv.Lineups;

/**
 * Created by Maude on 2018-02-07.
 */

public class DBHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "films.db";
    static final int DB_VERSION = 1;

    static final String TABLE_FILMS = "films";
    static final String F_ID = "_id";
    static final String F_TITLE = "title";
    static final String F_INFO = "info";

    private static SQLiteDatabase db = null;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        if (db==null){
            db = getWritableDatabase();
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_FILMS
                +" ( "+F_ID + " integer primary key, "
                + F_TITLE+ " text, "
                +F_INFO+ " text )";
        Log.d("SQL",sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_FILMS);
        onCreate(db);
    }

    public int ajouteFilms(Lineups films){
        int nb = 0;
        LineupItems film;
        ContentValues cv = new ContentValues();
        for (int i =0; i<films.LineupItems.size();i++){
            film = films.LineupItems.get(i);
            cv.clear();
            cv.put(F_ID,i);
            cv.put(F_TITLE,film.GenreTitle);
            cv.put(F_INFO,film.Details.Description);
            try{
                db.insertOrThrow(TABLE_FILMS, null, cv);
                nb++;
            } catch (SQLException e){

            }
        }
        return nb;
    }

    public Cursor listeFilms(){
        Cursor c;
        c = db.rawQuery("select * from " + TABLE_FILMS + " ORDER BY " + F_TITLE + " ASC",null);
        return c;
    }
}
