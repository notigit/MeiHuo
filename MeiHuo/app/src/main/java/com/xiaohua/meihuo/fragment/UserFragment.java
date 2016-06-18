package com.xiaohua.meihuo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.activity.ActiveActivity;
import com.xiaohua.meihuo.activity.ProtocolActivity;
import com.xiaohua.meihuo.dialog.PayDialog;

/**
 * Created by Administrator on 2016/5/6.
 */
public class UserFragment extends Fragment implements View.OnClickListener{
    RelativeLayout service,active,contact,protocol,update;
    int price;
    int zhifuMethod = 403;
    private ImageView quarterGou;
    private ImageView yearGou;
    private ImageView foreverGou;
    private ImageView wxGou;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_user, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        service.setOnClickListener(this);
        active.setOnClickListener(this);
        contact.setOnClickListener(this);
        protocol.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    private void initView(View view) {
        service = (RelativeLayout) view.findViewById(R.id.user_rl_service);
        active = (RelativeLayout) view.findViewById(R.id.user_rl_active);
        contact = (RelativeLayout) view.findViewById(R.id.user_rl_contact);
        protocol = (RelativeLayout) view.findViewById(R.id.user_rl_protocol);
        update = (RelativeLayout) view.findViewById(R.id.user_rl_update);
    }
    public void clickTypeEvent(final ImageView imageView , final int value){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price = value;
                quarterGou.setBackgroundResource(R.drawable.pay_3rd_select);
                yearGou.setBackgroundResource(R.drawable.pay_3rd_select);
                foreverGou.setBackgroundResource(R.drawable.pay_3rd_select);
                imageView.setBackgroundResource(R.drawable.pay_3rd_select_p);
            }
        });
    }
    public void clickZfEvent(final ImageView imageView , final int value){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifuMethod = value;
                wxGou.setBackgroundResource(R.drawable.pay_3rd_select);
                imageView.setBackgroundResource(R.drawable.pay_3rd_select_p);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.user_rl_service:
                PayDialog payDialog = new PayDialog(getActivity(),"1");
                payDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                payDialog.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                WindowManager.LayoutParams lp = payDialog.getWindow().getAttributes();
                lp.width = displayMetrics.widthPixels; // 设置宽度
                payDialog.getWindow().setAttributes(lp);
                break;
            case R.id.user_rl_active:
                intent = new Intent(getActivity(),ActiveActivity.class);
                break;
            case R.id.user_rl_contact:
//                UserUtil.SharedPerferencesCreat(getActivity(), "IS_VIP", "is_vip", true);
                View contactView = View.inflate(getActivity(), R.layout.contact_dialog, null);
                TextView cancel = (TextView) contactView.findViewById(R.id.contact_tv_cancel);
                TextView server = (TextView) contactView.findViewById(R.id.contact_tv_server);
               final AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(contactView).show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                server.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //跳转QQ聊天界面
                        String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=910066429&version=1";
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                    }
                });
                break;
            case R.id.user_rl_protocol:
                intent = new Intent(getActivity(),ProtocolActivity.class);
                break;
            case R.id.user_rl_update:
                Toast.makeText(getActivity(),"当前已是最新版本",Toast.LENGTH_SHORT).show();
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }
}
