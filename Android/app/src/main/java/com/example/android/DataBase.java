package com.example.android;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private static final String db_name = "androidBD";
    private static final int db_ver = 1;
    private static final String db_table = "abbreviation";
    private static final String db_column1 = "link";
    private static final String db_column2 = "abbName";



    //конструктор бд
    public DataBase(Context context) {
        super(context, db_name, null, db_ver);
    }

    //создание самой бд
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL);", db_table, db_column1, db_column2);
        db.execSQL(query);

    }

    //изменение бд
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", db_table);
        db.execSQL(query);
        onCreate(db);
    }

    //запись в бд
    public void insertData (String link, String abbName) {
        //получили бд
        SQLiteDatabase db = this.getWritableDatabase();
        //объект
        ContentValues values = new ContentValues();
        values.put(db_column1, link);
        values.put(db_column2, abbName);
        db.insertWithOnConflict(db_table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    //получеие данных из бд
    public ArrayList<String> getAllAbb (){
        ArrayList<String> all_abb = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_table, null, null, null , null , null , null);
        while (cursor.moveToNext()){
            int indexCol2 = cursor.getColumnIndex(db_column2);
            all_abb.add(cursor.getString(indexCol2));
        }
        cursor.close();
        db.close();
        return all_abb;
    }

    //получение линка на основе передаваемой абб
    public String getItemDB(String abb){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_table, null, db_column2 + " = ?", new String[]{abb}, null , null , null);
        String link = null;
        while (cursor.moveToNext()){
            link = cursor.getString(cursor.getColumnIndex(db_column1));
        }
        cursor.close();
        db.close();
        return link;
    }

    //удаление опред. строки
    public void deleteData(String abb) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(db_table, db_column2 + " = ?", new String[]{abb});
        db.close();
    }

    //полная очистка таблицы
    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(db_table, null, null);
    }


    public boolean EqualsDB (String abb){
        boolean Equal = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_table, null, db_column2 + " = ?", new String[]{abb}, null , null , null);
        while (cursor.moveToNext()){
            Equal = true;
        }
        cursor.close();
        db.close();
        return Equal;
    }


}
