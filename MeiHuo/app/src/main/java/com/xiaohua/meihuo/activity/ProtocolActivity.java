package com.xiaohua.meihuo.activity;

import android.os.Bundle;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.utils.ActivityFinsh;


/**
 * Created by Administrator on 2016/5/6.
 */
public class ProtocolActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        ActivityFinsh.getInstance().addActivity(this);
        setContentView(R.layout.user_protocol);
        setRightTextView("");
        setTitleTextView("用户协议");
        setTitileReturn_button();
    }
}
