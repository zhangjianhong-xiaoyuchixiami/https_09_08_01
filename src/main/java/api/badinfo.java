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
 * Created by jonhn on 2017/8/16.
 */
public class badinfo {

    public static class Req {
        public String authId;
        public long ts;
        public String reqId;
        public String sign;


        public String realName;
        public String idNo;
        public boolean omitLocal;
        public int aid;

    }

    static String authId = "qydata03";
    static String authPass = "a54cc70444ea4618ad8d586194ba1572";
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.qydata.org:9000/id/query/bad");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        Req req = new Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.realName = "孙康美";
        req.idNo = "341122196811041229";
        req.omitLocal = true;
        req.aid = 36;
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();
        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        System.out.println("httpCode="+execute.getStatusLine().getStatusCode());
        System.out.println("测试结果="+ EntityUtils.toString(execute.getEntity()));

    }

}
