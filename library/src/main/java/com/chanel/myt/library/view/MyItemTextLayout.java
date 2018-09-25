package com.chanel.myt.library.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.chanel.myt.library.R;

public class MyItemTextLayout extends ChanelItemTextLayout {
    private TextView smallTv;
    private TextView bigTv;
    public MyItemTextLayout(Context context) {
        super(context);
    }

    public MyItemTextLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyItemTextLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyItemTextLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context context){
        View.inflate(context, R.layout.chanel_item_text,this);
        smallTv = findViewById(com.chanel.myt.library.R.id.title);
        bigTv = findViewById(com.chanel.myt.library.R.id.content);

    }
    public TextView getSmallTv() {
        return null;
    }

    public void setSmallText(String text) {

    }

    public void setBigText(String text) {

    }
    @Override
    public void setTextAlpha(float percent){
        bigTv.setAlpha(percent);

    }
}
