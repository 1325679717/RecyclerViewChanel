package com.chanel.myt.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chanel.myt.R;
import com.chanel.myt.utils.DisPlayUtils;

public class ChanelItemView extends RelativeLayout {
    public static int opendHeight = 0;

    public static int OPEN = 0;

    public static int FOLDED = 1;

    public static int CLOSE = 2;

    private int opendWidth = 0;
    public static int foldedHeight = 0;


    private TextView textView;

    private int state = OPEN;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
        textView = findViewById(R.id.iv);
    }

    public void parallaxOpen(float f){
        textView.getLayoutParams().height = opendHeight;
        textView.getLayoutParams().width = opendWidth;
        requestLayout();
    }
    public void parallaxOpening(float f){//0-1
        int height = foldedHeight + (int) ((opendHeight -foldedHeight)* f);
        if (height >= foldedHeight && height <= opendHeight) {
            textView.getLayoutParams().height = height;
            textView.getLayoutParams().width = opendWidth;
            requestLayout();
        }
    }
    public void parallaxFolded(float f){
        textView.getLayoutParams().height = foldedHeight;
        textView.getLayoutParams().width = opendWidth;
        requestLayout();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textView.measure(MeasureSpec.makeMeasureSpec(opendWidth,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(opendHeight,MeasureSpec.EXACTLY));
//        int imgHeight =
//        setMeasuredDimension();
    }

    public TextView getImageView() {
        return textView;
    }
}
