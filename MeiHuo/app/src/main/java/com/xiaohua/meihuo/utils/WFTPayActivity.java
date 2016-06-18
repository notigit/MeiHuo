package com.xiaohua.meihuo.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xiaohua.meihuo.R;
import com.xiaohua.meihuo.activity.PlayActivity;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.switfpass.pay.service.GetPrepayIdResult;
import com.switfpass.pay.utils.MD5;
import com.switfpass.pay.utils.SignUtils;
import com.switfpass.pay.utils.Util;
import com.switfpass.pay.utils.XmlUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/11.
 */
public class WFTPayActivity extends Activity {
    private String goodsName, url;
    private Context context;

//    public WFTPayActivity(Context context,String money,String goodsName) {
////        this.activityResult = activityResult;
//        this.goodsName = goodsName;
//        new WFTPay(context,money).execute();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String money = getIntent().getStringExtra("MONEY");
        goodsName = getIntent().getStringExtra("GOODS_NAME");
        url = getIntent().getStringExtra("URL");
        context = this;
        new WFTPay(context, money).execute();
    }


    public class WFTPay extends AsyncTask<Void, Void, Map<String, String>> {
        String TAG = "PayMainActivity";
        private ProgressDialog dialog;
        private String accessToken;
        private Context context;
        private String payOrderNo, money, credit_pay = "0";

        public WFTPay(Context context, String accessToken, String money) {
            this.context = context;
            this.accessToken = accessToken;
            this.money = money;
        }

        public WFTPay(Context context, String money) {
            this.context = context;
            this.money = money;
        }

        @Override
        protected void onPreExecute() {
            dialog =
                    ProgressDialog.show(context,
                            context.getString(R.string.app_tip),
                            context.getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            if (result == null) {
                Toast.makeText(context, context.getString(R.string.get_prepayid_fail), Toast.LENGTH_LONG).show();
            } else {
                if (result.get("status").equalsIgnoreCase("0")) // 成功
                {
//                    Toast.makeText(context, R.string.get_prepayid_succ, Toast.LENGTH_LONG).show();
                    RequestMsg msg = new RequestMsg();
                    msg.setMoney(Double.parseDouble(money));
                    msg.setTokenId(result.get("token_id"));
                    msg.setOutTradeNo(payOrderNo);
                    // 微信wap支付
                    msg.setTradeType(MainApplication.PAY_WX_WAP);
                    PayPlugin.unifiedH5Pay((Activity) context, msg);
                } else {
//                    Toast.makeText(context, context.getString(R.string.get_prepayid_fail), Toast.LENGTH_LONG)
//                            .show();
                    Toast.makeText(context,result.toString(), Toast.LENGTH_LONG)
                            .show();

                }

            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            // 统一预下单接口
            //            String url = String.format("https://api.weixin.qq.com/pay/genprepay?access_token=%s", accessToken);
            String url = "https://paya.swiftpass.cn/pay/gateway";
            //            String entity = getParams();
            String entity = getParams();
            Log.d(TAG, "doInBackground, url = " + url);
            Log.d(TAG, "doInBackground, entity = " + entity);
            GetPrepayIdResult result = new GetPrepayIdResult();
            byte[] buf = Util.httpPost(url, entity);
            if (buf == null || buf.length == 0) {
                return null;
            }
            String content = new String(buf);
            Log.d(TAG, "doInBackground, content = " + content);
            result.parseFrom(content);
            try {
                return XmlUtils.parse(content);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 组装参数
         * <功能详细描述>
         *
         * @return
         * @see [类、类#方法、类#成员]
         */
        private String getParams() {

            Map<String, String> params = new HashMap<String, String>();
            params.put("body", goodsName); // 商品名称
            params.put("service", "unified.trade.pay"); // 支付类型
            params.put("version", "1.0"); // 版本
            params.put("mch_id", "100580000025"); // 威富通商户号
            //        params.put("mch_id", mchId.getText().toString()); // 威富通商户号
            params.put("notify_url", "http://zhifu.dev.swiftpass.cn/spay/notify"); // 后台通知url
            params.put("nonce_str", genNonceStr()); // 随机数
            payOrderNo = genOutTradNo();
            params.put("out_trade_no", payOrderNo); //订单号
            params.put("mch_create_ip", "127.0.0.1"); // 机器ip地址
            params.put("total_fee", money); // 总金额

            params.put("limit_credit_pay", credit_pay); // 是否限制信用卡支付， 0：不限制（默认），1：限制
            String sign = createSign("c2eb7ac4c184515af7dd20f38b0aea0c", params); // 9d101c97133837e13dde2d32a5054abb 威富通密钥

            params.put("sign", sign); // sign签名

            return XmlUtils.toXml(params);
        }

        private String genOutTradNo() {
            String str = ChannelUtil.getChannel(context,"defaultChannel");
            String s = System.currentTimeMillis()+"";
            return str+"_"+s;
        }

        private String genNonceStr() {
            Random random = new Random();
            return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
        }

        public String createSign(String signKey, Map<String, String> params) {
            StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
            SignUtils.buildPayParams(buf, params, false);
            buf.append("&key=").append(signKey);
            String preStr = buf.toString();
            String sign = "";
            // 获得签名验证结果
            try {
                sign = MD5.md5s(preStr).toUpperCase();
            } catch (Exception e) {
                sign = MD5.md5s(preStr).toUpperCase();
            }
            return sign;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String respCode = data.getExtras().getString("resultCode");
        if (!TextUtils.isEmpty(respCode) && respCode.equalsIgnoreCase("success")) {
            //标示支付成功
//            Toast.makeText(WFTPayActivity.this, "支付成功", Toast.LENGTH_LONG).show();
            if (url != null) {
                Intent intent = new Intent(context, PlayActivity.class);
                intent.putExtra("VIDEO_URL", url);
                context.startActivity(intent);
            }
            UserUtil.SharedPerferencesCreat(context, "IS_VIP", "is_vip", true);//设置VIP
        } else { //其他状态NOPAY状态：取消支付，未支付等状态
            //Toast.makeText(WFTPayActivity.this, "未支付", Toast.LENGTH_LONG).show();
        }
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
