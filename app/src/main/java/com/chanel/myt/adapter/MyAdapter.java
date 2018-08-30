package com.chanel.myt.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chanel.myt.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    int ids[] = {R.drawable.image_one,R.drawable.image_two,R.drawable.image_three,R.drawable.image_four,R.drawable.image_one,R.drawable.image_two};
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(View.inflate(viewGroup.getContext(),R.layout.chanel_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.imageView.setBackground(viewHolder.itemView.getContext().getDrawable(ids[i]));
    }

    @Override
    public int getItemCount() {
        return ids.length;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv);
        }
    }
}
