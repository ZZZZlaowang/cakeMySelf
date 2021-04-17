package com.example.homeworkforcakeshop.adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.homeworkforcakeshop.forCake.DetailsCake;
import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.entity.Cake;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

import java.util.List;

public class ShopCarAdapter extends BaseAdapter {

    private Context mContext;
    private List<Cake> cakes;
    private int item;
    private String user;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1://刷新
                    notifyDataSetChanged();
                    break;
            }
        }
    };

    public ShopCarAdapter(Context mContext, List<Cake> cakes, int item,String u) {
        this.mContext = mContext;
        this.cakes = cakes;
        this.item = item;
        this.user = u;
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
        //获取控件
        ImageView imgCake = convertView.findViewById(R.id.img_small);
        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvSize = convertView.findViewById(R.id.tv_size);
        TextView tvPrice = convertView.findViewById(R.id.tv_price);
        final LinearLayout cake = convertView.findViewById(R.id.shop_linearlayout);
        Button btnOrder = convertView.findViewById(R.id.btn_order);
        final CheckBox checkBox = convertView.findViewById(R.id.check_box);

        //设置控件内容
        imgCake.setImageBitmap(cakes.get(position).getPhoto());
        tvName.setText(cakes.get(position).getName());
        tvSize.setText(cakes.get(position).getSize()+"寸");
        tvPrice.setText(cakes.get(position).getPrice()+"￥");

        if(cakes.get(position).getN()==1){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }

        //进入蛋糕详情界面
        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, DetailsCake.class);
                intent.putExtra("user",user);
                intent.putExtra("name",cakes.get(position).getName());
                mContext.startActivity(intent);
            }
        });

        //点击下单
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        String keyvalue = "?name="+cakes.get(position).getName()+"&user="+user;
                        String servlet = "forOrder";
                        String s = CreatURLConnection.getString(servlet,keyvalue);
                        if(s.equals("成功")){
                            cakes.remove(cakes.get(position));
                            Message m = new Message();
                            m.what = 1 ;
                            handler.sendMessage(m);
                            Looper.prepare();
                            Toast toast = Toast.makeText(mContext,
                                    "下单成功",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            ShowTime.showMyToast(toast,1000);
                            Looper.loop();


                        }else {
                            Looper.prepare();
                            Toast toast = Toast.makeText(mContext,
                                    "下单失败",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            ShowTime.showMyToast(toast,1000);
                            Looper.loop();
                        }
                    }
                }.start();
            }
        });
        return convertView;
    }

}
