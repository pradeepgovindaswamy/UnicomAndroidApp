package com.makeathon.enable.simplechatapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lakapoor on 12/12/17.
 */

public class CustomAdapterV extends RecyclerView.Adapter<CustomAdapterV.MyViewHolder> {

    List<String> names;
    List<String> nums;
    Context context;

    public CustomAdapterV(Context context, List<String> names, List<String> nums){
        this.context = context;
        this.names = names;
        this.nums = nums;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CircleImageView imageView;
        public MyViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            imageView = (CircleImageView) view.findViewById(R.id.image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        MyViewHolder mv = new MyViewHolder(view);
        return mv;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(names.get(position));
        if (position%3==0){
            holder.imageView.setImageResource(R.drawable.iconscustomer);
        }
        else if (position%3==1){
            holder.imageView.setImageResource(R.drawable.iconscustomeblac);
        }
        else{
            holder.imageView.setImageResource(R.drawable.iconscustomerred);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chatbox.class);
                intent.putExtra("number", nums.get(position));
                intent.putExtra("name", names.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
