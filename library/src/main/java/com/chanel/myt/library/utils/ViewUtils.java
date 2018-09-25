package com.chanel.myt.library.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chanel.myt.library.view.ChanelItemView;

public class ViewUtils {
    public static int getCenterOpenChildViewPosition(RecyclerView recyclerView){

        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildOnOpen(recyclerView,child)) {
                    return recyclerView.getChildAdapterPosition(child);
                }
            }
        }
        return childCount;
    }
    public static boolean isChildOnOpen(RecyclerView recyclerView,View view){

        int childCount = recyclerView.getChildCount();
        int[] lvLocationOnScreen = new int[2];
        int[] vLocationOnScreen = new int[2];
        recyclerView.getLocationOnScreen(lvLocationOnScreen);
        int middleY = lvLocationOnScreen[1] + ChanelItemView.opendHeight / 2;
        if (childCount > 0) {
            view.getLocationOnScreen(vLocationOnScreen);
            if (vLocationOnScreen[1] <= middleY && vLocationOnScreen[1] + view.getHeight() >= middleY) {
                return true;
            }
        }
        return false;
    }
    public static View getOpenChildView(RecyclerView recyclerView){
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildOnOpen(recyclerView,child)) {
                    return child;
                }
            }
        }
        return null;
    }
}
