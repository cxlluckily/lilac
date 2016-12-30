package com.jzl.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 发送http和https
 * 
 * @author zhangz
 * @date 2016-7-20
 * 
 */
@SuppressWarnings("deprecation")
public class HttpsUtils {

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static String httpsPost(String url, String content, String charset) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        byte[] bb = HttpsUtils.post(url, content, charset);
        String str = new String(bb, "UTF-8");
        return str;
    }

    /**
     * post方式请求服务器(https协议)
     * 
     * @param url
     *            请求地址
     * @param content
     *            参数
     * @param charset
     *            编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static byte[] post(String url, String content, String charset) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes(charset));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            return outStream.toByteArray();
        }
        return null;
    }

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param token
     *            环信token
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String token) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            // connection.setRequestProperty("accept", "*/*");
            // connection.setRequestProperty("connection", "Keep-Alive");
            // connection.setRequestProperty("user-agent",
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 6.1;SV1)");
            // 设置环信token
            if (token != null && !"".equals(token)) {
                conn.setRequestProperty("Authorization", "Bearer " + token);
            }
            conn.setConnectTimeout(1000 * 5);
            conn.setReadTimeout(1000 * 5);
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，json格式
     * @param token
     *            环信token
     * @return 所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendPost(String url, String param, String token) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            // conn.setRequestProperty("accept", "*/*");
            // conn.setRequestProperty("connection", "Keep-Alive");
            // conn.setRequestProperty("user-agent",
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 设置参数格式为json
            conn.setRequestProperty("Content-Type", "application/json");
            // 设置环信token
            if (token != null && !"".equals(token)) {
                conn.setRequestProperty("Authorization", "Bearer " + token);
            }
            conn.setConnectTimeout(1000 * 5);
            conn.setReadTimeout(1000 * 5);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPostHttps(String url, String param, String token, String charset) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(param.getBytes(charset));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            return new String(outStream.toByteArray(), "UTF-8");
        }
        return null;
    }

    /**
     * 环信put请求
     * 
     * @auther zhangz
     * @date 2016-7-19 下午2:48:21
     * @param path
     *            请求路径
     * @param param
     *            参数，json格式
     * @param token
     *            环信token
     * @param charset
     *            字符集
     * @return
     * @throws IOException
     */
    public static String sendPut(String path, String param, String token, String charset) throws IOException {
        URL url = null;
        String outString = "";
        try {
            url = new URL(path);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
        HttpURLConnection conn = null;
        DataOutputStream dataOutputStream = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            // 设置环信token
            if (token != null && !"".equals(token)) {
                conn.setRequestProperty("Authorization", "Bearer " + token);
            }
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000 * 5);
            conn.setReadTimeout(1000 * 5);
            dataOutputStream = new DataOutputStream(conn.getOutputStream());

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(param.getBytes(charset));
            out.flush();
            out.close();
            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                outString = new String(outStream.toByteArray(), "UTF-8");
            }
        } finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.flush();
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return outString;
    }

    /**
     * 环信发送delete请求
     * 
     * @auther zhangz
     * @date 2016-7-19 下午4:12:34
     * @param path
     *            请求路径
     * @param param
     *            参数，json格式
     * @param token
     *            环信token
     * @param charset
     *            字符集
     * @return
     * @throws IOException
     */
    public static void sendDelete(String path, String param, String token, String charset) throws IOException {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000 * 5);
            conn.setReadTimeout(1000 * 5);
            conn.setRequestProperty("Content-Type", "application/json");
            // 设置环信token
            if (token != null && !"".equals(token)) {
                conn.setRequestProperty("Authorization", "Bearer " + token);
            }
            conn.setRequestMethod("DELETE");
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(param.getBytes(charset));
            out.flush();
            out.close();
            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String clientCustomSSLPost(String url, String content, String charset) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            IOException, KeyManagementException, UnrecoverableKeyException {
        String result = "";
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("D:/apiclient_cert.p12"));
        try {
            keyStore.load(instream, "1353244302".toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1353244302".toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        
        HttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {

            // HttpGet httpget = new
            // HttpGet("https://api.mch.weixin.qq.com/secapi/pay/refund");
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
//            }
            nvps.add(new BasicNameValuePair(content, null));
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
            client.execute(httpPost);
            // httpclient.getConnectionManager().shutdown();

            System.out.println("executing request" + httpPost.getRequestLine());

            HttpResponse response = client.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
                    while ((result = bufferedReader.readLine()) != null) {
                         System.out.println(result);
                    }

                }
                EntityUtils.consume(entity);
            } finally {
//                response.close();
            }
        } finally {
//            client.close();
        }
        return result;
    }

}
