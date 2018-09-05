package com.chanel.myt.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.chanel.myt.utils.ViewUtils;

public class ChanelView extends RecyclerView{
    private float velocityFactor = 0.25f;
    private float mTriggerOffset = 0.5f;
    private int mFirstTopWhenDragging;
    private View currentView;//当前展开的view
    private Rect mCurrentViewRect = new Rect();
    public ChanelView(@NonNull Context context) {
        super(context);
        onCreate();
    }

    public ChanelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onCreate();
    }

    public ChanelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onCreate();
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        boolean fling = super.fling((int) (velocityX *velocityFactor), (int) (velocityY *velocityFactor));
        adjustPostionY(velocityY);
        return fling;
    }
    private void adjustPostionY(int velocityY){
        int childCount = getChildCount();
        if (childCount > 0) {
            int curPosition = ViewUtils.getCenterOpenChildViewPosition(this);
            int flingCount = getFlingCount(velocityY, ChanelItemView.opendHeight);
            int targetPostion = curPosition + flingCount;
            smoothScrollToPosition(safeTargetPosition(targetPostion,getItemCount()));
            Log.i("ChanelView","adjustPostionY flingCount = "+flingCount+",curPosition = "+curPosition);
        }
    }
    private int getItemCount() {
        return getAdapter().getItemCount();
    }
    private int getFlingCount(int velocity, int cellSize) {
        if (velocity == 0) {
            return 0;
        }
        int sign = velocity > 0 ? 1 : -1;
        return (int) (sign * Math.ceil((velocity * sign * velocityFactor / cellSize)
                - mTriggerOffset));
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void onCreate(){
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scroll(recyclerView,dx,dy);
            }
        });
    }
    private void scroll(@NonNull RecyclerView recyclerView, int dx, int dy){
        int visibleItemCount = getChildCount();
        for (int i = 0;i< visibleItemCount;i++){
            View childView = getChildAt(i);
            if (!(childView instanceof ChanelItemView)) break;
            ChanelItemView chanelItemView = (ChanelItemView) getChildAt(i);
            float f = (1 - ((float)chanelItemView.getTop())/ChanelItemView.opendHeight);
            if(f >=1){//展开的
                currentView = childView;
                chanelItemView.setParallaxOffset(1);
                chanelItemView.setState(ChanelItemView.OPEN);
                chanelItemView.parallaxOpen(1.0f);
            }else if (f >= 0 && f < 1){//正在展开的
                chanelItemView.setParallaxOffset(0);
                chanelItemView.setState(ChanelItemView.FOLDED);
                chanelItemView.parallaxOpening(f);
            }else {//折叠的
                chanelItemView.setParallaxOffset(0);
                chanelItemView.setState(ChanelItemView.CLOSE);
                chanelItemView.parallaxFolded(0.2f);
            }
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == SCROLL_STATE_DRAGGING){
            if (currentView != null) {
                mFirstTopWhenDragging = currentView.getTop();
            }
        }else if (state == SCROLL_STATE_SETTLING){
        }else if (state == SCROLL_STATE_IDLE){

//            adjustPosition(state);
        }
    }

    @Override
    public void smoothScrollToPosition(int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(getContext());
        linearSmoothScroller.setTargetPosition(position);
        getLayoutManager().startSmoothScroll(linearSmoothScroller);
    }

    private int safeTargetPosition(int position, int count) {
        if (position < 0) {
            return 0;
        }
        if (position >= count) {
            return count - 1;
        }
        return position;
    }
    private int getYLoactionOnScreen(View view){
        int[] points = new int[2];
        view.getLocationOnScreen(points);
        return points[1];
    }
    private void adjustPosition(int state){
        int count = getChildCount();
        for (int i = 0;i<count;i++){
            View childView = getChildAt(i);
            if (childView.getHeight() > ChanelItemView.opendHeight * mTriggerOffset){
                int targetPosition = getChildAdapterPosition(childView);
                smoothScrollToPosition(targetPosition);
            }
        }
    }

}
