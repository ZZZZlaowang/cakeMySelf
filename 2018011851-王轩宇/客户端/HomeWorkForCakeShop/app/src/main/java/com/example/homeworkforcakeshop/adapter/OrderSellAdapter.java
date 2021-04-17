package com.example.homeworkforcakeshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.entity.OrderCake;
import com.example.homeworkforcakeshop.util.ConfigUtil;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class OrderSellAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderCake> cakes ;
    private int item;
    private String user,identity;
    private Button btnOrder;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    notifyDataSetChanged();
                    break;
            }
        }
    };

    public OrderSellAdapter(Context mContext, List<OrderCake> cakes, int item,String u,String identity) {
        this.mContext = mContext;
        this.cakes = cakes;
        this.item = item;
        this.user = u;
        this.identity = identity;
    }


    @Override
    public int getCount() {
        if(cakes!=null){
            return cakes.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(cakes!=null){
            return cakes.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(item, null);
        }

        btnOrder = convertView.findViewById(R.id.btn_ifOrder);
        TextView tvName = convertView.findViewById(R.id.tv_name_order);
        ImageView img = convertView.findViewById(R.id.img_order);
        TextView tvNameBuy = convertView.findViewById(R.id.tv_name_buy);

        tvName.setText(cakes.get(position).getName());
        btnOrder.setText(cakes.get(position).getIfOrder());
        img.setImageBitmap(cakes.get(position).getBitmap());
        tvNameBuy.setText(cakes.get(position).getUser());
        final String ifOrder = cakes.get(position).getIfOrder();

        //更改订单状态
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnOrder.getText().equals("待发货")){
                    // 创建对话框构建器
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("更改订单");
                    builder.setMessage("确定要更改为已发货吗？");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cakes.get(position).setIfOrder("已发货");
                            modifyOrder(position);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        return convertView;
    }

    //修改订单状态
    private void modifyOrder(final int p) {
        new Thread(){
            @Override
            public void run() {
                String keyvalue = "?user="+user+"&name="+cakes.get(p).getName()+"&ifOrder=已发货";
                try {
                    URL url = new URL(ConfigUtil.SERVER_ADDR+"ModifyOrder"+keyvalue);
                    URLConnection connection = url.openConnection();
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                    String string = reader.readLine();
                    if(string!=null && string.equals("成功")){
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        Looper.prepare();
                        Toast toast = Toast.makeText(mContext,
                                "修改成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        ShowTime.showMyToast(toast,1000);
                        Looper.loop();

                    }else {
                        Looper.prepare();
                        Toast toast = Toast.makeText(mContext,
                                "修改失败",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        ShowTime.showMyToast(toast,1000);
                        Looper.loop();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}