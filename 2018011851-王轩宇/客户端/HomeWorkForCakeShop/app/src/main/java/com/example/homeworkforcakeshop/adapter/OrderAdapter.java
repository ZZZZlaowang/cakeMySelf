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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.homeworkforcakeshop.R;
import com.example.homeworkforcakeshop.entity.OrderCake;
import com.example.homeworkforcakeshop.util.CreatURLConnection;
import com.example.homeworkforcakeshop.util.ShowTime;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

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

    public OrderAdapter(Context mContext, List<OrderCake> cakes, int item,String u,String identity) {
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
        final LinearLayout delete = convertView.findViewById(R.id.layout_delete);
        final CheckBox checkBox = convertView.findViewById(R.id.check_box);

        tvName.setText(cakes.get(position).getName());
        btnOrder.setText(cakes.get(position).getIfOrder());
        img.setImageBitmap(cakes.get(position).getBitmap());
        final String ifOrder = cakes.get(position).getIfOrder();

        if(cakes.get(position).getN()==1){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }

        //点击删除订单
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifOrder.equals("已发货")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("删除订单");
                    builder.setMessage("确定要删除该订单吗？");
                    builder.setNegativeButton("取消",null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String keyvalue = "?user="+user+"&name="+cakes.get(position).getName()+"&identity="+identity;
                            new Thread(){
                                @Override
                                public void run() {
                                    String servlet = "DeleteOrder";
                                    String string = CreatURLConnection.getString(servlet,keyvalue);
                                    if(string.equals("成功")){
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

                                    }else{
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

                } else{
                    Toast toast = Toast.makeText(mContext,
                            "还未发货，无法删除",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });


        return convertView;
    }

}
