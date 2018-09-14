package com.chanel.myt.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.chanel.myt.R;
import com.chanel.myt.adapter.MyAdapter;
import com.chanel.myt.listener.ItemClickListener;
import com.chanel.myt.utils.ViewUtils;

public class ChanelView extends RecyclerView {
    private float velocityFactor = 0.25f;
    private float mTriggerOffset = 0.5f;
    private int mPositionOnTouchDown;
    private int mPositionBeforeScroll;
    private View currentView;//当前展开的view
    private int mFirstTopWhenDragging;
    int mMaxTopWhenDragging = Integer.MIN_VALUE;
    int mMinTopWhenDragging = Integer.MAX_VALUE;
    private boolean mNeedAdjust = false;
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
        Log.i("ChanelView","adjustPostionY velocityY= "+velocityY);
        int childCount = getChildCount();
        if (childCount > 0) {
            int curPosition = ViewUtils.getCenterOpenChildViewPosition(this);
            int flingCount = getFlingCount(velocityY, ChanelItemView.opendHeight);
            flingCount = Math.max(-1, Math.min(1, flingCount));
            int targetPosition = flingCount == 0 ? curPosition : mPositionOnTouchDown + flingCount;
            scollToPosition(safeTargetPosition(targetPosition,getItemCount()),0);
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
            float f = (1 - ((float)chanelItemView.getTop())/ChanelItemView.opendHeight);//[0,1]
            setItemViewPercent(chanelItemView,f);
            if(f >=1){//展开的
                chanelItemView.setState(ChanelItemView.OPEN);
                chanelItemView.parallaxOpen(1.0f);
            }else if (f > 0 && f < 1){//正在展开的
                chanelItemView.setState(ChanelItemView.FOLDED);
                chanelItemView.parallaxOpening(f);
            }else {//折叠的
                chanelItemView.setState(ChanelItemView.CLOSE);
                chanelItemView.parallaxFolded(0.2f);
            }
        }
    }
    public void setItemViewPercent(ChanelItemView chanelItemView,float percent){
        ChanelItemText chanelItemText = chanelItemView.findViewById(R.id.chanelItemText);
        chanelItemText.setItemViewPercent(percent);
    }
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == SCROLL_STATE_DRAGGING){
            mNeedAdjust = true;
            currentView = ViewUtils.getOpenChildView(this);
            if (currentView != null) {
                mFirstTopWhenDragging = currentView.getTop();
                mPositionBeforeScroll = getChildLayoutPosition(currentView);
            }
        }else if (state == SCROLL_STATE_SETTLING){

        }else if (state == SCROLL_STATE_IDLE) {
            if (mNeedAdjust) {
                int targetPosition = ViewUtils.getCenterOpenChildViewPosition(this);
                if (currentView != null) {
                    targetPosition = getChildAdapterPosition(currentView);
                    int spanY = currentView.getTop() - mFirstTopWhenDragging;
                    if (spanY < currentView.getHeight() * -mTriggerOffset) {
                        targetPosition++;
                    } else if (spanY > currentView.getHeight() * mTriggerOffset) {
                        targetPosition--;
                    }
                }
                scollToPosition(safeTargetPosition(targetPosition, getItemCount()), 1);
                mNeedAdjust = false;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // recording the max/min value in touch track
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            if (currentView != null) {
                mMaxTopWhenDragging = Math.max(currentView.getTop(), mMaxTopWhenDragging);
                mMinTopWhenDragging = Math.min(currentView.getTop(), mMinTopWhenDragging);
            }
        }
        return super.onTouchEvent(e);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            mPositionOnTouchDown = ViewUtils.getCenterOpenChildViewPosition(this);
//            Log.i("ChanelView","dispatchTouchEvent mPositionOnTouchDown = "+mPositionOnTouchDown);
        }
        return super.dispatchTouchEvent(ev);
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
    public void onlickScrollToPosition(int targetPosition){
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if (targetPosition >0 && targetPosition <=lastItem){
            int position = targetPosition - firstItem;
//            int top = getChildAt(position).getTop() * position;
            int top = ChanelItemView.opendHeight * position;
            smoothScrollBy(0,top);
        }
    }
    private void scollToPosition(int targetPosition,int type) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if (targetPosition <= firstItem ){//targetPosition <= firstItem
            smoothScrollToPosition(targetPosition);
            Log.i("ChanelView","scollToPosition  if targetPosition= "+targetPosition+",type = "+type);
        }else if (targetPosition <= lastItem ){
            int position = targetPosition - firstItem;
            int top = getChildAt(position).getTop();
            smoothScrollBy(0,top);
            Log.i("ChanelView","scollToPosition  else if targetPosition= "+targetPosition+",type = "+type);
        }else{
            smoothScrollToPosition(targetPosition);
            Log.i("ChanelView","scollToPosition  else targetPosition= "+",type = "+type);

        }
    }
/*    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {
        if (getAdapter() != null && getLayoutManager() instanceof LinearLayoutManager) {
            LayoutAnimationController.AnimationParameters animationParameters = params.layoutAnimationParameters;
            if (animationParameters == null) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
                alphaAnimation.setDuration(1000);
                animationParameters = new LayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = animationParameters;
            }
            animationParameters.count = count;
            animationParameters.index = index;
        } else {
            super.attachLayoutAnimationParameters(child, params, index, count);
        }
    }*/

}
