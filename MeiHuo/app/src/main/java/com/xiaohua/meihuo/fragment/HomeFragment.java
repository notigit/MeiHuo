package com.xiaohua.meihuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.activity.DetailActivity;
import com.xiaohua.meihuo.bean.HomeBean;
import com.xiaohua.meihuo.bean.URLBean;
import com.xiaohua.meihuo.customerview.ImageCycleView;
import com.xiaohua.meihuo.okhttp.NetWorkBack;
import com.xiaohua.meihuo.okhttp.OkHttpClientManager;
import com.xiaohua.meihuo.recyclerview.FullyGridLayoutManager;
import com.xiaohua.meihuo.utils.ActivityFinsh;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    ImageCycleView imageCycleView;
    ArrayList<HomeBean> homeData;
    OkHttpClientManager okHttpClientManager;
    private HomeBean homeBean;
    private ArrayList<String> urlData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_home, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        okHttpClientManager = OkHttpClientManager.getInstance();
        okHttpClientManager.request(URLBean.INDEX_HOME, new HomeNetBack(), null);
        homeData = new ArrayList<>();
    }


    private void initView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_srl);
        refreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        imageCycleView = (ImageCycleView) view.findViewById(R.id.home_icv);
        recyclerView.setHasFixedSize(true);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
//        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
//        manager.setOrientation(GridLayoutManager.VERTICAL);
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new HomeAdapter());
    }

    @Override
    public void onRefresh() {
        //刷新完成
        refreshLayout.setRefreshing(false);
    }

    private class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
        @Override
        public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_rv, parent, false);
            return new HomeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HomeViewHolder holder, int position) {
            HomeBean.VideosEntity videosEntity = homeData.get(0).getVideos().get(position);
            Picasso.with(getActivity()).load(videosEntity.getFace()).into(holder.imageView);
            holder.textView.setText(videosEntity.getTitle());
        }

        @Override
        public int getItemCount() {
            return homeData.size();
        }

        class HomeViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            public HomeViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.rv_iv);
                textView = (TextView) itemView.findViewById(R.id.rv_tv);
            }
        }
    }

    private class HomeNetBack implements NetWorkBack {
        @Override
        public void onError(String error) {
            Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String t) {
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HomeBean>() {
            }.getType();
            homeBean = gson.fromJson(t, type);
            homeData.add(homeBean);

            urlData = new ArrayList<>();
            for (int i = 0; i < homeData.get(0).getBanner().size(); i++) {
                urlData.add(homeData.get(0).getBanner().get(i).getFace());
            }
            Toast.makeText(getActivity(), urlData.toString(), Toast.LENGTH_SHORT).show();

            imageCycleView.setImageResources(urlData, new ImageCycleView.ImageCycleViewListener() {
                @Override
                public void displayImage(String imageURL, ImageView imageView) {
                    Picasso.with(getActivity()).load(imageURL).into(imageView);
                }

                @Override
                public void onImageClick(int position, View imageView) {

                }
            });
        }
    }
}
