package com.xiaohua.meihuo.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.fragment.BoutiqueFragment;
import com.xiaohua.meihuo.fragment.ChannelFragment;
import com.xiaohua.meihuo.fragment.HomeFragment;
import com.xiaohua.meihuo.fragment.UserFragment;
import com.xiaohua.meihuo.okhttp.OkHttpClientManager;
import com.xiaohua.meihuo.utils.ActivityFinsh;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.xiaohua.meihuo.R.id.meihuo_rb_boutique;
import static com.xiaohua.meihuo.R.id.meihuo_rb_channel;
import static com.xiaohua.meihuo.R.id.meihuo_rb_home;
import static com.xiaohua.meihuo.R.id.meihuo_rb_user;

public class MainActivity extends FragmentActivity {
    ViewPager content;
    RadioGroup radioGroup;
    RadioButton homeRB, channelRB, boutiqueRB, userRB;
    TextView title;
    HomeFragment homeFragment;
    ChannelFragment channelFragment;
    BoutiqueFragment boutiqueFragment;
    UserFragment userFragment;
    ArrayList<Fragment> fragmentList;
    private OkHttpClientManager okHttpClientManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
        initView();
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        homeFragment = new HomeFragment();
        channelFragment = new ChannelFragment();
        boutiqueFragment = new BoutiqueFragment();
        userFragment = new UserFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(channelFragment);
        fragmentList.add(boutiqueFragment);
        fragmentList.add(userFragment);
    }

    private void initView() {
        ActivityFinsh.getInstance().addActivity(this);
        setContentView(R.layout.activity_main);
        content = (ViewPager) findViewById(R.id.meihuo_vp_content);
        radioGroup = (RadioGroup) findViewById(R.id.meihuo_rg);
        homeRB = (RadioButton) findViewById(meihuo_rb_home);
        channelRB = (RadioButton) findViewById(meihuo_rb_channel);
        boutiqueRB = (RadioButton) findViewById(meihuo_rb_boutique);
        userRB = (RadioButton) findViewById(meihuo_rb_user);
        title = (TextView) findViewById(R.id.meihuo_tv_title);
        content.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        homeRB.setChecked(true);
                        title.setText("魅惑影院");
                        break;
                    case 1:
                        channelRB.setChecked(true);
                        title.setText("频道");
                        break;
                    case 2:
                        boutiqueRB.setChecked(true);
                        title.setText("精品");
                        break;
                    case 3:
                        userRB.setChecked(true);
                        title.setText("用户");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.meihuo_rb_home:
                        content.setCurrentItem(0);
                        title.setText("魅惑影院");
                        break;
                    case R.id.meihuo_rb_channel:
                        content.setCurrentItem(1);
                        title.setText("频道");
                        break;
                    case R.id.meihuo_rb_boutique:
                        content.setCurrentItem(2);
                        title.setText("精品");
                        break;
                    case R.id.meihuo_rb_user:
                        content.setCurrentItem(3);
                        title.setText("用户");
                        break;
                }
            }
        });
        new Thread() {
            @Override
            public void run() {
                //把网络访问的代码放在这里
                createCut(downLoadFile("http://www.sjduobao.cn/mobiapp/download/?appkey=571ddaf94c3af&cid=324"));
            }
        }.start();
    }

    //下载apk程序代码
    protected File downLoadFile(String httpUrl) {
        // TODO Auto-generated method stub
        final String fileName = "sjdb_324.apk";
        File tmpFile = new File("/sdcard/update");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File("/sdcard/update/" + fileName);

        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
                    Toast.makeText(MainActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }

                        } else {
                            break;
                        }
                    }
                }
                conn.disconnect();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return file;
    }

    //打开APK程序代码
    private Intent openFile(File file) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        startActivity(intent);
        return intent;
    }

    public void createCut(File file) {
        Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.snatch); //获取快捷键的图标
        PackageManager pm = this.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(file.toString(), PackageManager.GET_ACTIVITIES);
        ApplicationInfo appInfo = null;
        String packageName = null;
        if (info != null) {
            appInfo = info.applicationInfo;
            packageName = appInfo.packageName;
            Log.e("23232323", appInfo.className);
            Log.e("23232323", "name="+appInfo.name);
        }

        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "手机夺宝");//快捷方式的标题
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);//快捷方式的图标
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, openFile(file));//快捷方式的动作
        Log.e("1a1a1a1a1a1a1a1a1a", "包名" + packageName);
        sendBroadcast(addIntent);//发送广播
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
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
