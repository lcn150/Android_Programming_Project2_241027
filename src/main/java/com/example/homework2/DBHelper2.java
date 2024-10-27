package com.example.homework2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//메뉴 데이터베이스를 만들기 위한 DBHelper2
public class DBHelper2 extends SQLiteOpenHelper {
    final static String TAG="MenuSQLiteDBTest";

    public DBHelper2(Context context2) {
        super(context2, MenuContract.MenuDB_Name, null, MenuContract.DATABASE_VERSION2);
    }

    @Override
    public void onCreate(SQLiteDatabase db2) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db2.execSQL(MenuContract.Menu.Menu_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db2, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db2.execSQL(MenuContract.Menu.DELETE_TABLE2);
        onCreate(db2);
    }

    public Cursor getAllUsersBySQL(int resId) {  //따로 메뉴를 삽입하기위해 식당의 ID 식별
        String sql = "Select * FROM " + MenuContract.Menu.Menu_Table_Name +" WHERE "+ MenuContract.Menu.KEY_Res_Id +" = "+resId;
       return getReadableDatabase().rawQuery(sql,null);
    }

    public long insertMenuByMethod2(String menu, String price, String explain, String menuimage, int resId) {
        SQLiteDatabase db2 = getWritableDatabase();
        ContentValues values2 = new ContentValues();
        values2.put(MenuContract.Menu.KEY_MENU_Name, menu);
        values2.put(MenuContract.Menu.KEY_MENU_PRICE, price);
        values2.put(MenuContract.Menu.KEY_MENU_EXPLAIN, explain);
        values2.put(MenuContract.Menu.KEY_Menu_Image, menuimage);
        values2.put(MenuContract.Menu.KEY_Res_Id, resId);
        //맛집ID를 이용하여 데이터베이스가 작동할 수 있다.


        return db2.insert(MenuContract.Menu.Menu_Table_Name,null,values2);
    }
}