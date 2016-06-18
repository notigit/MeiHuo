package com.xiaohua.meihuo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.utils.ActivityFinsh;
import com.xiaohua.meihuo.utils.ChannelUtil;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/5/6.
 */
public class LoadingActivity extends Activity {
    ImageView loadingIV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        ActivityFinsh.getInstance().addActivity(this);
        setContentView(R.layout.meihuo_loading);
        AnalyticsConfig.setChannel(ChannelUtil.getChannel(this, "defaultChannel"));//动态设置channel
        // MobclickAgent.setDebugMode(true);//使用集成测试服务
        // Toast.makeText(LoadingActivity.this, ChannelUtil.getChannel(LoadingActivity.this,"defaultChannel"),Toast.LENGTH_SHORT).show();
        loadingIV = (ImageView) findViewById(R.id.meihuo_iv_loading);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading_scale);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        loadingIV.setAnimation(animation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

}
