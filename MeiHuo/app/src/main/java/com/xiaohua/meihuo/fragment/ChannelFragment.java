package com.xiaohua.meihuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.activity.DetailActivity;
import com.xiaohua.meihuo.bean.URLBean;
import com.xiaohua.meihuo.utils.ActivityFinsh;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ChannelFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_channel,container,false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {

    }

    private void initView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.channel_srl);
        refreshLayout.setColorSchemeResources(R.color.dark_purple);
        refreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.channel_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyAdapter());
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.channel_rv_item,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView image;
            TextView title,update;
            public MyViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.channel_iv);
                title = (TextView) itemView.findViewById(R.id.channel_tv_title);
                update = (TextView) itemView.findViewById(R.id.channel_tv_update);
            }
        }
    }
}
