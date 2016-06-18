package com.xiaohua.meihuo.bean;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.xiaohua.meihuo.activity.PlayActivity;
import com.xiaohua.meihuo.utils.UserUtil;

import java.net.URL;

/**
 * Created by Administrator on 2016/5/11.
 */
public class PlayJavaScript {
    Context context;

    public PlayJavaScript(Context context) {
        this.context = context;
    }


    @JavascriptInterface
    public void callOnChannelJS(final URL url){
        //是否是VIP
        if (UserUtil.ReadSharedPerference(context,"IS_VIP","is_vip")){
            //直接播放
            Intent intent = new Intent(context, PlayActivity.class);
            intent.putExtra("VIDEO_URL",url);
            context.startActivity(intent);
        }else {
            //付费播放
            UserUtil.SharedPerferencesCreat(context,"IS_VIP","is_vip",true);
            Intent intent = new Intent(context, PlayActivity.class);
            intent.putExtra("VIDEO_URL",url);
            context.startActivity(intent);
        }
    }
    @JavascriptInterface
    public void callOnBoutiqueJS(final URL url){
        //是否是VIP
        if (UserUtil.ReadSharedPerference(context,"IS_VIP","is_vip")){
            //直接播放
            Intent intent = new Intent(context, PlayActivity.class);
            intent.putExtra("VIDEO_URL",url);
            context.startActivity(intent);
        }else {
            //付费播放
        }
    }
}
