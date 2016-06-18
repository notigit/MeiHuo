package com.xiaohua.meihuo.okhttp;

import java.io.File;

/**
 * Created by 汪文豪 on 2016/4/20.
 * 上海创恩信息技术有限公司
 * Chuangen ShangHai Information Technology CO., LTD..
 */
public class PutFile {
    public String type;
    public File file;
    public String key;

    public PutFile(String type, File file, String key) {
        this.type = type;
        this.file = file;
        this.key = key;
    }
}
