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
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
//식당 등록 액티비티
public class RestaurantRegister extends AppCompatActivity {
    private DBHelper RestaurantDBHelper;
    final static String TAG="SQLITEDBTEST";

    private File mPhotoFile =null;
    private String mPhotoFileName = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final int  REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA=1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("한성 맛집 앱");   //어플 이름 설정
        setContentView(R.layout.restaurant_register);
        checkDangerousPermissions();   //기기의 보호되는 기능을 사용하려면 check(보안기능)

        RestaurantDBHelper = new DBHelper(this);

        Button restaurant_registerbutton = (Button)findViewById(R.id.retaurant_register_button);   //맛집등록버튼
        restaurant_registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRestaurantRecord();
                Intent intent = new Intent(RestaurantRegister.this, RestaurantCatalog.class);
                startActivity(intent);  //맛집등록버튼을 누르면 RestaurantCatalog액티비티로 돌아간다
            }
        });

        ImageButton camera_registerbutton = findViewById(R.id.restaurant_camera_register_button);   //사진찍기 버튼 생성
        camera_registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //카메라 버튼
                dispatchTakePictureIntent_Restaurant();   //사진찍기로 감
            }
        });
    }

    private void dispatchTakePictureIntent_Restaurant() {   //카메라 앱을 실행하기위한 요청
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //1. 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
            mPhotoFileName = "IMG"+currentDateFormat()+".jpg";
            mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);

            if (mPhotoFile !=null) {
                //2. 생성된 파일 객체에 대한 Uri 객체를 얻기
                Uri imageUri2 = FileProvider.getUriForFile(this, "com.example.homework2", mPhotoFile);

                //3. Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri2);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else    //파일 없음
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();
        }
    }
    private void insertRestaurantRecord() { //메인에서 삽입역할
        String imageuri= "sdcard/Android/data/com.example.homework2/files/Pictures/"+mPhotoFileName;
        EditText name = (EditText)findViewById(R.id.restaurant_register_name);   //restaurantregister의 textInput
        EditText home = (EditText)findViewById(R.id.restaurant_register_home);
        EditText phone = (EditText)findViewById(R.id.restaurant_register_phone);
        EditText time = (EditText)findViewById(R.id.restaurant_register_time);

        long nOfRows = RestaurantDBHelper.insertRestaurantByMethod(name.getText().toString(), home.getText().toString(),
                phone.getText().toString(), time.getText().toString(), imageuri);
        if (nOfRows >0)
            Toast.makeText(this,nOfRows+" Restaurant Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"No Record Inserted", Toast.LENGTH_SHORT).show();
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
            ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //찍은 사진을 처리하기위한 함수
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) { //이미지를 찍고 OK를 눌렀을 때
            if (mPhotoFileName != null) {
                mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
                ImageButton ResImagebutton = findViewById(R.id.restaurant_camera_register_button);   //이미지 버튼에
                ResImagebutton.setImageURI(Uri.fromFile(mPhotoFile));   //이미지 변수 저장

            } else
                Toast.makeText(getApplicationContext(), "mPhotoFile is null", Toast.LENGTH_SHORT).show();
        }    //저장된 사진이나 비디오를 외부 저장소 파일에 저장하기
    }
}