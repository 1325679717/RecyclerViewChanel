package com.chanel.myt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.chanel.myt.view.ChanelItemText;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    int NORMAL = 1;
    int FOOTER = 2;
    int disPlayWidth = 0;
    float footerHeight = 0;
    List<ColorBean> list;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    public void addOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
    public MyAdapter(List<ColorBean> list, Context context) {
        this.list = list;
        disPlayWidth = DisPlayUtils.getScreenWidth(context);
        footerHeight = DisPlayUtils.getScreenHeight(context) * 0.2f;
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            ImageView imageView = myViewHolder.textView;
            imageView.setTag(viewHolder.getLayoutPosition());
            Bitmap bitmap = BitmapFactory.decodeResource(viewHolder.itemView.getContext().getResources(), list.get(i).getRes());
            imageView.setImageBitmap(bitmap);
            myViewHolder.chanelItemText.setBigText("即将于精品店上市");
            myViewHolder.itemView.setTag(viewHolder.getLayoutPosition());
            myViewHolder.chanelItemText.getBigTv().setTag(viewHolder.getLayoutPosition());
            myViewHolder.chanelItemText.setSmallText("2018/19秋冬系列");

        }else {
            FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
            TextView textView = footerViewHolder.textView;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.width = disPlayWidth;
            layoutParams.height = (int) footerHeight;
            textView.setLayoutParams(layoutParams);
            footerViewHolder.textView.setTag(viewHolder.getLayoutPosition());
//            footerViewHolder.imageView.setBackgroundColor(viewHolder.itemView.getContext().getColor(list.get(i).getRes()));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.onItemClick(v,viewHolder.getLayoutPosition());
            }
        });
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
        ImageView textView;
        ChanelItemText chanelItemText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.iv);
            chanelItemText = itemView.findViewById(R.id.chanelItemText);
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
