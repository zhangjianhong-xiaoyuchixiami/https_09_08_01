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
public class MainMobile {

    public static class Req {
        public String authId;
        public long ts;
        public String reqId;
        public String sign;

        public String mobile;
        public String realName;
        public String idNo;
    }
//      static String authId = "填入账号";
//      static String authPass = "填入密码";
    static String authId = "qydata_test";
    static String authPass = "f26d89cf8715403e81cfa72170083723";
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPost request = new HttpPost("https://qidata.net.cn:9000/mobile/query/status");
//        HttpPost request = new HttpPost("https://dataapi.linkcircle.cn:9000/mobile/verify/3f");
        HttpPost request = new HttpPost("https://api.qydata.org:9000/mobile/verify/3f");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

        Req req = new Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.mobile = "13800001111";
        req.idNo = "110224199901018290";
        req.realName = "张三";
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();

        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        System.out.println(execute.getStatusLine().getStatusCode());
        System.out.println(EntityUtils.toString(execute.getEntity()));
    }


}
