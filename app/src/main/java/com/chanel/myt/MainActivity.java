package com.chanel.myt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.chanel.myt.adapter.MyAdapter;
import com.chanel.myt.bean.ColorBean;
import com.chanel.myt.utils.LinearLayoutManagerWithSmoothScroller;
import com.chanel.myt.view.ChanelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ChanelView recyclerView;
    List<ColorBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        initData();
        LinearLayoutManagerWithSmoothScroller layout = new LinearLayoutManagerWithSmoothScroller(this, LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(new MyAdapter(list,this));
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
        list.add(colorBean0);
        list.add(colorBean1);
        list.add(colorBean2);
        list.add(colorBean3);
        list.add(colorBean4);
        list.add(colorBean5);
        list.add(colorBean6);
        list.add(colorBean7);
        list.add(colorBean8);
    }
}
