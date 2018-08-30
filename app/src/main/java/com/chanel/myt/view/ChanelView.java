package com.chanel.myt.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

public class ChanelView extends RecyclerView {
    LinearLayoutManager linearLayoutManager;
    private float velocityFactor = 0.25f;
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
        return super.fling((int) (velocityX *velocityFactor), (int) (velocityY *velocityFactor));
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
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        int visibleItemCount = linearLayoutManager.getChildCount();
        for (int i = 0;i< visibleItemCount;i++){
            ChanelItemView chanelItemView = (ChanelItemView) linearLayoutManager.getChildAt(i);
            if(chanelItemView.getBottom() <= chanelItemView.getOpendHeight()){//展开的
                chanelItemView.setParallaxOffset(1);
                chanelItemView.parallax(1.0f);
            }else if (chanelItemView.getTop() < chanelItemView.getOpendHeight()){//正在展开的
                Log.i("ChanelView","chanelItemView.getTop = " + ((float)chanelItemView.getTop()/chanelItemView.getOpendHeight()));
                chanelItemView.setParallaxOffset(0);
                chanelItemView.parallax(0.5f);
            }else {//折叠的

                chanelItemView.setParallaxOffset(0);
                chanelItemView.parallax(0.2f);
            }
        }
    }
}
