package com.xiaohua.meihuo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2016/5/17.
 */
public class MyInstalledReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {  // install
            String packageName = intent.getDataString();
            Log.e("homer", "安装了 :" + packageName);
//            Intent myIntent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
//            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "手机1夺宝");
//            // 要删除的应用程序的ComponentName，即应用程序包名+activity的名字
//            ComponentName comp = new ComponentName("com.bannisha.meihuo","com.bannisha.meihuo.activity"+"."+"LoadingActivity");
//            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,new Intent().setComponent(comp).setAction("android.intent.action.MAIN"));
//            context.sendBroadcast(myIntent);
//            Log.e("homer", "success");

//            Log.e("lala",context+"....."+intent);
//            Intent myIntent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
//            myIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "手机夺宝");
//            Intent intent1 = new Intent();
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent1.setAction(android.content.Intent.ACTION_VIEW);
//            intent1.setAction("android.intent.action.MAIN");
//            intent1.setDataAndType(Uri.fromFile(new File("/sdcard/update/sjdb_324.apk")), "application/vnd.android.package-archive");
//
//            Intent intent2 = new Intent(Intent.ACTION_MAIN);
//            intent2.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//            intent2.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
//            intent2.addCategory(Intent.CATEGORY_LAUNCHER);
//            intent2.setClass(context, MainActivity.class);
//
//            // 要删除的应用程序的ComponentName，即应用程序包名+activity的名字
////            myIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent1);
//            myIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2);
//            context.sendBroadcast(myIntent);
//            Log.e("homer", "success");
        }
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) { // uninstall
            String packageName = intent.getDataString();
            Log.e("homer", "卸载了 :" + packageName);
        }
    }
}

