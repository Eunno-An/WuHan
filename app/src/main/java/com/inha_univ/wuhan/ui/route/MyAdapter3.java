package com.inha_univ.wuhan.ui.route;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inha_univ.wuhan.R;


import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {

    //데이터 배열 선언
    public ArrayList<String> mList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private int tf;
        public CheckBox c;
        public ViewHolder(View itemView) {
            super(itemView);

            c = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    //생성자
    public MyAdapter3(ArrayList<String> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public MyAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_checkbox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter3.ViewHolder holder, final int position) {

        holder.c.setChecked(true);
        if(mList.get(position) == "b"){
            holder.c.setChecked(false);
        }
        holder.c.setText(position+1+"번 확진자");
        holder.c.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mList.get(position) == "a")
                    mList.set(position, "b");
                else{
                    mList.set(position, "a");
                }
            }
        }) ;
                //holder.textView_Date.setText(String.valueOf(mList.get(position).getDate()));


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}






