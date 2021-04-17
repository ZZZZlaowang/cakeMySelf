package com.example.homeworkforcakeshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.entity.ManageCake;
import com.example.homeworkforcakeshop.mainActivity.SellMainActivity;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

import java.util.ArrayList;
import java.util.List;

public class ManageAdapter extends BaseAdapter {
    private int item;
    private Context mContext;
    private List<ManageCake> cakes;
    private String user;

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

    public ManageAdapter(int item, Context mContext,  List<ManageCake> cakes,String user) {
        this.item = item;
        this.mContext = mContext;
        this.cakes = cakes;
        this.user = user;
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
        ImageView img = convertView.findViewById(R.id.img_manage);
        TextView name = convertView.findViewById(R.id.tv_name_manage);
        final Button btnChange = convertView.findViewById(R.id.btn_change);
        final LinearLayout delete = convertView.findViewById(R.id.layout_delete_manage);

        img.setImageBitmap(cakes.get(position).getBitmap());
        name.setText(cakes.get(position).getName());

        //点击删除蛋糕
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("删除");
                builder.setMessage("确定要下架该蛋糕吗？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除数据
                        new Thread(){
                            @Override
                            public void run() {
                                String keyValue = "?user="+user+"&name="+cakes.get(position).getName();
                                String result = CreatURLConnection.getString("DeleteCake",keyValue);
                                if(result.equals("成功")){
                                    cakes.remove(position);
                                    Message m = new Message();
                                    m.what = 1;
                                    handler.sendMessage(m);
                                    Looper.prepare();
                                    Toast toast = Toast.makeText(mContext,
                                            "删除成功",Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    ShowTime.showMyToast(toast,1000);
                                    Looper.loop();
                                }else {
                                    Looper.prepare();
                                    Toast toast = Toast.makeText(mContext,
                                            "删除失败",Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    ShowTime.showMyToast(toast,1000);
                                    Looper.loop();
                                }
                            }
                        }.start();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //点击修改
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出修改框
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                final View view = View.inflate(mContext, R.layout.change_cake, null);
                final EditText price = view.findViewById(R.id.change_price);
                final EditText size = view.findViewById(R.id.change_size);
                final EditText material = view.findViewById(R.id.change_material);
                final EditText packing = view.findViewById(R.id.change_packing);
                final EditText brand = view.findViewById(R.id.change_brand);
                final EditText place = view.findViewById(R.id.change_place);
                final EditText give = view.findViewById(R.id.change_give);
                final Button btnCancel = view.findViewById(R.id.change_cancel);
                final Button btnChange = view.findViewById(R.id.change_change);
                new Thread() {
                    @Override
                    public void run() {
                        //获取该蛋糕的信息
                        String result = CreatURLConnection.getString("findDetailsCake","?name="+cakes.get(position).getName());
                        String[] strings = result.split("&&");
                        price.setText(strings[0]);
                        size.setText(strings[1]);
                        material.setText(strings[2]);
                        packing.setText(strings[3]);
                        brand.setText(strings[4]);
                        place.setText(strings[5]);
                        give.setText(strings[6]);
                    }
                }.start();
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取修改后的信息
                        String price1 = price.getText().toString().trim();
                        String size1 = size.getText().toString().trim();
                        String material1 = material.getText().toString().trim();
                        String packing1 = packing.getText().toString().trim();
                        String brand1 = brand.getText().toString().trim();
                        String place1 = place.getText().toString().trim();
                        String give1 = give.getText().toString().trim();
                        final String keyValue = "?price="+price1+
                                "&size="+size1+
                                "&material="+material1+
                                "&packing="+packing1+
                                "&brand="+brand1+
                                "&place="+place1+
                                "&give="+give1+
                                "&name="+cakes.get(position).getName();
                        new Thread(){
                            @Override
                            public void run() {
                                String result = CreatURLConnection.getString("ChangeCake",keyValue);
                                if(result!=null && result.equals("成功")){
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
                            }
                        }.start();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        return convertView;
    }
}
