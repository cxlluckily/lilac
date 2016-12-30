package com.jzl.util.alipay;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.appcore.conf.AbstractPropertiesConfig;
import com.jzl.conf.Config;
import com.jzl.util.alipay.sign.RSA;

public class AlipayUtils extends AbstractPropertiesConfig {

    private static final long serialVersionUID = 1L;

    private static Properties p = getProperties("alipay.properties");

    public static final String PARTNER = p.getProperty("partner");
    public static final String PRIVATE_KEY = p.getProperty("private_key");
    public static final String LOG_PATH = p.getProperty("log_path");
    /** 支付回调地址 */
    private static final String NOTIFY_URL = Config.BASE_URL + "/api/alipay/callback";

    /**
     * 获取支付宝请求参数
     * 
     * @auther zhangz
     * @date 2016-8-9 下午4:11:40
     * @param out_trade_no
     *            订单号
     * @param total_fee
     *            总金额(单位:分)
     * @param subject
     *            商品名称
     * @param body
     *            商品详情
     * @return 支付宝参数字符串
     */
    public static String getParam(String out_trade_no, int total_fee, String subject, String body) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("service", "mobile.securitypay.pay");
        params.put("partner", PARTNER);
        params.put("_input_charset", "utf-8");
        params.put("notify_url", NOTIFY_URL);
        // 客户端号 可空
        // params.put("app_id", "");
        // 客户端来源 可空 标识客户端来源。参数值内容约定如下：appenv=”system=客户端平台名^version=业务系统版本”
        // params.put("appenv", "");
        // 商户网站唯一订单号 非空 支付宝合作商户网站唯一订单号。
        params.put("out_trade_no", out_trade_no);
        params.put("subject", subject);
        // 支付类型 非空 支付类型。默认值为：1（商品购买）。
        params.put("payment_type", "1");
        // 卖家支付宝账号 非空 卖家支付宝账号（邮箱或手机号码格式）或其对应的支付宝唯一用户号（以2088开头的纯16位数字）。
        params.put("seller_id", PARTNER);
        // 总金额 非空 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
        params.put("total_fee", total_fee / 100d + "");
        params.put("body", body);
        // 商品类型 可空 具体区分本地交易的商品类型。1：实物交易；0：虚拟交易。默认为1（实物交易）。
        // params.put("goods_type", "0");
        // 花呗分期参数 可空
        // params.put("hb_fq_param", "");
        // 是否发起实名校验 可空 T：发起实名校验；F：不发起实名校验。
        // params.put("rn_check", "F");
        // 未付款交易的超时时间 可空
        // params.put("it_b_pay", "");
        // 授权令牌 可空
        // params.put("extern_token", "");
        // 商户优惠活动参数 可空 商户与支付宝约定的营销参数，为Key:Value键值对，如需使用，请联系支付宝技术人员。
        // params.put("promo_params", "");

        String param = AlipayCore.createLinkStringQuotes(params);
        String sign = getSign(param);
        param += "&sign=\"" + sign + "\"&sign_type=\"RSA\"";
        return param;
    }

    /**
     * 获取签名
     * 
     * @auther zhangz
     * @date 2016-8-9 下午3:44:54
     * @param param
     * @return
     */
    private static String getSign(String param) {
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOzMrIpuwWp/pW4jLK4xR9JNmFEMYUibFWrUJcNyCmBGlE11kRrcW49nobcNp0wb+p2owVmTReA5DBENHz+0deJgozOl51ZXuhWGwOBasJ1dWo+tV2+NESWfBMp/apNJeKroM8Dshq+XzNfOrw3PY/Iy+DLgZ0UtawmE4Kj6Ca+JAgMBAAECgYEA3vUPeAA0aJvrHTpNNW39G8wnoBgx+PTcQ4ugStezYVbTB+OsTkgwhWOj5gEbBhRJ8ewUsMxzvFy6OcvLImY9nqpr9s/LGt4MdKjRXr6YxJOn6lQO28GeDj3u3/knlD0eqbn2KUaKoVZuKCHxpyN0dfXTUAMWg+cwKrBVOerkqCkCQQD8F3OVAvzyKfl1NvG6jEzxkLWyVAcdgKgt0cDyYhwQkz3InjdhIkUzQ8EZ0PtlgSg2IgvlJxZGMJ6TLYejQcAzAkEA8HiHQS7qUIPkljS6U73HNpu8Cye320x8Khge8yayGQfIRrC2502U5Vz6MjnDGjUga6sF48RQu6ZgOMiz1EslUwJBAO0zm9wgfaXXfRSf6IBht5ytT2pGypMmFhlW/riTPFkUUtRMm0tYlciQ/keubn6qMw4MpyityLWu1ecitjgVjCUCQCyJhjNyZv9mji2rsl7UJVNd4KQIRSserVh2gzTxk8bDBDrje9y3J76bS9OFkzcFY+3NDRg0QeefVu7tbTECgfUCQDGM/PYmNVZLLRU8S/sq+yUbxf+tOKd9BuW+tgKSY01H4W9nONkViCNF1DUGoGxXfjD1UGBAGjXTVDPY3unDlyk=";

        String sign = RSA.sign(param, privateKey, "utf-8");
        return sign;
    }

}
