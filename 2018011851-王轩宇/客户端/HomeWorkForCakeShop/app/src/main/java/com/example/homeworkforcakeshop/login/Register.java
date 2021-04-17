package com.example.homeworkforcakeshop.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.util.ConfigUtil;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

public class Register extends AppCompatActivity {

    private Button btnBack,btnRegister;
    private EditText etUser,etPwd;
    private RadioButton radioBuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //获取控件
        findViews();

        //点击注册
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = etUser.getText().toString().trim();
                final String pwd = etPwd.getText().toString().trim();
                final String identity;
                if(radioBuy.isChecked()){
                    identity = "buy";
                }else {
                    identity = "sell";
                }
                if(!user.equals("") && !pwd.equals("") ){
                    if(user.length()>7){
                        Toast toast = Toast.makeText(Register.this,
                                "用户名不能超过8位",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }else{
                        new Thread(){
                            @Override
                            public void run() {
                                String keyvalue = "?user="+user+"&identity="+identity;
                                String servlet = "IfExistUser";
                                String str = CreatURLConnection.sendPwd(servlet,keyvalue);
                                if(str.equals("存在")){
                                    Looper.prepare();
                                    Toast toast = Toast.makeText(Register.this,
                                            "该用户已经注册",Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    ShowTime.showMyToast(toast,1000);
                                    Looper.loop();
                                }else{
                                    Intent intent = new Intent();
                                    intent.putExtra("user",user);
                                    intent.putExtra("pwd",pwd);
                                    intent.putExtra("identity",identity);
                                    intent.setClass(Register.this,LoginActivity.class);
                                    setResult(100,intent);
                                    finish();
                                }
                            }
                        }.start();
                    }

                }else{
                    Toast.makeText(Register.this,
                            "请输入完整信息",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //点击 返回登录
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        btnBack = findViewById(R.id.btn_back);
        btnRegister = findViewById(R.id.btn_register);
        etPwd = findViewById(R.id.et_pwd);
        etUser = findViewById(R.id.et_user);
        radioBuy = findViewById(R.id.radio_buy);
    }
}