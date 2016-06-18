package com.xiaohua.meihuo.utils;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * 完全退出
 * Created by Administrator on 2016/1/8.
 */
public class ActivityFinsh extends Application {


    private List<Activity> activityList = new LinkedList<Activity>();
    private static ActivityFinsh instance;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private ActivityFinsh() {
    }

    //单例模式中获取唯一的MyApplication实例
    public static ActivityFinsh getInstance() {
        if (null == instance) {
            instance = new ActivityFinsh();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }



}
