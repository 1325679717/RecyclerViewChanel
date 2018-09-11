package com.chanel.myt.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanel.myt.R;
import com.chanel.myt.utils.DisPlayUtils;

public class ChanelItemText extends LinearLayout {
    private TextView smallTv;
    private TextView bigTv;
    public ChanelItemText(Context context) {
        super(context);
        init(context);
    }

    public ChanelItemText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChanelItemText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ChanelItemText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    private void init(Context context){
        View.inflate(context,R.layout.chanel_item_text,this);
        smallTv = findViewById(R.id.title);
        bigTv = findViewById(R.id.content);

    }

    public TextView getBigTv() {
        return bigTv;
    }
    public void setItemViewPercent(float percent){
        setTextAlpha(percent);
        setTextLocation(percent);
    }
    public void setTextLocation(float percent){
        if (percent > 0 && percent <=1){
            this.setTranslationY(percent);
//            smallTv.setTranslationY(1);
        }
    }
    public void setTextAlpha(float percent){
        if (percent >0 && percent <=1){
            bigTv.setAlpha(percent);
        }else if (percent == 0){
            bigTv.setAlpha(0);
        }
    }
    public TextView getSmallTv() {
        return smallTv;
    }
    public void setSmallText(String text){
        smallTv.setText(text);
    }
    public void setBigText(String text){
        bigTv.setText(text);
    }

}
