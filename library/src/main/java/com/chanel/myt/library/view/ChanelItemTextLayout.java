package com.chanel.myt.library.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanel.myt.library.R;


public abstract class ChanelItemTextLayout extends LinearLayout {
    public ChanelItemTextLayout(Context context) {
        super(context);
        init(context);
    }

    public ChanelItemTextLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChanelItemTextLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ChanelItemTextLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    public abstract void init(Context context);
    public void setItemViewPercent(float percent){
        if (percent < 0){
            percent = 0;
        }else if (percent > 1){
            percent = 1;
        }
        setTextAlpha(percent);
        setTextLocation(percent);
    }
    public void setTextLocation(float percent){//(0,1)

        float foldeY = (ChanelItemView.foldedHeight - getHeight()) /2;
        float openY = (ChanelItemView.opendHeight - getHeight());

        float translationY = (openY - foldeY) * percent;
        this.setTranslationY(translationY);
    }
    public abstract void setTextAlpha(float percent);

}
