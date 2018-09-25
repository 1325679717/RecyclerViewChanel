package com.chanel.myt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.chanel.myt.adapter.MyAdapter;
import com.chanel.myt.library.bean.ColorBean;
import com.chanel.myt.library.view.ChanelView;

import java.util.ArrayList;
import java.util.List;

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
        list.add(new ColorBean(R.drawable.img_one,1));
        list.add(new ColorBean(R.drawable.img_two,1));
        list.add(new ColorBean(R.drawable.img_three,1));
        list.add(new ColorBean(R.drawable.img_four,1));
        list.add(new ColorBean(R.drawable.img_five,1));
    }
//http://smart-prod-phpservice-1255596649.file.myqcloud.com/images/cms/61ba4499faab67410098e4ea995608c7.png
//    http:\/\/smart-test1-php-1255596649.file.myqcloud.com\/images\/cms\/566a6452a40568c09931a141a08217b1.jpg

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
        recyclerView.onlickScrollToPosition(position);
    }
}
