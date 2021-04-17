package com.example.homeworkforcakeshop.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class CreatURLConnection {

    /**
     * 获取图片
     * @param name
     * @return
     */
    public static Bitmap getBitmap(final String name){
        try {
            URL url = new URL(ConfigUtil.SERVER_ADDR+"images/"+name);
            URLConnection imgConn = url.openConnection();
            InputStream imgIn = imgConn.getInputStream();
            return BitmapFactory.decodeStream(imgIn);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送密码这种需要保密的信息
     * @param servlet
     * @param keyValue
     * @return
     */
    public static String sendPwd(final String servlet,final String keyValue){
        try {
            URL url = new URL(ConfigUtil.SERVER_ADDR+servlet);
            //接收服务端返回的数据
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            OutputStream out = conn.getOutputStream();
            out.write(keyValue.getBytes());
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, "utf-8"));
            return reader.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加在后缀的方式
     * 与服务端连接并返回数据
     * @param servlet
     * @param keyvalue
     * @return
     */
    public static String getString(final String servlet, final String keyvalue) {
        try {
            URL url = new URL(ConfigUtil.SERVER_ADDR+servlet+keyvalue);
            URLConnection connection = url.openConnection();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            return reader.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
