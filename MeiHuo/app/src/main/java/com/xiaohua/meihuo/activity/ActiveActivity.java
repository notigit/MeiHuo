package com.xiaohua.meihuo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.utils.ActivityFinsh;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ActiveActivity extends BaseActivity{
    EditText number;
    Button active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.user_active);
        setTitileReturn_button();
        setTitleTextView("手动激活");
        setRightTextView("");
        ActivityFinsh.getInstance().addActivity(this);
        number = (EditText) findViewById(R.id.active_et);
        active = (Button) findViewById(R.id.active_btn);
        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断单号是否为空
                if (number.getText().toString().equals("")){
                    showToast("单号不能为空");
                }else {
//                    UserUtil.SharedPerferencesCreat(ActiveActivity.this,"IS_VIP","is_vip",true);
                    showToast("单号错误");
                }
            }
        });
    }

}
