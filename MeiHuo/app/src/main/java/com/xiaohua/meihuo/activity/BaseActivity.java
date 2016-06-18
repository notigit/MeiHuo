package com.xiaohua.meihuo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.utils.ActivityFinsh;
import com.xiaohua.meihuo.utils.UserUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2014/10/26 0026.
 */
public class BaseActivity extends FragmentActivity {

    public FragmentActivity mActivity;
    private onTitleOnClickListener listener;
    private LinearLayout right_root;
    LinearLayout title_left_image;
    TextView title_center_textView;

    protected void setOnTitleOnClickListener(onTitleOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        ActivityFinsh.getInstance().addActivity(this);
    }

    /**
     * 设置头部title
     *
     * @param title 标题
     */
    protected void setTitleTextView(String title) {
        title_center_textView = (TextView) findViewById(R.id.title_center_textView);
        title_center_textView.setText(title);
    }


    /**
     * 设置返回按钮
     */
    protected void setTitileReturn_button() {
        title_left_image = (LinearLayout) findViewById(R.id.title_left_image);
        title_left_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.leftOnClickListener();
                finish();
            }
        });
    }

    /**
     * 设置右侧按钮图片
     */
    protected void setRegihtButIcon(int rightDrawableRes) {
        LinearLayout right_root = (LinearLayout) findViewById(R.id.right_root);
        right_root.setBackgroundResource(rightDrawableRes);
    }


    /**
     * 设置右侧按钮点击事件
     */
    protected void setRegihtBut() {
        right_root = (LinearLayout) findViewById(R.id.right_root);
        right_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.RightTextViewOnClickListener();
            }
        });
    }

    /**
     * 设置右侧按钮文字
     */
    protected void setRightTextView(String rightText) {
        TextView right_text = (TextView) findViewById(R.id.right_text);
        right_text.setText(rightText);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return super.onTouchEvent(event);
    }

    public interface onTitleOnClickListener {
        /**
         * 左边边图片的点击事件
         */
        void leftOnClickListener();

        /**
         * 右边图片的点击事件
         */
        void RightImageClickListener();

        /**
         * 右边textView的点击事件
         */
        void RightTextViewOnClickListener();
    }

    /**
     * 右边的TextView是否隐藏
     *
     * @param sta
     */
    public void setVisibility(Boolean sta) {
        right_root = (LinearLayout) findViewById(R.id.right_root);
        if (sta) {
            right_root.setVisibility(View.VISIBLE);
            right_root.setClickable(true);
        } else {
            right_root.setVisibility(View.INVISIBLE);
            right_root.setClickable(false);
        }
    }

    /**
     * 吐司
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 将数据写入XML文件
     *
     * @param key
     * @param value
     */
    protected void setDataToXml_APP(String key, boolean value) {
        UserUtil.SharedPerferencesCreat(this, "app", key, value);
    }

    /**
     * 从XML文件获取数据
     *
     * @param key
     */
    protected boolean getDataFromXml_APP(String key) {
        return UserUtil.ReadSharedPerference(this, "app", key);
    }

    /**
     * 从XML文件获取数据
     *
     * @param key
     */
    protected boolean getDataFromXml_X(String key) {
        return UserUtil.ReadSharedPerference(this, "x", key);
    }
    /*友盟*/
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onResume(this);
    }
}
