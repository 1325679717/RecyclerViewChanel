package com.chanel.myt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    int factor = 1000;
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
        footerHeight = (DisPlayUtils.getScreenHeight(context)) * 0.2f;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(View.inflate(viewGroup.getContext(),R.layout.chanel_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getLayoutPosition() % list.size();
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        ImageView imageView = myViewHolder.textView;
        Log.i("MyAdapter","onBindViewHolder position = "+position);
        Bitmap bitmap = BitmapFactory.decodeResource(viewHolder.itemView.getContext().getResources(), list.get(position).getRes());
//            Glide
//                    .with(imageView.getContext())
//                    .load("http://smart-test1-php-1255596649.file.myqcloud.com/images/cms/566a6452a40568c09931a141a08217b1.jpg")
//                    .into(imageView);
        imageView.setImageBitmap(bitmap);
        myViewHolder.chanelItemText.setBigText("即将于精品店上市");
        myViewHolder.itemView.setTag(position);
//            myViewHolder.chanelItemText.getBigTv().setTag(viewHolder.getLayoutPosition());
        myViewHolder.chanelItemText.setSmallText("2018/19秋冬系列");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClickListener.onItemClick(v,viewHolder.getLayoutPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size() * factor;
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
}
