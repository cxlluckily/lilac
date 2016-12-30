package com.jzl.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;

import com.appcore.conf.AbstractPropertiesConfig;

public class ShortMessageUtils extends AbstractPropertiesConfig {

    private static final long serialVersionUID = 1L;

    private static Properties p = getProperties("shortMessage.properties");

    /** 项目名 */
    private static final String URL = p.getProperty("url");
    private static final String ACCOUNT = p.getProperty("account");
    private static final String PSWD = p.getProperty("pswd");
    private static final String MSG = p.getProperty("msg");
    private static final String NEEDSTATUS = p.getProperty("needstatus");
    private static final String EXTNO = p.getProperty("extno");

    /**
     * 发送短信验证码
     * 
     * @auther zhangz
     * @date 2016-5-12 下午1:04:36
     * @param mobile
     *            手机号码，多个号码使用","分割
     * @return
     */
    public static String sendAuthCode(String mobile, Integer smsVerificationCode) {
        String msg = MSG.replaceAll("smsVerificationCode", smsVerificationCode.toString());// 短信内容
        String returnString = ShortMessageUtils.sendShortMessage(mobile, msg);
        return returnString;
    }
    
    /**
     * 发送短信
     * 
     * @auther zhangz
     * @date 2016-5-12 下午1:04:36
     * @param mobile
     *            手机号码，多个号码使用","分割
     * @return
     */
    public static String sendShortMessage(String mobile, String msg) {
        String url = URL;// 应用地址
        String account = ACCOUNT;// 账号
        String pswd = PSWD;// 密码
        boolean needstatus = Boolean.parseBoolean(NEEDSTATUS);// 是否需要状态报告，需要true，不需要false
        String extno = EXTNO;// 扩展码
        String returnString = "";
        try {
            returnString = ShortMessageUtils.batchSend(url, account, pswd, mobile, msg, needstatus, null, extno);
            System.out.println(returnString);
            // TODO 处理返回值,参见HTTP协议文档
        } catch (Exception e) {
            // TODO 处理异常
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * 
     * @param url
     *            应用地址，类似于http://ip:port/msg/
     * @param account
     *            账号
     * @param pswd
     *            密码
     * @param mobile
     *            手机号码，多个号码使用","分割
     * @param msg
     *            短信内容
     * @param needstatus
     *            是否需要状态报告，需要true，不需要false
     * @return 返回值定义参见HTTP协议文档
     * @throws Exception
     */
    public static String batchSend(String url, String account, String pswd, String mobile, String msg, boolean needstatus, String product, String extno) throws Exception {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod();
        try {
            URI base = new URI(url, false);
            method.setURI(new URI(base, "HttpBatchSendSM", false));
            method.setQueryString(new NameValuePair[] { new NameValuePair("account", account), new NameValuePair("pswd", pswd), new NameValuePair("mobile", mobile),
                    new NameValuePair("needstatus", String.valueOf(needstatus)), new NameValuePair("msg", msg), new NameValuePair("product", product),
                    new NameValuePair("extno", extno), });
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }
    }
}
