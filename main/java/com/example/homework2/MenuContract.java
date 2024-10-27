package com.example.homework2;

import android.provider.BaseColumns;
//메뉴 데이터베이스 정의
public final class MenuContract {
    public static final String MenuDB_Name ="llkk.db";
    public static final int DATABASE_VERSION2 = 2;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private MenuContract() {}

    /* Inner class that defines the table contents */
    public static class Menu implements BaseColumns {
        public static final String Menu_Table_Name ="Users";
        public static final String KEY_MENU_Name = "Menu";
        public static final String KEY_MENU_PRICE = "Price";
        public static final String KEY_MENU_EXPLAIN = "Explain";
        public static final String KEY_Menu_Image = "Image2";
        public static final String KEY_Res_Id = "ResId";  //식당을 식별하기 위한 ID

        public static final String Menu_CREATE_TABLE = "CREATE TABLE " + Menu_Table_Name + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_MENU_Name + TEXT_TYPE + COMMA_SEP +
                KEY_MENU_PRICE + TEXT_TYPE + COMMA_SEP+
                KEY_MENU_EXPLAIN + TEXT_TYPE + COMMA_SEP+
                KEY_Menu_Image + TEXT_TYPE + COMMA_SEP+
                KEY_Res_Id + TEXT_TYPE+
                " )";
        public static final String DELETE_TABLE2 = "DROP TABLE IF EXISTS " + Menu_Table_Name;
    }
}