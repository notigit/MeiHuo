package com.xiaohua.meihuo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.xiaohua.meihuo.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/5/10.
 */
public class PlayActivity extends Activity {
    VideoView videoView;
    String url ;
    ProgressBar progressBar;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        url = getIntent().getStringExtra("VIDEO_URL");
        videoView = (VideoView) findViewById(R.id.play_vv);
        progressBar = (ProgressBar) findViewById(R.id.play_pb);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setFocusable(true);
        videoView.setVideoURI(Uri.parse(url));
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    //此接口每次回调完START就回调END,若不加上判断就会出现缓冲图标一闪一闪的卡顿现象
                    if (mp.isPlaying()) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                videoView.start();
            }
        });
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
