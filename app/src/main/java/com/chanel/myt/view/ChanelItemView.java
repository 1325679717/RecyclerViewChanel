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

    private int opendWidth = 0;
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
        opendWidth = DisPlayUtils.getScreenWidth(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = findViewById(R.id.iv);
    }

    public void parallaxOpen(float f){
        parallax = f;
        imageView.getLayoutParams().height = (int) (opendHeight *f);
        imageView.getLayoutParams().width = opendWidth;
        requestLayout();
    }
    public void parallaxFolded(float f){//0-1
        parallax = f;

        imageView.getLayoutParams().height = foldedHeight + (int) ((opendHeight -foldedHeight)* f);
        imageView.getLayoutParams().width = opendWidth;
        requestLayout();
    }
    public void parallax(float f){
        parallax = f;
        imageView.getLayoutParams().height = foldedHeight;
        imageView.getLayoutParams().width = opendWidth;
        requestLayout();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        imageView.measure(MeasureSpec.makeMeasureSpec(opendWidth,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(opendHeight,MeasureSpec.EXACTLY));
//        int imgHeight =
//        setMeasuredDimension();
    }

    public ImageView getImageView() {
        return imageView;
    }
}
