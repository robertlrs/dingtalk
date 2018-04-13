package cn.idongjia.dingtalk.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpsUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpsUtils.class);//这个class待定

    public static String postJson(String url, String json) {
        try {
            return post(url, json, "application/json");
        } catch (UnsupportedEncodingException | ClientProtocolException e) {
            logger.warn("post json:[{}]", e);
        } catch (IOException e) {
            logger.warn("post json err:[{}]", e);
        } catch (Exception e) {
            logger.warn("post json err:[{}] ", e);
        }

        return null;
    }

    private static String post(String url, String msg, String contentType) throws Exception {
        HttpClient httpClient = null;
        try {
            httpClient = new SSLClient();
            HttpPost httpPost = new HttpPost(url);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            httpPost.setConfig(requestConfig);

            StringEntity entity = new StringEntity(msg, "UTF-8");
            httpPost.addHeader("Content-Type", contentType);
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            return EntityUtils.toString(resEntity, "UTF-8");
        }finally {
            if (null != httpClient) {
                ClientConnectionManager client = httpClient.getConnectionManager();
                if (null != client) {
                    client.shutdown();
                }
            }
        }
    }

    public static void main(String[] args) {
        String url = "https://oapi.dingtalk.com/robot/send?access_token=e3d3b0daedde7add73ebe328ca7f4401a4f30d8da61b13a06ce727f1a91e8d41";
        String msg = "test";
        String result = HttpsUtils.postJson(url, msg);
    }
}
