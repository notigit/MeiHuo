package com.xiaohua.meihuo.okhttp;

/**
 * Created by 汪文豪 on 2016/4/20.
 * 上海创恩信息技术有限公司
 * Chuangen ShangHai Information Technology CO., LTD..
 */
public interface NetWorkBack {
    void onError(String error);
    void onResponse(String t);
}
