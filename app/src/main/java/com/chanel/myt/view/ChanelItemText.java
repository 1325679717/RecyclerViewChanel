package com.chanel.myt.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
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
        if (percent < 0){
            percent = 0;
        }else if (percent > 1){
            percent = 1;
        }
        setTextAlpha(percent);
        setTextLocation(percent);
    }
    public void setTextLocation(float percent){

        float foldeY = (ChanelItemView.foldedHeight * 1f - getHeight())/ 2;
        float openY = (ChanelItemView.opendHeight * 1f - getHeight())/ 2;

        float translationY = (openY - foldeY) * percent;
        int bigTvHeight = bigTv.getHeight();
        if ((int)bigTv.getTag() == 1) {
//            Log.i("ChanelItemText", "setTextLocation bigTvHeight= " + bigTvHeight + ",translationY = " + translationY);
        }
//        this.setTranslationY(translationY);
        if (translationY < bigTvHeight){

        }else {
            if ((int)bigTv.getTag() == 1)
            Log.i("ChanelItemText", "setTextLocation else= " + (translationY - bigTvHeight)+",foldeY = "+foldeY+",openY = "+openY+",percent = "+percent);
            this.setTranslationY(translationY - bigTvHeight);
        }
    }
    public void setTextAlpha(float percent){
        bigTv.setAlpha(percent);

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
