package com.example.theseventh.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.theseventh.R;

import java.util.*;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    public Context con;
    public  LayoutInflater inflater;
    public List<Map<String,Object>> list=new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //利用LayoutInflater来加载item的布局
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycle_view, viewGroup, false);
        return new ViewHolder(itemView);
    }
    public RecyclerViewAdapter(List<Map<String,Object>> list, Context con){
        this.con=con;
        this.list=list;
        inflater= LayoutInflater.from(con);
    }

    @Override
    public void onBindViewHolder( final ViewHolder viewHolder, int i) {
        viewHolder.mTextView.setText(list.get(i).get("desc").toString());
        viewHolder.iTextView.setText(list.get(i).get("who").toString());
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView iTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_item_text);
            iTextView = itemView.findViewById(R.id.text_id);
        }
    }

}
