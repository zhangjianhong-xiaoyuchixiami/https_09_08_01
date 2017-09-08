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
 * 请自行准备运行所依赖的jar
 *
 * Created by jonhn on 2017/5/23.
 */
public class IdCard {

    public static class Req {
        public String authId;
        public long ts;
        public String reqId;
        public String sign;
        public String idNo;
        public String realName;
        public String mobile;

        public boolean omitLocal;
        public int aid;

    }

    static String authId = "qydata03";
    static String authPass = "a54cc70444ea4618ad8d586194ba1572";
    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.qydata.org:9000/id/verify/2f");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        IdCard.Req req = new IdCard.Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.idNo = "310230198107261058";
        req.realName = "顾乐";
        //req.mobile = "15910331887";
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();
        req.omitLocal = true;
        req.aid = 47;
        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        System.out.println("httpCode="+execute.getStatusLine().getStatusCode());
        System.out.println("测试结果="+ EntityUtils.toString(execute.getEntity()));
    }

}
