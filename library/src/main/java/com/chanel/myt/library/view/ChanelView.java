package com.chanel.myt.library.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.chanel.myt.library.utils.ViewUtils;

public class ChanelView extends RecyclerView {
    private float velocityFactor = 0.2f;
    private float mTriggerOffset = 0.5f;
    private int mPositionOnTouchDown;
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

    private void onCreate(){
        addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrolled(recyclerView,dx,dy);
            }
        });
    }
    private void scrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
        int visibleItemCount = getChildCount();
        for (int i = 0;i< visibleItemCount;i++){
            View childView = getChildAt(i);
            if (!(childView instanceof ChanelItemView)) break;
            ChanelItemView chanelItemView = (ChanelItemView) getChildAt(i);
            if(childView.getTop()< 0 && childView.getBottom() <= ChanelItemView.opendHeight){//展开的
                chanelItemView.updateView(1.0f);
            }else if (childView.getTop() < ChanelItemView.opendHeight){//正在展开的
                float f = (1 - ((float)chanelItemView.getTop())/ChanelItemView.opendHeight);//[0,1]
                chanelItemView.updateView(f);
            }else {//折叠的
                chanelItemView.updateView(0f);
            }
        }
    }
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == SCROLL_STATE_DRAGGING){
            mNeedAdjust = true;
            currentView = ViewUtils.getOpenChildView(this);
            if (currentView != null) {
                mFirstTopWhenDragging = currentView.getTop();
            }
        }else if (state == SCROLL_STATE_SETTLING){
            currentView = null;
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
            Log.i("ChanelView","dispatchTouchEvent mPositionOnTouchDown = "+mPositionOnTouchDown);
        }
        return super.dispatchTouchEvent(ev);
    }



    private int safeTargetPosition(int position, int count) {
        if (position <= 0) {
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
            int top = ChanelItemView.opendHeight * position;
            smoothScrollBy(0,top);
        }
    }
    private void scollToPosition(int targetPosition,int type) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if (targetPosition <= firstItem){//targetPosition <= firstItem
            smoothScrollToPosition(targetPosition);
            Log.i("ChanelView","scollToPosition  if targetPosition= "+targetPosition+",type = "+type);
        }else if (targetPosition <= lastItem){
            int position = targetPosition - firstItem;
            int top = getChildAt(position).getTop();
            smoothScrollBy(0,top);
            Log.i("ChanelView","scollToPosition  else if targetPosition= "+targetPosition+",type =  "+type);
        }else{
            smoothScrollToPosition(targetPosition);
            Log.i("ChanelView","scollToPosition  else targetPosition= "+",type = "+type);
        }
    }

}
