package com.xiaohua.meihuo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.dialog.PayDialog;
import com.xiaohua.meihuo.utils.UserUtil;

/**
 * Created by Administrator on 2016/5/10.
 */
public class DetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    WebView webView;
    String url;
    SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        setRightTextView("");
        setTitleTextView("");
        setTitileReturn_button();
        url = getIntent().getStringExtra("URL");
        webView = (WebView) findViewById(R.id.detail_wv);
        layout = (SwipeRefreshLayout) findViewById(R.id.detail_srl);
        layout.setOnRefreshListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new HomeInterface(), "video");
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
    }
    class HomeInterface{
        @JavascriptInterface
        public void callOnHomeTitle(String title){
//            showToast(title);
            setTitleTextView(title);
        }
        //播放视频或者弹出支付对话框
        @JavascriptInterface
        public void callOnHomeJS(String url){
            //是否是VIP
            if (UserUtil.ReadSharedPerference(DetailActivity.this, "IS_VIP", "is_vip")){
                //直接播放
                Intent intent = new Intent(DetailActivity.this, PlayActivity.class);
                intent.putExtra("VIDEO_URL",url);
                startActivity(intent);
            }else {
                //付费播放
                PayDialog payDialog = new PayDialog(DetailActivity.this,url);
                payDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                payDialog.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) DetailActivity.this.getSystemService(Context.WINDOW_SERVICE);
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                WindowManager.LayoutParams lp = payDialog.getWindow().getAttributes();
                lp.width = displayMetrics.widthPixels; // 设置宽度
                payDialog.getWindow().setAttributes(lp);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    DetailActivity.this.finish();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRefresh() {
        layout.setRefreshing(true);
        webView.reload();
        layout.setRefreshing(false);
    }
}
