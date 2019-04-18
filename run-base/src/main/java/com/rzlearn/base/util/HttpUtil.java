package com.rzlearn.base.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;

/**
 * <p>ClassName:HttpUtil</p>
 * <p>Description:</p>
 * @author JiPeigong
 * @date 2018-08-11 16:58:13
 **/
@Slf4j
public final class HttpUtil {

    private static final MediaType CONTENT_TYPE_FORM = MediaType
            .parse("application/x-www-form-urlencoded;charset=UTF-8");
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";

    private HttpUtil() {
    }

    public static void main(String[] args) {
        System.out.println(get(
                "http://restapi.amap.com/v3/place/text?key=a7c65026724bee6e0c826ddef9155e69&keywords=%E6%98%8C%E5%B9%B3%E5%8C%BA&city=%E5%8C%97%E4%BA%AC%E5%B8%82%E5%8C%97%E4%BA%AC%E5%B8%82&children=1&offset=1&page=1&extensions=base"));
    }

    public static final String get(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", DEFAULT_USER_AGENT);
        // 响应内容
        String responseContent = null;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            // 正确返回
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseContent = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public static final String post(String url, ArrayList<NameValuePair> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // 响应内容
        String responseContent = null;
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public static String post(String url, String params) {
        RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, params);
        Request request = new Request.Builder().url(url).post(body).build();
        return exec(request);
    }

    private static String exec(okhttp3.Request request) {
        try {
            okhttp3.Response response = new OkHttpClient().newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String postSSL(String url, String data, String certPath, String certPass) {
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            // 读取本机存放的PKCS12证书文件
            FileInputStream instream = new FileInputStream(certPath);
            try {
                // 指定PKCS12的密码(商户ID)
                clientStore.load(instream, certPass.toCharArray());
            } finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(clientStore, certPass.toCharArray()).build();
            // 指定TLS版本
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            // 设置httpclient的SSLSocketFactory
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            try {
                HttpPost httpost = new HttpPost(url); // 设置响应头信息
                httpost.addHeader("Connection", "keep-alive");
                httpost.addHeader("Accept", "*/*");
                httpost.addHeader("Content-Type", CONTENT_TYPE_FORM.toString());
                httpost.addHeader("X-Requested-With", "XMLHttpRequest");
                httpost.addHeader("Cache-Control", "max-age=0");
                httpost.addHeader("User-Agent", DEFAULT_USER_AGENT);
                httpost.setEntity(new StringEntity(data, "UTF-8"));
                CloseableHttpResponse response = httpclient.execute(httpost);
                try {
                    HttpEntity entity = response.getEntity();
                    String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                    EntityUtils.consume(entity);
                    return jsonStr;
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
