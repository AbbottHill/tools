package com.cd.tools.http;

import com.alibaba.fastjson.JSON;
import com.cd.tools.file.FileUitl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author ChuD
 * @date 2019-06-20 10:18
 */
public class ApiTest {

    @Test
    public void commitReceiveInfoTest() throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
//            HttpGet httpget = new HttpGet("http://httpbin.org/");
//            HttpPost httpPost = new HttpPost("http://61.132.133.86:9080/service/wsbz/mail/commitReceiveInfo");
            HttpPost httpPost = new HttpPost("http://localhost:8580/wsbz/mail/commitReceiveInfo");

            System.out.println("Executing request " + httpPost.getRequestLine());
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("requestid", "2119050721421781");
            httpPost.setHeader("timestamp", "2019050721421781");
            httpPost.setHeader("no_check_timestamp", "1");
            httpPost.setHeader("signature", "9e358be0658607c10b5db8e423f4d2026c2c51757b88f7bd6b0cc4c1fbdc95ab");

//            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            nvps.add(new BasicNameValuePair("receive_address", "安徽省x市收件地址"));
//            nvps.add(new BasicNameValuePair("password", "secret"));
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HashMap<Object, Object> params = new HashMap<>();
            params.put("receive_address", "安徽省x市收件地址");
            params.put("receive_location", "实际收件地址");
            params.put("receive_time", "20190528110206");
            params.put("receiver_xm", "赵四");
            params.put("receiver_xb", "1");
            params.put("receiver_mz", "01");
            params.put("receiver_gmsfzhm", "340111199301018811");
            params.put("receiver_csrq", "19930101");
            params.put("receiver_yxqqsrq", "20210206");
            params.put("receiver_yxqjzrq", "20290529");
            params.put("receiver_zz", "安徽省安庆市潜山市");
            params.put("fingerprint_xsd_1", "0.7");
            params.put("postman_id", "pst001");
            params.put("postman_name", "速递员1");
            params.put("ems_id", "12340001");
            params.put("card_slh", "3401010012019061810001");
            params.put("receiver_photo", FileUitl.base64Str("C:\\Users\\Administrator\\Desktop\\0142756206.jpg"));

            httpPost.setEntity(new StringEntity(JSON.toJSONString(params), "UTF8"));

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            httpclient.close();
        }
    }
}
