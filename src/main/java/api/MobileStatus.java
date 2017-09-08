package api;

import com.google.gson.Gson;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by jonhn on 2017/5/23.
 */
public class MobileStatus {

    public static class Req {
        public String authId;
        public long ts;
        public String reqId;
        public String sign;


        public String mobile;

    }

    static String authId = "sztrjr_test";
    static String authPass = "b61d31c791804e6eb0b0f53387712307";
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.qydata.org:9000/mobile/query/status");
        request.setHeader(HttpHeaders.REFERER,"referer");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        MobileStatus.Req req = new MobileStatus.Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.mobile = "13100001231";
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();

        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        System.out.println("httpCode="+execute.getStatusLine().getStatusCode());
        System.out.println("测试结果="+ EntityUtils.toString(execute.getEntity()));

    }

}
