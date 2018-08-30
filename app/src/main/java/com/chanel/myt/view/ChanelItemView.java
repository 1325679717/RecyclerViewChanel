package com.chanel.myt.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chanel.myt.R;
import com.chanel.myt.utils.DisPlayUtils;

public class ChanelItemView extends LinearLayout {
    private int opendHeight = 0;

    private int foldedHeight = 0;

    private float parallax = 0;

    private int parallaxOffset;
    private ImageView imageView;
    public void setParallaxOffset(int parallaxOffset) {
        this.parallaxOffset = parallaxOffset;
    }

    public int getFoldedHeight() {
        return foldedHeight;
    }

    public int getOpendHeight() {
        return opendHeight;
    }

    public ChanelItemView(Context context) {
        super(context);
        init(context);
    }

    public ChanelItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChanelItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ChanelItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    private void init(Context context){

        foldedHeight = (int)context.getResources().getDimension(R.dimen.closed_bloc_height);
        opendHeight = (int) (DisPlayUtils.getScreenHeight(context) *0.6);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = findViewById(R.id.iv);
    }

    public void parallax(float f){
        parallax = f;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getLayoutParams();
        layoutParams.height = (int) (opendHeight * f);
//        setLayoutParams(layoutParams);
//        imageView.setY(opendHeight * f);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        setMeasuredDimension();
    }
}
