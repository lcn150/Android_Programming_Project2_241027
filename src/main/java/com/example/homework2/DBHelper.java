package com.example.homework2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//식당 데이터베이스를 만들기 위한 DBHelper
public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="RestaurantSQLiteDBTest";

    public DBHelper(Context context) {
        super(context, RestaunrantContract.Restaurant_DB_NAME, null, RestaunrantContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(RestaunrantContract.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(RestaunrantContract.Users.DELETE_TABLE);
        onCreate(db);
    }
    //insert하기위한 메소드방식
    public long insertRestaurantByMethod(String name, String home, String phone, String time, String image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RestaunrantContract.Users.KEY_Restaurant_NAME, name);
        values.put(RestaunrantContract.Users.KEY_Restaurant_Home, home);
        values.put(RestaunrantContract.Users.KEY_Restaurant_Phone, phone);
        values.put(RestaunrantContract.Users.KEY_Restaurant_Time, time);
        values.put(RestaunrantContract.Users.KEY_Restaurant_Image, image);

        return db.insert(RestaunrantContract.Users.Restaurant_TABLE_NAME,null,values);
    }

    public Cursor getAllUsersByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(RestaunrantContract.Users.Restaurant_TABLE_NAME,null,null,null,null,null,null);
    }

    public Cursor getAllUsersBySQL(int resId) {  //데이터베이스에 저장되어있는 식당을 가져오기 위한 SQL
        String sql = "Select * FROM " + RestaunrantContract.Users.Restaurant_TABLE_NAME + " WHERE " + RestaunrantContract.Users._ID + " = "+resId;
        return getReadableDatabase().rawQuery(sql,null);
    }
}