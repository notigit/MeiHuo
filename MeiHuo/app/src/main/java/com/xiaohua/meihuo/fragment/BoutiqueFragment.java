package com.xiaohua.meihuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.activity.DetailActivity;
import com.xiaohua.meihuo.bean.URLBean;
import com.xiaohua.meihuo.utils.ActivityFinsh;

/**
 * Created by Administrator on 2016/5/6.
 */
public class BoutiqueFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    WebView webView;
    SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_boutique, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.boutique_srl);
        refreshLayout.setColorSchemeResources(R.color.dark_purple);
        refreshLayout.setOnRefreshListener(this);
        webView = (WebView) view.findViewById(R.id.boutique_wv);
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //设置Web视图
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        //加载需要显示的视图
        webView.loadUrl(URLBean.INDEX_BOUTIQUE);
        //必须设置
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setOnKeyListener(backlistener);
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("URL",url);
            view.getContext().startActivity(intent);
            return true;
        }
    }


    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        webView.reload();
        refreshLayout.setRefreshing(false);
    }

    private View.OnKeyListener backlistener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                if (i == KeyEvent.KEYCODE_BACK) {  //表示按返回键 时的操作
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        ActivityFinsh.getInstance().exit();
                    }
                    return true;    //已处理
                }
            }
            return false;
        }
    };
}
