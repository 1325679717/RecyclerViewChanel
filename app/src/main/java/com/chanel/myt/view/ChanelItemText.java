package com.chanel.myt.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanel.myt.R;
import com.chanel.myt.utils.DisPlayUtils;

public class ChanelItemText extends LinearLayout {

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


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

}
