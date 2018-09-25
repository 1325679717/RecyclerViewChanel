package com.chanel.myt.library.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chanel.myt.library.R;
import com.chanel.myt.library.utils.DisPlayUtils;

public class ChanelItemView extends RelativeLayout {
    public static int opendHeight = 0;


    private int opendWidth = 0;
    public static int foldedHeight = 0;


    private ImageView imageView;

    private ChanelItemTextLayout chanelItemText;

    private LinearLayout chanel_mask;

    private float f;

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
        chanelItemText = findViewById(R.id.chanelItemText);
        chanel_mask = findViewById(R.id.chanel_mask);

    }


    public void setItemViewPercent(float percent){
        chanelItemText.setItemViewPercent(percent);
    }
    public void parallaxImg(float f){//0-1
        int offset = (int) (foldedHeight* f);
        int moveY = foldedHeight - offset;
        imageView.setY(-moveY);//imgetView y坐标设置移动0-foldedHeight高度产生视差效果

    }
    public void updateView(float f){

        this.f = f;
        int height = foldedHeight + getDifference();
        if (height >= foldedHeight && height <= opendHeight) {
            chanel_mask.getLayoutParams().height = height;
            imageView.getLayoutParams().height = opendHeight;
            imageView.getLayoutParams().width = opendWidth;
        }
        setItemViewPercent(f);
        parallaxImg(f);
        requestLayout();
    }

    /**
     * 滑动时ChanelItemView增加的高度0%-100% foldedHeight到opendHeight的高度差
     * @return
     */
    private int getDifference(){
        return (int) ((opendHeight -foldedHeight)* f);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = foldedHeight + getDifference();
        setMeasuredDimension(opendWidth,height);
        chanel_mask.measure(MeasureSpec.makeMeasureSpec(opendWidth,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
        imageView.measure(MeasureSpec.makeMeasureSpec(opendWidth,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(opendHeight,MeasureSpec.EXACTLY));
    }
}