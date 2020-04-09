package com.example.myapplication.utils;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.dto.Commodity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Demo {

    public List<Commodity> getList() {
        final String path = "http://s30453o259.zicp.vip/text/1";
        final String json =getJson(path);
        List<Commodity> accountList =(List<Commodity>) JSON.parseArray(json, Commodity.class);
        return accountList;
    }

    private String getJson(String path){
        String json = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);
            if(responseCode==200){
                InputStream is = connection.getInputStream();
                int count = 0;
                while (count == 0) {
                    // 如果发送的请求没有结束, 那么值为0
                    count = is.available();
                }

                byte[] bt = new byte[count];
                int readCount = 0;
                while (readCount < count) {
                    readCount += is.read(bt, readCount, count - readCount);
                }
                json =  new String(bt);
            }
        }catch (Exception e){
                e.printStackTrace();
        }
        return json;
    }
}
