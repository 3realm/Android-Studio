package com.example.dictinary;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    // Открытие
    public void openDP(){
        database = dbHelper.getWritableDatabase(); // открытие базы данных
    }
    // Запись
    public void insertDB(String deutsch, String russian){
        ContentValues contentValues = new ContentValues(); // создание одного рядка
        contentValues.put(MyConstans.KEY_DEUTSCH, deutsch);
        contentValues.put(MyConstans.KEY_RUSSIAN, russian);

        database.insert(MyConstans.TABLE_NAME, null, contentValues); // запись
    }
    // Удаление всего списка
    public void deleteDB(){
        database.delete(MyConstans.TABLE_NAME, null, null);
    }
    // Сортировка
    public List<String> sortDB() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.query(MyConstans.TABLE_NAME,
                new String[]{MyConstans.KEY_ID, MyConstans.KEY_DEUTSCH, MyConstans.KEY_RUSSIAN},
                null,
                null,
                null,
                null,
                MyConstans.KEY_DEUTSCH_SORT,
                null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String text_deutsch = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_DEUTSCH)); // получение индекса
            @SuppressLint("Range") String text_russian = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_RUSSIAN));
            list.add(text_deutsch + "\t" + text_russian);
        }
        cursor.close();
        return list;
    }
    // Удаление по индексу
    public void deletevalueDB(String deutsch){
         database.delete(MyConstans.TABLE_NAME, MyConstans.KEY_DEUTSCH + " = ?", new String[]{deutsch});
    }
    // Получение данных по данным
    public List<String> getvalueDB2(String deutsch){
        List<String> list = new ArrayList<>();
        // создание курсора
        Cursor cursor = database.query(MyConstans.TABLE_NAME,
                new String[]{MyConstans.KEY_DEUTSCH, MyConstans.KEY_RUSSIAN},
                MyConstans.KEY_DEUTSCH + " = ?",
                new String[]{deutsch},
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()){
            //@SuppressLint("Range") String text_deutsch = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_DEUTSCH));
            @SuppressLint("Range") String text_russian = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_RUSSIAN));
            list.add(text_russian);
        }
        cursor.close();
        return list;
    }
    // Получение данных
    public List<String> getvalueDB(){
        List<String> list = new ArrayList<>();
        // создание курсора
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
            @SuppressLint("Range") String text_deutsch = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_DEUTSCH)); // получение индекса
            list.add(text_deutsch);
        }
        cursor.close();
        return list;
    }
    // Получение данных
    public List<String> getDB(){
        List<String> list = new ArrayList<>();
        // создание курсора
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
            @SuppressLint("Range") String text_deutsch = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_DEUTSCH)); // получение индекса
            @SuppressLint("Range") String text_russian = cursor.getString(cursor.getColumnIndex(MyConstans.KEY_RUSSIAN));
            list.add(text_deutsch + "     " + text_russian);
        }
        cursor.close();
        return list;
    }
    // Закрытие
    public void closeDB(){
        dbHelper.close();
    }
}