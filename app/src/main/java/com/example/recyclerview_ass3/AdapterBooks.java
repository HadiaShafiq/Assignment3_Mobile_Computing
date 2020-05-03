package com.example.recyclerview_ass3;

import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class AdapterBooks extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    TextView tv_loading;
    String dest_file_path = "test.pdf";
    int downloadedSize = 0, totalsize;
    float per = 0;

    private Context context;
    private LayoutInflater inflater;
    List<BooksData> data= Collections.emptyList();
    BooksData current;
    int currentPos=0;
    // create constructor to innitilize context and data sent from MainActivity
    public AdapterBooks(Context context, List<BooksData> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }
    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=inflater.inflate(R.layout.custom_layout, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }
    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder= (MyHolder) holder;
        final BooksData current=data.get(position);
        myHolder.bookName.setText(current.title);
        myHolder.binfo.setText(current.info);
        myHolder.level.setText(current.level);
        //load image  using glide
       // Glide.with(context).load(current.cover).into(myHolder.cover); //not working
        Picasso.with(context).load(current.cover).into(myHolder.cover);
        myHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context , DownloadTask.class);
                intent.putExtra("url",current.url);
                Toast.makeText(context, "This a toast message", Toast.LENGTH_LONG).show();
                context.startActivity(intent);
                //new DownloadTask(current.url);
            }
        });
    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView bookName;
        ImageView cover;
        TextView author;
        TextView authorUrl;
        TextView level;
        TextView binfo;
        TextView url;
        Button btn;
        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            bookName= (TextView) itemView.findViewById(R.id.bookName);
            cover= (ImageView) itemView.findViewById(R.id.cover);
            level = (TextView) itemView.findViewById(R.id.level);
            binfo = (TextView) itemView.findViewById(R.id.info);
            btn=itemView.findViewById(R.id.button);
        }
    }

}
