package com.example.timer;

public class MyConstans {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "my_db.db";
    public static final String TABLE_NAME = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_WORK = "work";
    public static final String KEY_MINUTES = "minutes";
    public static final String KEY_KALENDER = "kalender";

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WORK + " TEXT," +
            KEY_MINUTES + " TEXT," + KEY_KALENDER + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
