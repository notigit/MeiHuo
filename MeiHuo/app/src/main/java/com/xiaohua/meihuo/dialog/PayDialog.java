package com.xiaohua.meihuo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.utils.WFTPayActivity;

/**
 * Created by Administrator on 2016/5/10.
 */
public class PayDialog extends Dialog implements View.OnClickListener {

    private Button sure;
    private ImageView quarterGou, yearGou, foreverGou;
    private RelativeLayout quarterRL, yearRL, foreverRL;
    private String price = "8800", goodsName = "永久会员";//价格
    private Context context;
    private String url;

    public PayDialog(Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
    }

    public PayDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_dialog);
        sure = (Button) findViewById(R.id.service_btn_sure);
        sure.setOnClickListener(this);
        quarterGou = (ImageView) findViewById(R.id.service_iv_quarter);
        quarterRL = (RelativeLayout) findViewById(R.id.service_rl_quarter);
        quarterRL.setOnClickListener(this);
        yearGou = (ImageView) findViewById(R.id.service_iv_year);
        yearRL = (RelativeLayout) findViewById(R.id.service_rl_year);
        yearRL.setOnClickListener(this);
        foreverGou = (ImageView) findViewById(R.id.service_iv_forever);
        foreverRL = (RelativeLayout) findViewById(R.id.service_rl_forever);
        foreverRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service_btn_sure:
                Intent intent = new Intent(context, WFTPayActivity.class);
                intent.putExtra("MONEY", price);
                intent.putExtra("GOODS_NAME", goodsName);
                if (url != "1"){
                    intent.putExtra("URL",url);
                }
                context.startActivity(intent);
                break;
            case R.id.service_rl_quarter:
                clickTypeEvent(quarterGou, "3800", "季度会员");
                break;
            case R.id.service_rl_year:
                clickTypeEvent(yearGou, "5800", "年费会员");
                break;
            case R.id.service_rl_forever:
                clickTypeEvent(foreverGou, "8800", "永久会员");
                break;
        }
    }

    public void clickTypeEvent(final ImageView imageView, final String value, String goodVal) {
        goodsName = goodVal;
        price = value;
        quarterGou.setBackgroundResource(R.drawable.pay_3rd_select);
        yearGou.setBackgroundResource(R.drawable.pay_3rd_select);
        foreverGou.setBackgroundResource(R.drawable.pay_3rd_select);
        imageView.setBackgroundResource(R.drawable.pay_3rd_select_p);
    }

}
