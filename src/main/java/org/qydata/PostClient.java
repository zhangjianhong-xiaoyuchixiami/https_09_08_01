package org.qydata;

import com.alibaba.fastjson.JSON;

/**
 * Created by jonhn on 2016/11/11.
 */
public class PostClient {

    public static class Req {
        public String url;
        public Integer cid;


    }

    public static void main(String[] args) {
        Req req = new Req();
        req.url = "https://b.qianyandata.com";
        req.cid = 1;
        PostClient.getData(req);
    }

    public static String getData(Req requestData) {
        final String uri = "http://192.168.31.219/query/clicks";
        String json = JSON.toJSONString(requestData);
        System.out.println(json);

        try {
            return HttpClient.doPostSSL(uri, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
