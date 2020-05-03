package com.example.recyclerview_ass3;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterBooks adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get();

    }

    public void get() {
        try{
            InputStream input = getAssets().open("test.json");
            int size= input.available();
            byte[] buffer= new byte[size];
            input.read(buffer);
            input.close();
            String res= new String(buffer,"UTF-8");
            JSONArray jArray= new JSONArray(res);
            List<BooksData> data=new ArrayList<>();
            for(int i=0;i<jArray.length();i++){
                JSONObject jobj=jArray.getJSONObject(i);
                BooksData bookInfo=new BooksData();
                bookInfo.author=jobj.getString("author");
                bookInfo.authorUrl=jobj.getString("authorUrl");
                bookInfo.cover=jobj.getString("cover");
                bookInfo.info=jobj.getString("info");
                bookInfo.level=jobj.getString("level");
                bookInfo.url=jobj.getString("url");
                bookInfo.title=jobj.getString("title");
                data.add(bookInfo);
                recyclerView=findViewById(R.id.bookList);
                adapter=new AdapterBooks(this,data);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            }
        }catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}