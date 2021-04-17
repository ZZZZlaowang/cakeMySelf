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
                case 1://刷新订单
                    orderAdapter.notifyDataSetChanged();
                    break;
                case 2://刷新购物车
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

        //获取控件
        findViews();

        //监听器
        setListener();

        //获取用户名和身份
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        identity = intent.getStringExtra("identity");

        //欢迎用户
        tvUser = findViewById(R.id.main_tv_user);
        tvUser.setText(user);
        //注销账户
        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //搜索
        imgSearch = findViewById(R.id.img_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow();
            }
        });

        //获取TabHost引用
        TabHost tabHost = findViewById(android.R.id.tabhost);
        //初始化TabHost
        tabHost.setup();
        //创建TabSpec页面
        final TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1")
                .setIndicator(getTabSpecView("tab1","主界面",R.drawable.home))//选项按钮的显示
                .setContent(R.id.tab1);//对应的页面
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2")
                .setIndicator(getTabSpecView("tab2","购物车",R.drawable.car))
                .setContent(R.id.tab2);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3")
                .setIndicator(getTabSpecView("tab3","我的订单",R.drawable.order))
                .setContent(R.id.tab3);
        //添加页面效果

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        mainList();

        //设置TabHost监听器
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {//当切换选项按钮时调用
                switch (tabId) {
                    case "tab1"://点击主界面
                        imageViewMap.get("tab1").setImageResource(R.drawable.home1);
                        imageViewMap.get("tab2").setImageResource(R.drawable.car);
                        imageViewMap.get("tab3").setImageResource(R.drawable.order);
                        textViewMap.get("tab1").setTextColor(getResources().getColor(R.color.red));
                        textViewMap.get("tab2").setTextColor(getResources().getColor(R.color.dark));
                        textViewMap.get("tab3").setTextColor(getResources().getColor(R.color.dark));
                        //加载蛋糕信息
                        mainList();
                        break;
                    case "tab2"://点击购物车
                        imageViewMap.get("tab1").setImageResource(R.drawable.home);
                        imageViewMap.get("tab2").setImageResource(R.drawable.car1);
                        imageViewMap.get("tab3").setImageResource(R.drawable.order);
                        textViewMap.get("tab1").setTextColor(getResources().getColor(R.color.dark));
                        textViewMap.get("tab2").setTextColor(getResources().getColor(R.color.red));
                        textViewMap.get("tab3").setTextColor(getResources().getColor(R.color.dark));
                        shopCarList();
                        break;
                    case "tab3"://点击我的订单
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
        //设置默认
        tabHost.setCurrentTab(0);
        imageViewMap.get("tab1").setImageResource(R.drawable.home1);
        textViewMap.get("tab1").setTextColor(getResources().getColor(R.color.red));
    }


    //设置监听器
    private void setListener() {
        cbOrder.setOnClickListener(this);
        cbDelete.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
    }

    //获取控件
    private void findViews() {
        cbDelete = findViewById(R.id.check_box_delete_all);
        cbOrder = findViewById(R.id.check_box_order_all);
        tvDelete = findViewById(R.id.delete_all);
        tvOrder = findViewById(R.id.order_all);
        tvOrderAll = findViewById(R.id.tv_order_all);
        tvDeleteAll = findViewById(R.id.tv_delete_all);
    }

    //设置tabhost的显示
    public View getTabSpecView(String tag,String title,int drawable){
        View view = getLayoutInflater().inflate(R.layout.tab_spec,null);
        //获取tab_spec布局文件中试图控件的引用
        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(drawable);

        //将ImageView对象存储到Map中
        imageViewMap.put(tag,icon);


        TextView tv_title = view.findViewById(R.id.title);
        tv_title.setText(title);
        textViewMap.put(tag,tv_title);
        return view;
    }

    //弹出提示框，选择搜索的方式
    private void popupWindow() {
        //创建popupWindow对象
        final PopupWindow popupWindow = new PopupWindow(this);
        //设置弹出窗口的宽度
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置它的视图
        View view = getLayoutInflater().inflate(R.layout.popu_window,null);
        //设置视图当中控件的属性和监听器
        Button btnName = view.findViewById(R.id.btn_name);
        Button btnPrice = view.findViewById(R.id.btn_price);
        Button btnSize = view.findViewById(R.id.btn_size);
        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出搜索的输入框
                showInputBox("name");
                popupWindow.dismiss();
            }
        });
        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出搜索的输入框
                showInputBox("price");
                popupWindow.dismiss();
            }
        });
        btnSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出搜索的输入框
                showInputBox("size");
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        //位置
        popupWindow.showAsDropDown(imgSearch);
    }

    //输入框
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


    //点击我的订单
    private void myOrderList() {
        tvDeleteAll.setText("全选");
        cbDelete.setChecked(false);
        cakesOrder.clear();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://插入数据
                        cakesOrder.add((OrderCake) msg.obj);
                        break;
                    case 2://刷新
                        orderAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        //准备数据
        initDataForMyOrderList(handler);
        //绑定
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
                if(!cakes.equals("空")){
                    String[] cakeList = cakes.split("&&");
                    for(String str : cakeList){
                        String name = str.split("&")[0];
                        String ifOrder = str.split("&")[1];
                        String user1 = null;
                        String imgName = name+".jpg";
                        Bitmap bitmap = CreatURLConnection.getBitmap(imgName);
                        OrderCake cake = new OrderCake(name,ifOrder,bitmap,user1);
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
                }else{
                    Looper.prepare();
                    Toast toast = Toast.makeText(BuyMainActivity.this,
                            "暂无订单",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
                }
            }
        }.start();
    }

    //点击购物车
    private void shopCarList() {
        tvOrderAll.setText("全选");
        cbOrder.setChecked(false);
        cakeShopCar.clear();
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1://添加蛋糕
                        cakeShopCar.add((Cake) msg.obj);
                        break;
                    case 2://刷新
                        shopCarAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        //准备数据
        initDataForShopCarLst(handler);
        //绑定
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
                if(!cakes.equals("空")){
                    String[] cakeList = cakes.split("&&");
                    for(String str : cakeList){
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
                }else{
                    Looper.prepare();
                    Toast toast = Toast.makeText(BuyMainActivity.this,
                            "购物车空空",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    ShowTime.showMyToast(toast,1000);
                    Looper.loop();
                }
            }
        }.start();
    }

    //点击主界面
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
        //准备数据
        initDataForMainLst(handler);
        //绑定
        cakeAdapter = new CakeAdapter(BuyMainActivity.this,cakeList,R.layout.cake_item,user,identity);
        GridView gridView = findViewById(R.id.tab1_view);
        gridView.setAdapter(cakeAdapter);
    }

    //主界面查询蛋糕
    private void initDataForMainLst(final Handler handler) {
        new Thread(){
            @Override
            public void run() {
                String servlet = "MainList";
                String keyValue = "";
                String cakes = CreatURLConnection.getString(servlet,keyValue);
                if(cakes.equals("空")){
                    Looper.prepare();
                    Toast toast = Toast.makeText(BuyMainActivity.this,
                            "暂无上架蛋糕",Toast.LENGTH_SHORT);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_all://点击全部删除
                deleteAll();
                break;
            case R.id.order_all://点击全部下单
                orderAll();
                break;
            case R.id.check_box_delete_all://点击全部删除的复选框
                cbDeleteAll();
                break;
            case R.id.check_box_order_all://点击全部下单的复选框
                cbOrderAll();
                break;
        }
    }

    //点击全部删除
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
                //刷新
                Message m = new Message();
                m.what=4;
                handler.sendMessage(m);
            }
        }.start();
    }

    //点击全部下单
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
                //刷新
                Message m = new Message();
                m.what=3;
                handler.sendMessage(m);
            }
        }.start();
    }

    //点击全部删除的复选框
    private void cbDeleteAll() {
        if(tvDeleteAll.getText().equals("全选")){
            if(cakesOrder.size()!=0){
                for(int i=0;i<cakesOrder.size();++i){
                    cakesOrder.get(i).setN(1);
                }
            }
            tvDeleteAll.setText("取消全选");
        }else if(tvDeleteAll.getText().equals("取消全选")){
            if(cakesOrder.size()!=0){
                for(int i=0;i<cakesOrder.size();++i){
                    cakesOrder.get(i).setN(0);
                }
            }
            tvDeleteAll.setText("全选");
        }
        //刷新
        Message m = new Message();
        m.what=1;
        handler.sendMessage(m);
    }

    //点击全部下单的复选框
    private void cbOrderAll() {
        if(tvOrderAll.getText().equals("全选")){
            if(cakeShopCar.size()!=0){
                for(int i=0;i<cakeShopCar.size();++i){
                    cakeShopCar.get(i).setN(1);
                }
            }
            tvOrderAll.setText("取消全选");
        }else if(tvOrderAll.getText().equals("取消全选")){
            if(cakeShopCar.size()!=0){
                for(int i=0;i<cakeShopCar.size();++i){
                    cakeShopCar.get(i).setN(0);
                }
            }
            tvOrderAll.setText("全选");
        }
        //刷新
        Message m = new Message();
        m.what=2;
        handler.sendMessage(m);
    }
}