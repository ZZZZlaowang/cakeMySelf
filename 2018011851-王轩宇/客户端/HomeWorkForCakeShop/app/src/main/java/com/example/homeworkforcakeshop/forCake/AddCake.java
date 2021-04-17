package com.example.homeworkforcakeshop.forCake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.login.LoginActivity;
import com.example.homeworkforcakeshop.util.ConfigUtil;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddCake extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etPrice, etSize, etMaterial, etPacking, etBrand, etPlace, etGive;
    private Button btnBack, btnAdd;
    private ImageView imgAdd;
    private String user;
    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    //图片的路径
    private String imagePath = null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cake);

        if (ActivityCompat.checkSelfPermission(AddCake.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( AddCake.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        //获取控件
        findViews();

        //设置监听器
        setListener();

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_add://添加蛋糕
                addCake();
                break;
            case R.id.btn_back_add://返回
                finish();
                break;
            case R.id.img_add://添加图片
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                break;
        }
    }

    //添加蛋糕
    private void addCake() {
        final String name = etName.getText().toString().trim();
        final String price = etPrice.getText().toString().trim();
        final String size = etSize.getText().toString().trim();
        final String material = etMaterial.getText().toString().trim();
        final String packing = etPacking.getText().toString().trim();
        final String brand = etBrand.getText().toString().trim();
        final String place = etPlace.getText().toString().trim();
        final String give = etGive.getText().toString().trim();
        if(name!=null && !name.equals("")){
            new Thread(){
                @Override
                public void run() {
                    String result = CreatURLConnection.getString("IfExistCake","?name="+name);
                    if(result!=null && result.equals("存在")){
                        Looper.prepare();
                        Toast toast = Toast.makeText(AddCake.this,
                                "蛋糕名字重复",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        ShowTime.showMyToast(toast,1000);
                        Looper.loop();
                    }else{//名字不存在
                        if(price!=null && size!=null && material!=null && packing!=null && brand!=null && place!=null && give!=null && imagePath!=null
                            && !price.equals("") && !size.equals("") && !material.equals("") && !packing.equals("") && !brand.equals("") && !place.equals("") && !give.equals("") && !imagePath.equals("")){
                            String cake = name+"-"+price+"-"+size+"-"+material+"-"+packing+"-"+brand+"-"+place+"-"+give+"-"+user;
                            String keyValue = "?cake="+cake;
                            String result1 = CreatURLConnection.getString("AddCake",keyValue);//添加信息的返回
                            String result2 = null;//添加图片的返回
                            //传图片
                            File file = new File(imagePath);
                            try {
                                URL url = new URL(ConfigUtil.SERVER_ADDR +"UpLoadFile"+"?name="+name);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                OutputStream out = conn.getOutputStream();
                                FileInputStream in = new FileInputStream(file);
                                byte[] b = new byte[1024];
                                int n = -1;
                                while ((n=in.read(b))!=-1){
                                    out.write(b,0,n);
                                }
                                InputStream inputStream = conn.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                                result2 = reader.readLine();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(result1!=null && result1.equals("成功") && result2!=null && result2.equals("成功")){
                                Message m = new Message();
                                m.what = 1;
                                handler.sendMessage(m);
                            }else{
                                Looper.prepare();
                                Toast toast = Toast.makeText(AddCake.this,
                                        "添加失败",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                ShowTime.showMyToast(toast,1000);
                                Looper.loop();
                            }
                        }else {
                            Looper.prepare();
                            Toast toast = Toast.makeText(AddCake.this,
                                    "请将信息补充完整",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            ShowTime.showMyToast(toast,1000);
                            Looper.loop();
                        }
                    }
                }
            }.start();
        }else{
            Toast toast = Toast.makeText(AddCake.this,"请输入名字",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imagePath = c.getString(columnIndex);
            //显示图片
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imgAdd.setImageBitmap(bitmap);
            c.close();
        }
    }

    //设置监听器
    private void setListener() {
        btnAdd.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
    }

    //获取控件
    private void findViews() {
        etName = findViewById(R.id.add_name);
        etPrice = findViewById(R.id.add_price);
        etSize = findViewById(R.id.add_size);
        etMaterial = findViewById(R.id.add_material);
        etPacking = findViewById(R.id.add_packing);
        etBrand = findViewById(R.id.add_brand);
        etPlace = findViewById(R.id.add_place);
        etGive = findViewById(R.id.add_give);
        btnAdd = findViewById(R.id.btn_add_add);
        btnBack = findViewById(R.id.btn_back_add);
        imgAdd = findViewById(R.id.img_add);
    }
}