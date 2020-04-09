package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dto.Commodity;
import com.example.myapplication.dto.Tag;
import com.example.myapplication.utils.BitmapUtil;
import com.example.myapplication.utils.Demo;
import com.example.myapplication.utils.QRCode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    public static final int CHANGE_UI=1;

    private ListView mListView;

    private List<Commodity> commodities  = new ArrayList<>();

    private ImageView webView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mListView = findViewById(R.id.lv);
        webView = findViewById(R.id.web);
        fillData();



    }

    private void  fillData(){
        final AsyncHttpClient client = new AsyncHttpClient();
        //获取服务器返回值(json格式)
        client.get(getString(R.string.serverUrl), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //调用json解析工具 ,解析json文件
                try{
                    String json = new String(responseBody,"utf-8");
                    commodities = JSON.parseArray(json,Commodity.class);
                    if(commodities==null){
                        Toast.makeText(MainActivity.this,"解析数据失败",Toast.LENGTH_SHORT).show();
                    }else{
                        //调用setAdapter把返回值填充到ListView中
                        mListView.setAdapter(new MyBaseAdapter());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this,"请求数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }



    class MyBaseAdapter extends BaseAdapter{

        private Map<Integer,Bitmap> map = new HashMap<>();
        private Bitmap bitmap;
        //异步处理类
        private MyHendler myHendler = new MyHendler();

        @Override
        public int getCount() {
            return commodities.size();
        }

        @Override
        public Object getItem(int position) {
            return commodities.get(position).getCommodityName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //得到item的view的视图,并赋值
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //将list_item的布局转换成一个View对象
            View view  = View.inflate(MainActivity.this,R.layout.list_item,null);
            //找到条目中的控件
            ImageView item_image = view.findViewById(R.id.item_image);
            TextView name = view.findViewById(R.id.name);
            TextView price = view.findViewById(R.id.price);
            TextView count = view.findViewById(R.id.count);
            Button button = view.findViewById(R.id.buyButton);
            name.setText(commodities.get(position).getCommodityName());
            price.setText("$  "+commodities.get(position).getPrice().toString());
            count.setText(commodities.get(position).getCount().toString());

            final int content = position;
            bitmap = map.get(content);
            if (bitmap != null) {
                //已经加载了图片，直接显示
                item_image.setImageBitmap(bitmap);
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        //没有加载图片，从网络加载
                        bitmap = BitmapUtil.getbitmap(getString(R.string.hostUrl)+commodities.get(content).getPicture());
                        map.put(content, bitmap);
                        myHendler.sendEmptyMessage(0);
                    }

                }.start();
            }

            //为post服务器数据
            Map<String,String> commodityInfo = new HashMap<>();
            commodityInfo.put("machineId","1");
            commodityInfo.put("commodityId",commodities.get(position).getId().toString());
            commodityInfo.put("count","1");
            final RequestParams params = new RequestParams(commodityInfo);

            //为购买按钮添加点击功能
            button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post(getString(R.string.postUrl), params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try{
                                //获取支付链接
                                String html = new String(responseBody,"utf-8");

                                Bitmap bitmap = QRCode.createQRCodeBitmap("html",150,150,"utf-8","H", "1", Color.BLACK, Color.WHITE);
                                webView.setVisibility(View.VISIBLE);
                                webView.setImageBitmap(bitmap);
                            }catch (Exception e){

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                }
            });
            return view;
        }

        private class MyHendler extends Handler {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        notifyDataSetChanged();
                        break;

                    default:
                        break;
                }
                super.handleMessage(msg);
            }

        }

    }
}
