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
 * Created by jonhn on 2017/08/04.
 */
public class bankcard4f {

    public static class Req {
        public String authId;
        public long ts;
        public String reqId;
        public String sign;

        public String bankcard;
        public String mobile;
        public String realName;
        public String idNo;
        public boolean omitLocal;
        public int aid;

    }

    static String authId = "qydata03_test";
    static String authPass = "1c4ced7a3e5b4b8fabaff268306729a0";
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.qydata.org:9000/bankcard/verify/3f");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        bankcard4f.Req req = new bankcard4f.Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.bankcard = "6236680680000160000";
        req.mobile = "13111111111";
        req.realName = "张三";
        req.idNo = "32132219941203062X";
//        req.omitLocal = true;
//        req.aid = 19;
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();
        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        System.out.println("httpCode="+execute.getStatusLine().getStatusCode());
        System.out.println("测试结果="+ EntityUtils.toString(execute.getEntity()));

    }

}
