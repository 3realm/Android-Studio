package com.example.timer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBMeneger {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBMeneger(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context); // инициализация
    }

    public void openDP(){
        database = dbHelper.getWritableDatabase(); // открытие базы данных
    }

    public void insertDB(String work, String minutes, String kalender){
        ContentValues contentValues = new ContentValues(); // создание одного рядка
        contentValues.put(MyConstans.KEY_WORK, work);
        contentValues.put(MyConstans.KEY_MINUTES, minutes);
        contentValues.put(MyConstans.KEY_KALENDER, kalender);

        database.insert(MyConstans.TABLE_NAME, null, contentValues);
    }

    public List<String> getDB(){
        List<String> list = new ArrayList<>(); // создание курсора
        Cursor cursor = database.query(MyConstans.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        // проверка движения по базе данных
        while (cursor.moveToNext()){
            @SuppressLint("Range") String text_work = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_WORK)); // получение индекса
            @SuppressLint("Range") String text_minutes = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_MINUTES));
            @SuppressLint("Range") String text_kalender = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_KALENDER));
            list.add(text_work + "   |    " + text_minutes + "   |    " + text_kalender  );
        }
        cursor.close();
        return list;
    }

    public void closeDB(){
        dbHelper.close();
    }

}
