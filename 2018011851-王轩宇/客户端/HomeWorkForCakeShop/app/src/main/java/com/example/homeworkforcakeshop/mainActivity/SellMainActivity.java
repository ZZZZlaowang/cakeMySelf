package com.example.homeworkforcakeshop.mainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.adapter.OrderSellAdapter;
import com.example.homeworkforcakeshop.forCake.AddCake;
import com.example.homeworkforcakeshop.forCake.SearchResult;
import com.example.homeworkforcakeshop.adapter.CakeAdapter;
import com.example.homeworkforcakeshop.adapter.ManageAdapter;
import com.example.homeworkforcakeshop.adapter.OrderAdapter;
import com.example.homeworkforcakeshop.entity.Cake;
import com.example.homeworkforcakeshop.entity.ManageCake;
import com.example.homeworkforcakeshop.entity.OrderCake;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

public class SellMainActivity extends AppCompatActivity {

    private String user,identity;
    private CakeAdapter cakeAdapter;
    private OrderAdapter orderAdapter;
    private ManageAdapter manageAdapter;
    private OrderSellAdapter orderSellAdapter;
    private TextView tvUser;
    private Button btnLogout,btnAdd;
    private ImageView imgSearch;

    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_main);

        //????????????????????????
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        identity = intent.getStringExtra("identity");

        //????????????
        tvUser = findViewById(R.id.main_tv_user);
        tvUser.setText(user);
        //????????????
        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //??????
        imgSearch = findViewById(R.id.img_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow();
            }
        });
        //????????????
        btnAdd = findViewById(R.id.btn_add_cake);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCake();
            }
        });

        //??????TabHost??????
        TabHost tabHost = findViewById(android.R.id.tabhost);
        //?????????TabHost
        tabHost.setup();
        //??????TabSpec??????
        final TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1")
                .setIndicator(getTabSpecView("tab1","?????????",R.drawable.home))//?????????????????????
                .setContent(R.id.tab1);//???????????????
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2")
                .setIndicator(getTabSpecView("tab2","????????????",R.drawable.setting))
                .setContent(R.id.tab2);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3")
                .setIndicator(getTabSpecView("tab3","????????????",R.drawable.order))
                .setContent(R.id.tab3);
        //??????????????????

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        mainList();

        //??????TabHost?????????
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {//??????????????????????????????
                switch (tabId) {
                    case "tab1"://???????????????
                        imageViewMap.get("tab1").setImageResource(R.drawable.home1);
                        imageViewMap.get("tab2").setImageResource(R.drawable.setting);
                        imageViewMap.get("tab3").setImageResource(R.drawable.order);
                        textViewMap.get("tab1").setTextColor(getResources().getColor(R.color.red));
                        textViewMap.get("tab2").setTextColor(getResources().getColor(R.color.dark));
                        textViewMap.get("tab3").setTextColor(getResources().getColor(R.color.dark));
                        //??????????????????
                        mainList();
                        break;
                    case "tab2"://??????????????????
                        imageViewMap.get("tab1").setImageResource(R.drawable.home);
                        imageViewMap.get("tab2").setImageResource(R.drawable.setting1);
                        imageViewMap.get("tab3").setImageResource(R.drawable.order);
                        textViewMap.get("tab1").setTextColor(getResources().getColor(R.color.dark));
                        textViewMap.get("tab2").setTextColor(getResources().getColor(R.color.red));
                        textViewMap.get("tab3").setTextColor(getResources().getColor(R.color.dark));
                        manageCakeList();
                        break;
                    case "tab3"://??????????????????
                        imageViewMap.get("tab1").setImageResource(R.drawable.home);
                        imageViewMap.get("tab2").setImageResource(R.drawable.setting);
                        imageViewMap.get("tab3").setImageResource(R.drawable.order1);
                        textViewMap.get("tab1").setTextColor(getResources().getColor(R.color.dark));
                        textViewMap.get("tab2").setTextColor(getResources().getColor(R.color.dark));
                        textViewMap.get("tab3").setTextColor(getResources().getColor(R.color.red));
                        myOrderList();
                        break;
                }
            }
        });

        //????????????
        tabHost.setCurrentTab(0);
        imageViewMap.get("tab1").setImageResource(R.drawable.home1);
        textViewMap.get("tab1").setTextColor(getResources().getColor(R.color.red));
    }

    //??????tabhost?????????
    public View getTabSpecView(String tag,String title,int drawable){
        View view = getLayoutInflater().inflate(R.layout.tab_spec,null);
        //??????tab_spec????????????????????????????????????
        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(drawable);

        //???ImageView???????????????Map???
        imageViewMap.put(tag,icon);


        TextView tv_title = view.findViewById(R.id.title);
        tv_title.setText(title);
        textViewMap.put(tag,tv_title);
        return view;
    }

    //????????????
    private void addCake() {
        Intent intent = new Intent();
        intent.setClass(SellMainActivity.this, AddCake.class);
        intent.putExtra("user",user);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            manageCakeList();
            Toast toast = Toast.makeText(SellMainActivity.this,"????????????",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    //???????????????????????????????????????
    private void popupWindow() {
        //??????popupWindow??????
        final PopupWindow popupWindow = new PopupWindow(this);
        //???????????????????????????
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //??????????????????
        View view = getLayoutInflater().inflate(R.layout.popu_window,null);
        //?????????????????????????????????????????????
        Button btnName = view.findViewById(R.id.btn_name);
        Button btnPrice = view.findViewById(R.id.btn_price);
        Button btnSize = view.findViewById(R.id.btn_size);
        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????????????????
                showInputBox("name");
                popupWindow.dismiss();
            }
        });
        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????????????????
                showInputBox("price");
                popupWindow.dismiss();
            }
        });
        btnSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????????????????
                showInputBox("size");
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        //??????
        popupWindow.showAsDropDown(imgSearch);
    }

    //?????????
    private void showInputBox(final String mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SellMainActivity.this);
        View view = View.inflate(this,R.layout.search_item,null);
        final EditText editText = view.findViewById(R.id.et_search);
        Button btnCancel = view.findViewById(R.id.btn_search_cancel);
        Button btnSearch = view.findViewById(R.id.btn_search_search);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String content = editText.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("mode",mode);
                intent.putExtra("content",content);
                intent.putExtra("user",user);
                intent.putExtra("identity",identity);
                intent.setClass(SellMainActivity.this, SearchResult.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    //??????????????????
    private void myOrderList() {
        final List<OrderCake> cakes = new ArrayList<>();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://????????????
                        cakes.add((OrderCake) msg.obj);
                        break;
                    case 2://??????
                        orderSellAdapter.notifyDataSetChanged();
                        Log.e("dsd",""+cakes.size());
                        break;
                }
            }
        };
        //????????????
        initDataForMyOrderList(handler);
        //??????
        orderSellAdapter = new OrderSellAdapter(SellMainActivity.this,cakes,R.layout.order_sell_cake,user,identity);
        GridView gridView = findViewById(R.id.tab3_view);
        gridView.setAdapter(orderSellAdapter);
    }

    private void initDataForMyOrderList(final Handler handler) {
        new Thread(){
            @Override
            public void run() {
                String servlet = "UserFindOrder";
                String keyValue = "?user="+user;
                String cakes = CreatURLConnection.getString(servlet,keyValue);
                if(!cakes.equals("???")){
                    String[] cakeList = cakes.split("&&");
                    for(String str : cakeList){
                        String name = str.split("&")[0];
                        String ifOrder = str.split("&")[1];
                        String user = str.split("&")[2];
                        String imgName = name+".jpg";
                        Bitmap bitmap = CreatURLConnection.getBitmap(imgName);
                        OrderCake cake = new OrderCake(name,ifOrder,bitmap,user);
                        //????????????
                        Message message = new Message();
                        message.what = 1;
                        message.obj = cake;
                        handler.sendMessage(message);
                    }
                    //????????????
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }else{
                    Looper.prepare();
                    Toast toast = Toast.makeText(SellMainActivity.this,
                            "????????????",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
                }
            }
        }.start();
    }

    //??????????????????
    private void manageCakeList() {
        final List<ManageCake> cakes = new ArrayList<>();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://????????????
                        cakes.add((ManageCake) msg.obj);
                        break;
                    case 2://??????
                        manageAdapter.notifyDataSetChanged();
                        break;

                }
            }
        };
        //????????????
        initDataForManageCakeLst(handler);
        manageAdapter = new ManageAdapter(R.layout.manage_cake,SellMainActivity.this,cakes,user);
        GridView gridView = findViewById(R.id.tab2_view);
        gridView.setAdapter(manageAdapter);
    }

    private void initDataForManageCakeLst(final Handler handler) {
        new Thread(){
            @Override
            public void run() {
                String string = CreatURLConnection.getString("FindNameBuySell","?user="+user);
                String[] names = string.split("&");
                if(names.equals("???")){
                    Looper.prepare();
                    Toast toast = Toast.makeText(SellMainActivity.this,
                            "??????????????????",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
                }else{
                    for(String name:names){
                        Bitmap bitmap = CreatURLConnection.getBitmap(name+".jpg");
                        ManageCake cake = new ManageCake(name,bitmap);
                        Message m = new Message();
                        m.what = 1;
                        m.obj = cake;
                        handler.sendMessage(m);
                    }
                    //????????????
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //???????????????
    private void mainList() {
        final List<Cake> cakeList = new ArrayList<>();
        Handler handler = new Handler(){
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
        //????????????
        initDataForMainLst(handler);
        //??????
        cakeAdapter = new CakeAdapter(SellMainActivity.this,cakeList,R.layout.cake_item,user,identity);
        GridView gridView = findViewById(R.id.tab1_view);
        gridView.setAdapter(cakeAdapter);
    }

    //?????????????????????
    private void initDataForMainLst(final Handler handler) {
        new Thread(){
            @Override
            public void run() {
                String servlet = "MainList";
                String keyValue = "";
                String cakes = CreatURLConnection.getString(servlet,keyValue);
                if(cakes.equals("???")){
                    Looper.prepare();
                    Toast toast = Toast.makeText(SellMainActivity.this,
                            "??????????????????",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
                }else{
                    String[] cakeList = cakes.split("&&");
                    for(String str : cakeList){
                        String name = str.split("&")[0];
                        String size = str.split("&")[1];
                        String price = str.split("&")[2];
                        String imgName = name+".jpg";
                        Bitmap bitmap = CreatURLConnection.getBitmap(imgName);
                        Cake cake = new Cake(name,size,price,bitmap);
                        //????????????
                        Message message = new Message();
                        message.what = 1;
                        message.obj = cake;
                        handler.sendMessage(message);
                    }
                    //????????????
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}