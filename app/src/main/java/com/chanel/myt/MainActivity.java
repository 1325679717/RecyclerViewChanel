package com.chanel.myt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.chanel.myt.adapter.MyAdapter;
import com.chanel.myt.bean.ColorBean;
import com.chanel.myt.listener.ItemClickListener;
import com.chanel.myt.utils.LinearLayoutManagerWithSmoothScroller;
import com.chanel.myt.view.ChanelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* 1.上拉会滑动多个，并且可能停在item任何位置(可能smoothScrollToPosition->(targetPosition > currentPosition))
* 2.拖动停留
* 3.
* */
public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    private ChanelView recyclerView;
    private MyAdapter myAdapter;
    List<ColorBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        initData();
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layout);
        myAdapter = new MyAdapter(list,this);
        recyclerView.setAdapter(myAdapter);
        myAdapter.addOnItemClickListener(this);
        runLayoutAnimation(recyclerView);
    }
    private void initData(){
        ColorBean colorBean0 = new ColorBean(R.color.red,1);
        ColorBean colorBean1 = new ColorBean(R.color.color_2aa094,1);
        ColorBean colorBean2 = new ColorBean(R.color.color_F5A623,1);
        ColorBean colorBean3 = new ColorBean(R.color.colorAccent,1);
        ColorBean colorBean4 = new ColorBean(R.color.colorPrimaryDark,1);
        ColorBean colorBean5 = new ColorBean(R.color.color_378983,1);
        ColorBean colorBean6 = new ColorBean(R.color.colorPrimary,1);
        ColorBean colorBean7 = new ColorBean(R.color.def_pointer_color,2);
        ColorBean colorBean8 = new ColorBean(R.color.def_pointer_color,2);
        list.add(new ColorBean(R.drawable.iv_splash_bg,1));
        list.add(new ColorBean(R.drawable.system_bg,1));
        list.add(new ColorBean(R.drawable.image_one,1));
        list.add(new ColorBean(R.drawable.image_four,1));
        list.add(new ColorBean(R.drawable.image_one,1));
//        list.add(colorBean5);
//        list.add(colorBean6);
        list.add(colorBean7);
        list.add(colorBean8);
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.recycler_animation);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
    @Override
    public void onItemClick(View view, int position) {
        if (position < myAdapter.getItemCount() - 2)
            recyclerView.onlickScrollToPosition(position);
    }
}
