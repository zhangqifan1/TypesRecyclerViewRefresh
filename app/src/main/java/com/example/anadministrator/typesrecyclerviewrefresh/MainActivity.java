package com.example.anadministrator.typesrecyclerviewrefresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     *上拉刷新下拉加载网址: http://www.jianshu.com/p/3bf125b4917d
     * 添加
     */
    private Button mBtnAdd;
    /**
     * 删除
     */
    private Button mBtnDelete;
    /**
     * List
     */
    private Button mBtnList;
    /**
     * Grid
     */
    private Button mBtnGrid;
    /**
     * flow
     */
    private Button mBtnFlow;
    private RecyclerView mRecyclerview;
    private List<String> list;
    private MyRecycleViewAdapter myRecycleViewAdapter;
    private Animation animation;
    private DefaultItemAnimator animator;
    private SwipeRefreshLayout mSwipRefreshLayout;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        myRecycleViewAdapter = new MyRecycleViewAdapter(list, this);
        mRecyclerview.setAdapter(myRecycleViewAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        myRecycleViewAdapter.setmMyItemclickListener(new MyRecycleViewAdapter.MyItemclickListener() {
            @Override
            public void itemclick(View view, int position) {
                Toast.makeText(MainActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mSwipRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                //我在List最前面加入一条数据
                list.add(0,"我是新滴");

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                myRecycleViewAdapter.notifyDataSetChanged();
                mSwipRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerview.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });

    }
    //每次上拉加载的时候，给RecyclerView的后面添加了10条数据数据
    private void loadMoreData() {
        for (int i =0; i < 10; i++){
            list.add("嘿，我是“上拉加载”生出来的"+i);
            myRecycleViewAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("Content_" + i);
        }
    }

    private void initView() {
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(this);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
        mBtnDelete.setOnClickListener(this);
        mBtnList = (Button) findViewById(R.id.btn_list);
        mBtnList.setOnClickListener(this);
        mBtnGrid = (Button) findViewById(R.id.btn_grid);
        mBtnGrid.setOnClickListener(this);
        mBtnFlow = (Button) findViewById(R.id.btn_flow);
        mBtnFlow.setOnClickListener(this);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSwipRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipRefreshLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add://D.添加数据
                list.add(0, "孙子");
                myRecycleViewAdapter.notifyItemInserted(1);
                break;
            case R.id.btn_delete://D.删除数据
                list.remove(2);
                myRecycleViewAdapter.notifyItemRemoved(2);
                break;

            case R.id.btn_list://设置List类型效果

                mRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                break;

            case R.id.btn_grid://设置Grid类型效果
                mRecyclerview.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
                break;

            case R.id.btn_flow://设置瀑布流类型效果
                mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
