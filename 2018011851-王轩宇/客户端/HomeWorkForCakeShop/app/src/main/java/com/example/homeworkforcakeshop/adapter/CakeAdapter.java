package com.example.homeworkforcakeshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.entity.Cake;
import com.example.homeworkforcakeshop.forCake.DetailsCake;

import java.util.List;

public class CakeAdapter extends BaseAdapter {
    private Context mContext;
    private List<Cake> cakes;
    private int item;
    private String user;
    private String identity;

    public CakeAdapter(Context mContext, List<Cake> cakes, int item,String u,String identity) {
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
        //获取控件
        ImageView imgCake = convertView.findViewById(R.id.img_small);
        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvSize = convertView.findViewById(R.id.tv_size);
        TextView tvPrice = convertView.findViewById(R.id.tv_price);
        LinearLayout cake = convertView.findViewById(R.id.cake_linearlayout);

        //设置控件内容
        imgCake.setImageBitmap(cakes.get(position).getPhoto());
        tvName.setText(cakes.get(position).getName());
        tvSize.setText(cakes.get(position).getSize()+"寸");
        tvPrice.setText(cakes.get(position).getPrice()+"￥");

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, DetailsCake.class);
                intent.putExtra("user",user);
                intent.putExtra("name",cakes.get(position).getName());
                intent.putExtra("identity",identity);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
