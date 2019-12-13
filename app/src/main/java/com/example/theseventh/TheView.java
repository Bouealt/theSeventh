package com.example.theseventh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.theseventh.RecyclerView.RecyclerViewAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheView extends AppCompatActivity {
    androidx.recyclerview.widget.RecyclerView revView;
    RecyclerViewAdapter recAdapter;
    public List<Map<String, Object>> list = new ArrayList<>();
    RequestOptions options = new RequestOptions().circleCrop();
    //ImageView imageView =(ImageView) findViewById(R.id.text_image);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_view);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        sendRequestWithiHttpURLConnection();
    }
    private void sendRequestWithiHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://gank.io/api/data/Android/10/1");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    parseJSONWithJSONObject(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String response) {
        try {
            JSONObject jsonObjectALL = new JSONObject(response);
            JSONArray jsonArray = jsonObjectALL.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String desc = jsonObject.optString("desc", null);
                String url = jsonObject.optString("url", null);
                String who =  jsonObject.optString("who",null);
                Map<String, Object> map = new HashMap<>();
                //Glide.with(this).load(url).apply(options).into(imageView);
                map.put("desc", desc);
                map.put("who",who);
                list.add(map);
            }
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    revView = findViewById(R.id.rv_recycle);
                    revView.addItemDecoration(new DividerItemDecoration(TheView.this, OrientationHelper.VERTICAL));
                    revView.setItemAnimator(new DefaultItemAnimator());
                    recAdapter = new RecyclerViewAdapter(list, TheView.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TheView.this);
                    revView.setLayoutManager(linearLayoutManager);
                    revView.setAdapter(recAdapter);
                    break;
            }
        }
    };
}
