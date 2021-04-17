package com.example.homeworkforcakeshop.forCake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

public class DetailsCake extends AppCompatActivity {

    private Button btnBack,btnShopCar;
    private TextView price,size,material,packing,brand,place,give;
    private ImageView imgBig;
    private String name,user,identity;
    private String price1,size1;
    private String keyvalue1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    keyvalue1 = "?name="+name+"&user="+user+"&price="+price1+"&size="+size1;
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cake);

        //获取控件
        findViews();

        //获取蛋糕的名字和用户名
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        user = intent.getStringExtra("user");
        identity = intent.getStringExtra("identity");


        //点击返回
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        //点击加入购物车
        btnShopCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(identity.equals("sell")){
                    Toast toast = Toast.makeText(DetailsCake.this,
                            "卖家无法加入购物车",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else {
                    new Thread(){
                        @Override
                        public void run() {
                            String servlet = "forShopCar";
                            String str = CreatURLConnection.getString(servlet,keyvalue1);
                            if(str!=null && str.equals("成功")){
                                Looper.prepare();
                                Toast toast = Toast.makeText(DetailsCake.this,
                                        "加入购物车成功",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                ShowTime.showMyToast(toast,1000);
                                Looper.loop();
                            }else if(str.equals("存在")){
                                Looper.prepare();
                                Toast toast = Toast.makeText(DetailsCake.this,
                                        "该蛋糕已在购物车或订单中，请删除后再添加",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                ShowTime.showMyToast(toast,2000);
                                Looper.loop();
                            } else{
                                Looper.prepare();
                                Toast toast = Toast.makeText(DetailsCake.this,
                                        "加入购物车失败",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                ShowTime.showMyToast(toast,1000);
                                Looper.loop();
                            }
                        }
                    }.start();
                }
            }
        });

        //加载图片
        new Thread(){
            @Override
            public void run() {
                Bitmap bitmap = CreatURLConnection.getBitmap(name+".jpg");
                imgBig.setImageBitmap(bitmap);
            }
        }.start();

        //根据蛋糕名字查询蛋糕的详细信息
        String keyvalue = "?name="+name;
        findDetailsCake("findDetailsCake",keyvalue);
    }

    //查询详细信息并展示
    private void findDetailsCake(final String servlet,final String keyvalue) {
        new Thread(){
            @Override
            public void run() {
                String str = CreatURLConnection.getString(servlet,keyvalue);
                String[] strings = str.split("&&");
                price1 = strings[0];
                size1 = strings[1];
                price.setText(strings[0]+"￥");
                size.setText(strings[1]+"寸");
                material.setText("【材料】："+strings[2]);
                packing.setText("【包装】："+strings[3]);
                brand.setText("【品牌】："+strings[4]);
                place.setText("【送货范围】："+strings[5]);
                give.setText("【赠送】："+strings[6]);
                Message m = new Message();
                m.what = 1;
                handler.sendMessage(m);
            }
        }.start();
    }

    private void findViews() {
        btnShopCar = findViewById(R.id.btn_shopcar);
        btnBack = findViewById(R.id.btn_back);
        price = findViewById(R.id.tv_pirce);
        size = findViewById(R.id.tv_size);
        material = findViewById(R.id.tv_material);
        packing = findViewById(R.id.tv_packing);
        brand = findViewById(R.id.tv_brand);
        place = findViewById(R.id.tv_place);
        give = findViewById(R.id.tv_give);
        imgBig = findViewById(R.id.img_big);
    }

}