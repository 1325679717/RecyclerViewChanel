package com.chanel.myt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanel.myt.R;
import com.chanel.myt.bean.ColorBean;
import com.chanel.myt.utils.DisPlayUtils;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    int ids[] = {R.color.red,R.color.color_2aa094,R.color.color_F5A623,R.color.colorAccent,R.color.colorPrimaryDark,R.color.color_378983,R.color.colorPrimary};
    int NORMAL = 1;
    int FOOTER = 2;
    int disPlayWidth = 0;
    List<ColorBean> list;

    public MyAdapter(List<ColorBean> list, Context context) {
        this.list = list;
        disPlayWidth = DisPlayUtils.getScreenWidth(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == NORMAL)
            return new MyViewHolder(View.inflate(viewGroup.getContext(),R.layout.chanel_item,null));
        else
            return new FooterViewHolder(View.inflate(viewGroup.getContext(),R.layout.chanel_footer,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            TextView textView = myViewHolder.textView;
            textView.setTag(viewHolder.getLayoutPosition());
            textView.setBackgroundColor(viewHolder.itemView.getContext().getColor(list.get(i).getRes()));
            textView.setText(viewHolder.getLayoutPosition()+"");
            myViewHolder.itemView.setTag(viewHolder.getLayoutPosition());

        }else {
            FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
            TextView textView = footerViewHolder.textView;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.width = disPlayWidth;
            textView.setLayoutParams(layoutParams);
            footerViewHolder.textView.setTag(viewHolder.getLayoutPosition());
//            footerViewHolder.imageView.setBackgroundColor(viewHolder.itemView.getContext().getColor(list.get(i).getRes()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 1)
            return NORMAL;
        else
            return FOOTER;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.iv);
        }
    }
    class FooterViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.iv);
        }
    }
}
