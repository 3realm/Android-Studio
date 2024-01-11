package com.example.dictinary;

public class MyConstans {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "my_db_dic.db";
    public static final String TABLE_NAME = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_DEUTSCH = "deutsch";
    public static final String KEY_RUSSIAN = "russian";

    public static final String KEY_DEUTSCH_SORT = "deutsch ASC";

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DEUTSCH + " TEXT," +
            KEY_RUSSIAN + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
