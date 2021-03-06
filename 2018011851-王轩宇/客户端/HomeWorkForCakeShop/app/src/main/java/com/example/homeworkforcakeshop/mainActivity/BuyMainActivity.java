package com.example.homeworkforcakeshop.mainActivity;


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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.forCake.SearchResult;
import com.example.homeworkforcakeshop.adapter.CakeAdapter;
import com.example.homeworkforcakeshop.adapter.OrderAdapter;
import com.example.homeworkforcakeshop.adapter.ShopCarAdapter;
import com.example.homeworkforcakeshop.entity.Cake;
import com.example.homeworkforcakeshop.entity.OrderCake;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyMainActivity extends AppCompatActivity implements View.OnClickListener {

    private String user,identity;
    private CakeAdapter cakeAdapter;
    private ShopCarAdapter shopCarAdapter;
    private OrderAdapter orderAdapter;
    private TextView tvUser;
    private Button btnLogout;
    private ImageView imgSearch;
    private CheckBox cbOrder,cbDelete;
    private TextView tvDelete,tvOrder,tvOrderAll,tvDeleteAll;
    private ListView shopcarListView;

    private final List<Cake> cakeShopCar = new ArrayList<>();
    private final List<OrderCake> cakesOrder = new ArrayList<>();

    private Map<String,ImageView> imageViewMap = new HashMap<>();
    private Map<String,TextView> textViewMap = new HashMap<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1://????????????
                    orderAdapter.notifyDataSetChanged();
                    break;
                case 2://???????????????
                    shopCarAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    shopCarList();
                    break;
                case 4:
                    myOrderList();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_main);

        //????????????
        findViews();

        //?????????
        setListener();

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

        //??????TabHost??????
        TabHost tabHost = findViewById(android.R.id.tabhost);
        //?????????TabHost
        tabHost.setup();
        //??????TabSpec??????
        final TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1")
                .setIndicator(getTabSpecView("tab1","?????????",R.drawable.home))//?????????????????????
                .setContent(R.id.tab1);//???????????????
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2")
                .setIndicator(getTabSpecView("tab2","?????????",R.drawable.car))
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
                        imageViewMap.get("tab2").setImageResource(R.drawable.car);
                        imageViewMap.get("tab3").setImageResource(R.drawable.order);
                        textViewMap.get("tab1").setTextColor(getResources().getColor(R.color.red));
                        textViewMap.get("tab2").setTextColor(getResources().getColor(R.color.dark));
                        textViewMap.get("tab3").setTextColor(getResources().getColor(R.color.dark));
                        //??????????????????
                        mainList();
                        break;
                    case "tab2"://???????????????
                        imageViewMap.get("tab1").setImageResource(R.drawable.home);
                        imageViewMap.get("tab2").setImageResource(R.drawable.car1);
                        imageViewMap.get("tab3").setImageResource(R.drawable.order);
                        textViewMap.get("tab1").setTextColor(getResources().getColor(R.color.dark));
                        textViewMap.get("tab2").setTextColor(getResources().getColor(R.color.red));
                        textViewMap.get("tab3").setTextColor(getResources().getColor(R.color.dark));
                        shopCarList();
                        break;
                    case "tab3"://??????????????????
                        imageViewMap.get("tab1").setImageResource(R.drawable.home);
                        imageViewMap.get("tab2").setImageResource(R.drawable.car);
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


    //???????????????
    private void setListener() {
        cbOrder.setOnClickListener(this);
        cbDelete.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
    }

    //????????????
    private void findViews() {
        cbDelete = findViewById(R.id.check_box_delete_all);
        cbOrder = findViewById(R.id.check_box_order_all);
        tvDelete = findViewById(R.id.delete_all);
        tvOrder = findViewById(R.id.order_all);
        tvOrderAll = findViewById(R.id.tv_order_all);
        tvDeleteAll = findViewById(R.id.tv_delete_all);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(BuyMainActivity.this);
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
               intent.setClass(BuyMainActivity.this, SearchResult.class);
               startActivity(intent);
           }
       });
        dialog.show();
    }


    //??????????????????
    private void myOrderList() {
        tvDeleteAll.setText("??????");
        cbDelete.setChecked(false);
        cakesOrder.clear();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://????????????
                        cakesOrder.add((OrderCake) msg.obj);
                        break;
                    case 2://??????
                        orderAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        //????????????
        initDataForMyOrderList(handler);
        //??????
        orderAdapter = new OrderAdapter(BuyMainActivity.this,cakesOrder,R.layout.order_cake,user,identity);
        ListView listView = findViewById(R.id.tab3_view);
        listView.setAdapter(orderAdapter);
    }

    private void initDataForMyOrderList(final Handler handler) {
        new Thread(){
            @Override
            public void run() {
                String servlet = "UserFindOrderBuy";
                String keyValue = "?user="+user+"&identity="+identity;
                String cakes = CreatURLConnection.getString(servlet,keyValue);
                if(!cakes.equals("???")){
                    String[] cakeList = cakes.split("&&");
                    for(String str : cakeList){
                        String name = str.split("&")[0];
                        String ifOrder = str.split("&")[1];
                        String user1 = null;
                        String imgName = name+".jpg";
                        Bitmap bitmap = CreatURLConnection.getBitmap(imgName);
                        OrderCake cake = new OrderCake(name,ifOrder,bitmap,user1);
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
                    Toast toast = Toast.makeText(BuyMainActivity.this,
                            "????????????",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
                }
            }
        }.start();
    }

    //???????????????
    private void shopCarList() {
        tvOrderAll.setText("??????");
        cbOrder.setChecked(false);
        cakeShopCar.clear();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://????????????
                        cakeShopCar.add((Cake) msg.obj);
                        break;
                    case 2://??????
                        shopCarAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        //????????????
        initDataForShopCarLst(handler);
        //??????
        shopCarAdapter = new ShopCarAdapter(BuyMainActivity.this,cakeShopCar,R.layout.shop_car_item,user);
        shopcarListView = findViewById(R.id.tab2_view);
        shopcarListView.setAdapter(shopCarAdapter);
    }

    private void initDataForShopCarLst(final Handler handler) {
        new Thread(){
            @Override
            public void run() {
                String servlet = "UserFindShopChar";
                String keyValue = "?user="+user;
                String cakes = CreatURLConnection.getString(servlet,keyValue);
                if(!cakes.equals("???")){
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
                }else{
                    Looper.prepare();
                    Toast toast = Toast.makeText(BuyMainActivity.this,
                            "???????????????",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
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
        cakeAdapter = new CakeAdapter(BuyMainActivity.this,cakeList,R.layout.cake_item,user,identity);
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
                    Toast toast = Toast.makeText(BuyMainActivity.this,
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_all://??????????????????
                deleteAll();
                break;
            case R.id.order_all://??????????????????
                orderAll();
                break;
            case R.id.check_box_delete_all://??????????????????????????????
                cbDeleteAll();
                break;
            case R.id.check_box_order_all://??????????????????????????????
                cbOrderAll();
                break;
        }
    }

    //??????????????????
    private void deleteAll() {
        new Thread(){
            @Override
            public void run() {
                for(int i=0;i<cakesOrder.size();++i){
                    if(cakesOrder.get(i).getN()==1){
                        final String name = cakesOrder.get(i).getName();
                        CreatURLConnection.getString("DeleteOrder","?name="+name+"&user="+user+"&identity="+identity);
                    }
                }
                //??????
                Message m = new Message();
                m.what=4;
                handler.sendMessage(m);
            }
        }.start();
    }

    //??????????????????
    private void orderAll() {
        new Thread(){
            @Override
            public void run() {
                for(int i=0;i<cakeShopCar.size();++i){
                    if(cakeShopCar.get(i).getN()==1){
                        final String name = cakeShopCar.get(i).getName();
                        CreatURLConnection.getString("forOrder","?name="+name+"&user="+user);
                    }
                }
                //??????
                Message m = new Message();
                m.what=3;
                handler.sendMessage(m);
            }
        }.start();
    }

    //??????????????????????????????
    private void cbDeleteAll() {
        if(tvDeleteAll.getText().equals("??????")){
            if(cakesOrder.size()!=0){
                for(int i=0;i<cakesOrder.size();++i){
                    cakesOrder.get(i).setN(1);
                }
            }
            tvDeleteAll.setText("????????????");
        }else if(tvDeleteAll.getText().equals("????????????")){
            if(cakesOrder.size()!=0){
                for(int i=0;i<cakesOrder.size();++i){
                    cakesOrder.get(i).setN(0);
                }
            }
            tvDeleteAll.setText("??????");
        }
        //??????
        Message m = new Message();
        m.what=1;
        handler.sendMessage(m);
    }

    //??????????????????????????????
    private void cbOrderAll() {
        if(tvOrderAll.getText().equals("??????")){
            if(cakeShopCar.size()!=0){
                for(int i=0;i<cakeShopCar.size();++i){
                    cakeShopCar.get(i).setN(1);
                }
            }
            tvOrderAll.setText("????????????");
        }else if(tvOrderAll.getText().equals("????????????")){
            if(cakeShopCar.size()!=0){
                for(int i=0;i<cakeShopCar.size();++i){
                    cakeShopCar.get(i).setN(0);
                }
            }
            tvOrderAll.setText("??????");
        }
        //??????
        Message m = new Message();
        m.what=2;
        handler.sendMessage(m);
    }
}