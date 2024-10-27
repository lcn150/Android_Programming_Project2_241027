package com.example.homework2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuRegister extends AppCompatActivity {
    private DBHelper2 MenuDBHelper;
    final static String TAG="SQLITEDBTEST";

    private File Menu_mPhotoFile =null;
    private String Menu_PhotoFileName = null;
    static final int Menu_REQUEST_IMAGE_CAPTURE = 1;
    final int  REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA2=1;
    int resto; //식당ID를 식별하기 위한 변수

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("한성 맛집 앱");   //어플 이름 설정
        setContentView(R.layout.menu_register);
        checkDangerousPermissions();

        Intent resIDintent = getIntent();  //맛집의 ID를 불러온다.
        resto = resIDintent.getIntExtra("dataFromRestaurantDetail",-1);

        Button registermenubutton = (Button)findViewById(R.id.menu_register_button);//버튼을 누르면 메뉴 요소들이 삽입
        registermenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMenuRecord();
                Intent insertMenuintent = new Intent(MenuRegister.this, RestaurantDetail.class);
                insertMenuintent.putExtra("dataFromRestaurantCatalog0", resto);
                //맛집의 ID를 넣어서 보내준다.
                startActivity(insertMenuintent);
                finish();
            }
        });

        ImageButton menuimage_registerbutton = (ImageButton)findViewById(R.id.menu_Image_register_button);  //메뉴 이미지 추가 버튼
        menuimage_registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //메뉴 카메라버튼
                dispatchTakePictureIntent();   //카메라 앱으로 감
            }
        });
    }

    private void dispatchTakePictureIntent() {   //카메라 앱을 실행하기위한 요청
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //1. 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
            Menu_PhotoFileName = "IMG"+currentDateFormat()+".png";
            Menu_mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), Menu_PhotoFileName);

            if (Menu_mPhotoFile !=null) {
                //2. 생성된 파일 객체에 대한 Uri 객체를 얻기
                Uri imageUri = FileProvider.getUriForFile(this, "com.hansung.android.homework2", Menu_mPhotoFile);

                //3. Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(takePictureIntent, Menu_REQUEST_IMAGE_CAPTURE);
            } else    //파일 없음
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();
        }
    }

    private String currentDateFormat(){  //현재 날짜, 시간 타입을 알려주기위해
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void checkDangerousPermissions() {  //기기의 보호되는 기능을 사용하려면 check(보안기능)
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA2);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //찍은 사진을 처리하기위한 함수
        if (requestCode == Menu_REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) { //이미지를 찍고 OK를 눌렀을 때
            if (Menu_PhotoFileName != null) {
                Menu_mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), Menu_PhotoFileName);
                ImageButton Imagebutton2 = findViewById(R.id.menu_Image_register_button);   //이미지 버튼에
                Imagebutton2.setImageURI(Uri.fromFile(Menu_mPhotoFile));   //이미지 변수 저장
            } else
                Toast.makeText(getApplicationContext(), "mPhotoFile is null", Toast.LENGTH_SHORT).show();
        }    //저장된 사진이나 비디오를 외부 저장소 파일에 저장하기
    }

    private void insertMenuRecord() { //메인에서 삽입역할
        String imageuri2 = "sdcard/Android/data/com.hansung.android.homework2/files/Pictures/"+ Menu_PhotoFileName;
        EditText menu = (EditText)findViewById(R.id.menu_register_name);
        EditText price = (EditText)findViewById(R.id.menu_register_price);
        EditText explain = (EditText)findViewById(R.id.menu_register_explain);

        MenuDBHelper = new DBHelper2(this);

        long nOfRows2 = MenuDBHelper.insertMenuByMethod2(menu.getText().toString(),
                price.getText().toString(),
                explain.getText().toString(),
                imageuri2,
                resto);  //ID를 빼먹지 않고 넣는다. 메뉴 삽입될 때 중요한 역할
        if (nOfRows2 >0) {
            Toast.makeText(this, nOfRows2 + " Menu Inserted", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"No Menu Inserted", Toast.LENGTH_SHORT).show();
    }
}