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
import android.widget.ListView;
import android.widget.Toast;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.adapter.CakeAdapter;
import com.example.homeworkforcakeshop.entity.Cake;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity {
    private String mode;
    private String content;
    private Button btnBack;
    private ListView listView;
    private String user,identity;
    private List<Cake> cakeList = new ArrayList<>();
    private CakeAdapter cakeAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    cakeList.add((Cake) msg.obj);
                    break;
                case 2:
                    cakeAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        content = intent.getStringExtra("content");
        user = intent.getStringExtra("user");
        identity = intent.getStringExtra("identity");

        //获取控件
        findViews();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //准备数据
        initData();
        //绑定
        cakeAdapter = new CakeAdapter(SearchResult.this,cakeList,R.layout.cake_item,user,identity);
        listView.setAdapter(cakeAdapter);
    }

    //准备数据
    private void initData() {
        new Thread(){
            @Override
            public void run() {
                String keyValue = "?mode="+mode+"&content="+content;
                String cakes = CreatURLConnection.getString("SearchCakes",keyValue);
                if(cakes.equals("空")){
                    Looper.prepare();
                    Toast toast = Toast.makeText(SearchResult.this,
                            "没有搜到任何蛋糕",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
                }else {
                    String[] list = cakes.split("&&");
                    for(String str : list){
                        String name = str.split("&")[0];
                        String size = str.split("&")[1];
                        String price = str.split("&")[2];
                        String imgName = name+".jpg";
                        Bitmap bitmap = CreatURLConnection.getBitmap(imgName);
                        Cake cake = new Cake(name,size,price,bitmap);
                        //插入数据
                        Message message = new Message();
                        message.what = 1;
                        message.obj = cake;
                        handler.sendMessage(message);
                    }
                    //刷新数据
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            }
        }.start();

    }

    //获取控件
    private void findViews() {
        btnBack = findViewById(R.id.btn_back_search);
        listView = findViewById(R.id.search_listview);
    }
}