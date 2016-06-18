package com.xiaohua.meihuo.okhttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by 汪文豪 on 2016/4/20.
 * 上海创恩信息技术有限公司
 * Chuangen ShangHai Information Technology CO., LTD..
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private ACache mACache;
    private static final int maxMemory = (int) Runtime.getRuntime().maxMemory();
    private static final int mCacheSize = maxMemory / 8;
    public LruCache<String, Bitmap> bitmaps;

    public ACache getmACache() {
        return mACache;
    }

    public OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager getInstance() {
        synchronized (OkHttpClientManager.class) {
            if (null == mInstance) {
                mInstance = new OkHttpClientManager();
            }
        }
        return mInstance;
    }

    public void initImageCache(Context context) {
        mACache = ACache.get(context);
        bitmaps = new LruCache<String, Bitmap>(mCacheSize) {
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public void request(String url, NetWorkBack back, Map<String, String> param) {
        Request.Builder builder = new Request.Builder().url(url);
        if (param != null) {
            FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                formEncodingBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.post(formEncodingBuilder.build());
        }
        Request request = builder.build();
        deliveryResult(request, back);
    }

    private void deliveryResult(Request request, final NetWorkBack back) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                back.onError(e.toString() + ":  " + request.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String string = response.body().string();
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        back.onResponse(string);
                    }
                });
            }
        });
    }

    public void postFileRequest(String url, NetWorkBack back, Map<String, String> param, PutFile[] files) {
        Request.Builder builder = new Request.Builder().url(url);
        if (files == null || files.length == 0) {
            throw new NullPointerException("Upload the file does not exist!");
        }
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (PutFile file : files) {
            RequestBody fileBody = RequestBody.create(MediaType.parse(file.type), file.file);
            multipartBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + file.key + "\"; filename=\"" + file.file.getName() + "\""), fileBody);
        }
        for (Map.Entry<String, String> entry : param.entrySet()) {
            multipartBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), RequestBody.create(null, entry.getValue()));
        }
        builder.post(multipartBuilder.build());
        Request request = builder.build();
        deliveryResult(request, back);
    }

    public void downloadFile(final String url, final String destFileDir, final NetWorkBack back) {
        final Request request = new Request.Builder().url(url).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(Request request, IOException e) {
                             back.onError(e.toString() + ":  " + request.toString());
                         }

                         @Override
                         public void onResponse(Response response) throws IOException {
                             InputStream is = null;
                             byte[] buf = new byte[3072];
                             int len;
                             FileOutputStream fos = null;
                             try {
                                 is = response.body().byteStream();
                                 File file = new File(destFileDir, getFileName(url));
                                 fos = new FileOutputStream(file);
                                 while ((len = is.read(buf)) != -1) {
                                     fos.write(buf, 0, len);
                                 }
                                 fos.flush();
                                 //如果下载文件成功，第一个参数为文件的绝对路径
                                 back.onResponse(file.getAbsolutePath());
                             } catch (IOException e) {
                                 back.onError(e.toString() + ": " + response.request());
                             } finally {
                                 try {
                                     if (is != null) is.close();
                                 } catch (IOException ignored) {
                                 }
                                 try {
                                     if (fos != null) fos.close();
                                 } catch (IOException ignored) {
                                 }
                             }
                         }
                     }

        );
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    public void loadingImage(final ImageView view, final String url, final int errorResId) {
        Bitmap bitmap = bitmaps.get(url);
        if (null != bitmap) {
            view.setImageBitmap(bitmap);
            return;
        } else if (null != mACache) {
            bitmap = mACache.getAsBitmap(url);
            if (null != bitmap) {
                view.setImageBitmap(bitmap);
                return;
            }
        }
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                setErrorResId(view, errorResId);
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                try {
                    is = response.body().byteStream();
                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false;
                    final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                    bitmaps.put(url, bm);
                    if (null != mACache)
                        mACache.put(url, bm);
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            if (view.getTag().equals(url))
                                view.setImageBitmap(bm);
                            else
                                setErrorResId(view, errorResId);
                        }
                    });
                } catch (Exception e) {
                    setErrorResId(view, errorResId);

                } finally {
                    if (is != null) try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setErrorResId(final ImageView view, final int errorResId) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                view.setImageResource(errorResId);
            }
        });
    }
}
