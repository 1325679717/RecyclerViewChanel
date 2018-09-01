package com.chanel.myt.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chanel.myt.R;
import com.chanel.myt.utils.ScrollDirectionDetector;

public class ChanelView extends RecyclerView implements ScrollDirectionDetector.OnDetectScrollListener {
    private ScrollDirectionDetector scrollDirectionDetector;
    private float velocityFactor = 0.25f;
    private ScrollDirectionDetector.ScrollDirection mOldScrollDirection = null;
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
        return super.fling((int) (velocityX *velocityFactor), (int) (velocityY *velocityFactor));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        scrollDirectionDetector = new ScrollDirectionDetector(this);
//        scrollDirectionDetector.onDetectedListScroll(getLayoutManager(),0);
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
            View childView = linearLayoutManager.getChildAt(i);
            if (!(childView instanceof ChanelItemView)) break;
            ChanelItemView chanelItemView = (ChanelItemView) linearLayoutManager.getChildAt(i);
            float f = (1 - ((float)chanelItemView.getTop())/chanelItemView.getOpendHeight());
            if(f >=1){//展开的
                chanelItemView.setParallaxOffset(1);
                chanelItemView.parallaxOpen(1.0f);
            }else if (f >= 0 && f < 1){//正在展开的
                chanelItemView.setParallaxOffset(0);
                chanelItemView.parallaxOpening(f);
            }else {//折叠的
                chanelItemView.setParallaxOffset(0);
                chanelItemView.parallaxFolded(0.2f);
            }
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        Log.i("ChanelView","onScrollStateChanged state = "+state+",mOldScrollDirection = "+mOldScrollDirection);
        if (state == SCROLL_STATE_DRAGGING){

        }else if (state == SCROLL_STATE_SETTLING){
            adjustPosition(state);
        }else if (state == SCROLL_STATE_IDLE){

        }
    }
    private void adjustPosition(int state){
        int count = getLayoutManager().getChildCount();
        if (count < 2) return;

        for (int i = 0;i< 2;i++){
            View childView = getChildAt(i);
            int percents = getVisibilityPercents(childView);
            if (percents > 50){
                int targetPosition = getChildAdapterPosition(childView);
                Log.i("ChanelView","adjustPosition = "+targetPosition+",percents = "+percents+",state = "+state);
                smoothScrollToPosition(targetPosition);
            }
        }
    }
    public int getVisibilityPercents(View view) {

        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);

        int height = view.getHeight();

        if(viewIsPartiallyHiddenTop()){
            // view is partially hidden behind the top edge
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if(viewIsPartiallyHiddenBottom(height)){
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        return percents;
    }
    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    @Override
    public void onScrollDirectionChanged(ScrollDirectionDetector.ScrollDirection scrollDirection) {
        mOldScrollDirection = scrollDirection;
    }
}
