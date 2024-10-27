package com.example.homework2;

import android.provider.BaseColumns;

//식당 데이터베이스 정의
public final class RestaunrantContract {
    public static final String Restaurant_DB_NAME ="aacc.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private RestaunrantContract() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String Restaurant_TABLE_NAME ="Users";
        public static final String KEY_Restaurant_NAME = "Name";
        public static final String KEY_Restaurant_Home = "Home";
        public static final String KEY_Restaurant_Phone = "Phone";
        public static final String KEY_Restaurant_Time = "Time";
        public static final String KEY_Restaurant_Image = "Image";

        public static final String CREATE_TABLE = "CREATE TABLE " + Restaurant_TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_Restaurant_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_Restaurant_Home + TEXT_TYPE + COMMA_SEP+
                KEY_Restaurant_Phone + TEXT_TYPE + COMMA_SEP+
                KEY_Restaurant_Time + TEXT_TYPE +COMMA_SEP+
                KEY_Restaurant_Image + TEXT_TYPE +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + Restaurant_TABLE_NAME;
    }


}