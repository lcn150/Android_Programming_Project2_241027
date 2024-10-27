package com.example.homework2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

//어플을 시작했을때 처음보이는 RestaurantCatalog엑티비티
public class RestaurantCatalog extends AppCompatActivity {
    final static String TAG="SQLITEDBTEST";

    private DBHelper RestaurantDbHelper;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("한성 맛집 앱");   //어플 이름 설정
        setContentView(R.layout.activity_main);          //xml에 화면

        RestaurantDbHelper = new DBHelper(this);

        viewAllToListViewRestaurant();  //실행후에 모든 값을 refresh해주는 역할
    }

    private void viewAllToListViewRestaurant() {  //현재 등록되어있는 맛집들을 보여주는 메소드
        cursor = RestaurantDbHelper.getAllUsersByMethod();  //맛집 커서
        SimpleCursorAdapter RestaurantAdapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.retaurant_item, cursor, new String[]{
                RestaunrantContract.Users.KEY_Restaurant_NAME,
                RestaunrantContract.Users.KEY_Restaurant_Home,
                RestaunrantContract.Users.KEY_Restaurant_Image,
               }, new int[]{R.id.restaurant_detail_home, R.id.restaurant_item_home,R.id.restaurant_icon}, 0);

        GridView lv = findViewById(R.id.gridview);
        lv.setAdapter(RestaurantAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //클릭했을때 RestaurantDetail 액티비티로 이동한다.
                cursor.moveToPosition(i);
                Intent intent = new Intent(RestaurantCatalog.this, RestaurantDetail.class);
                intent.putExtra("dataFromRestaurantCatalog0", cursor.getInt(0));
                //식당의 ID값만을 넣어서 RestaurantDetail 액티비티로 보낸다.

//                intent.putExtra("dataFromRestaurantCatalog1", cursor.getString(1));
//                intent.putExtra("dataFromRestaurantCatalog3", cursor.getString(3));
//                intent.putExtra("dataFromRestaurantCatalog4", cursor.getString(4));
//                intent.putExtra("dataFromRestaurantCatalog5", cursor.getString(5));

                startActivity(intent);
            }
        });
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  //옵션 앱바
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //옵션바 클릭했을 때
        if (item.getItemId() == R.id.quick_action1) {
            Intent intent = new Intent(RestaurantCatalog.this, RestaurantRegister.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}