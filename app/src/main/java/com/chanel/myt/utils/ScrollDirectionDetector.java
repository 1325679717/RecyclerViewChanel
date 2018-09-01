package com.chanel.myt.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * This class detects a {@link ScrollDirection} ListView is scrolled to.
 * And then call {@link OnDetectScrollListener#onScrollDirectionChanged(ScrollDirection)}
 *
 * @author danylo.volokh
 */
public class ScrollDirectionDetector {

    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
    private static final String TAG = ScrollDirectionDetector.class.getSimpleName();

    private final OnDetectScrollListener mOnDetectScrollListener;

    private int mOldTop;
    private int mOldFirstVisibleItem;

    private ScrollDirection mOldScrollDirection = null;

    public ScrollDirectionDetector(OnDetectScrollListener onDetectScrollListener) {
        mOnDetectScrollListener = onDetectScrollListener;
    }

    public interface OnDetectScrollListener{
        void onScrollDirectionChanged(ScrollDirection scrollDirection);
    }

    public enum ScrollDirection{
        UP, DOWN
    }

    public void onDetectedListScroll(RecyclerView.LayoutManager layoutManager, int firstVisibleItem) {
        if(SHOW_LOGS) Logger.v(TAG, ">> onDetectedListScroll, firstVisibleItem " + firstVisibleItem + ", mOldFirstVisibleItem " + mOldFirstVisibleItem);

        View view = layoutManager.getChildAt(0);
        int top = (view == null) ? 0 : view.getTop();
        if(SHOW_LOGS) Logger.v(TAG, "onDetectedListScroll, view " + view + ", top " + top + ", mOldTop " +mOldTop);

        if (firstVisibleItem == mOldFirstVisibleItem) {
            if (top > mOldTop) {
                onScrollUp();
            } else if (top < mOldTop) {
                onScrollDown();
            }
        } else {
            if (firstVisibleItem < mOldFirstVisibleItem) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }

        mOldTop = top;
        mOldFirstVisibleItem = firstVisibleItem;
        if(SHOW_LOGS) Logger.v(TAG, "<< onDetectedListScroll");
    }

    private void onScrollDown() {
        if(SHOW_LOGS) Logger.v(TAG, "onScroll Down");

        if(mOldScrollDirection != ScrollDirection.DOWN){
            mOldScrollDirection = ScrollDirection.DOWN;
            mOnDetectScrollListener.onScrollDirectionChanged(ScrollDirection.DOWN);
        } else {
            if(SHOW_LOGS) Logger.v(TAG, "onDetectedListScroll, scroll state not changed " + mOldScrollDirection);
        }
    }

    private void onScrollUp() {
        if(SHOW_LOGS) Logger.v(TAG, "onScroll Up");

        if(mOldScrollDirection != ScrollDirection.UP) {
            mOldScrollDirection = ScrollDirection.UP;
            mOnDetectScrollListener.onScrollDirectionChanged(ScrollDirection.UP);
        } else {
            if(SHOW_LOGS) Logger.v(TAG, "onDetectedListScroll, scroll state not changed " + mOldScrollDirection);
        }
    }
}
