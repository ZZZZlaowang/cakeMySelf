package com.example.homeworkforcakeshop.login;

import androidx.annotation.Nullable;
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

import com.example.homeworkforcakeshop.mainActivity.BuyMainActivity;
import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.mainActivity.SellMainActivity;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

public class LoginActivity extends AppCompatActivity {

    private EditText etUser,etPwd;
    private Button btnLogin,btnRegister;
    private RadioButton radioBuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取控件
        findViews();

        //点击登录
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = etUser.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                final String identity;
                if(radioBuy.isChecked()){
                    identity = "buy";
                }else {
                    identity = "sell";
                }
                final String keyvalue = user+"&"+pwd+"&"+identity;
                new Thread(){
                    @Override
                    public void run() {
                        //检查用户密码是否正确
                        String servlet = "IfTrueUser";
                        String str = CreatURLConnection.sendPwd(servlet,keyvalue);
                        if(str!=null &&str.equals("错误")){
                            Looper.prepare();
                            Toast toast = Toast.makeText(LoginActivity.this,
                                    "用户名或密码错误",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            ShowTime.showMyToast(toast,1000);
                            Looper.loop();
                        }else {
                            Intent i = new Intent();
                            i.putExtra("user",user);
                            i.putExtra("identity",identity);
                            if(identity.equals("buy")){
                                i.setClass(LoginActivity.this, BuyMainActivity.class);
                            }else{
                                i.setClass(LoginActivity.this, SellMainActivity.class);
                            }
                            startActivity(i);
                        }
                    }
                }.start();
            }
        });

        //点击注册
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,Register.class);
                startActivityForResult(intent,100);
            }
        });

    }

    //注册,返回数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==100){//注册用户
            String user = data.getStringExtra("user");
            String pwd = data.getStringExtra("pwd");
            String identity = data.getStringExtra("identity");
            String keyValue = user+"&"+pwd+"&"+identity;
            registerUser(keyValue,"registerUser");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //注册用户
    private void registerUser(final String keyValue,final String servlet) {
        new Thread(){
            @Override
            public void run() {
                String str = CreatURLConnection.sendPwd(servlet,keyValue);
                if(str!=null && str.equals("成功")){
                    Looper.prepare();
                    Toast toast = Toast.makeText(LoginActivity.this,
                            "注册成功",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast toast = Toast.makeText(LoginActivity.this,
                            "注册失败",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
                }
            }
        }.start();

    }

    //获取控件
    private void findViews() {
        etPwd = findViewById(R.id.et_pwd);
        etUser = findViewById(R.id.et_user);
        btnLogin = findViewById(R.id.btn_login);
        radioBuy = findViewById(R.id.radio_buy);
        btnRegister = findViewById(R.id.btn_register);
    }

}