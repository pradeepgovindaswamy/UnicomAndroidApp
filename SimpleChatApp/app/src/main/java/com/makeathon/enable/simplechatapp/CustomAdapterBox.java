package com.makeathon.enable.simplechatapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lakapoor on 12/12/17.
 */

public class CustomAdapterBox extends RecyclerView.Adapter<CustomAdapterBox.MyViewHolder> {

    List<String> shownumbers;
    List<String> showtext;
    List<String> showdate;
    String mynumber;
    Context context;


    public CustomAdapterBox(Context context, List<String> showtext, List<String> shownumbers, List<String> showdate, String mynumber) {
        this.shownumbers = shownumbers;
        this.showtext = showtext;
        this.showdate = showdate;
        this.context = context;
        this.mynumber = mynumber;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CardView cardView;
        public MyViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            cardView = (CardView) view.findViewById(R.id.cv);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg,parent,false);
        MyViewHolder mv = new MyViewHolder(view);
        return mv;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(showtext.get(position));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.cardView.getLayoutParams();

        if(mynumber.equals(shownumbers.get(position))){
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        else{
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        holder.cardView.setLayoutParams(params);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, showdate.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return showtext.size();
    }
}

