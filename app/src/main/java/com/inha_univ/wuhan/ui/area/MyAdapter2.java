package com.inha_univ.wuhan.ui.area;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inha_univ.wuhan.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<ItemObject2> mList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_Date, textView_Place, textView_Comment;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_Date = (TextView) itemView.findViewById(R.id.text3);
            textView_Place = (TextView) itemView.findViewById(R.id.text2);
            textView_Comment = (TextView) itemView.findViewById(R.id.text1);
        }
    }

    //생성자
    public MyAdapter2(ArrayList<ItemObject2> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public MyAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.ViewHolder holder, final int position) {

        holder.textView_Date.setText(String.valueOf(mList.get(position).getDate()));
        holder.textView_Place.setText(String.valueOf(mList.get(position).getPlace()));
        holder.textView_Comment.setText(String.valueOf(mList.get(position).getComment()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}






