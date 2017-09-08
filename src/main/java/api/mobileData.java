package api;

import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.util.Iterator;

/**
 * Created by jonhn on 2017/8/9.
 */
public class mobileData {

    public static void main(String[] args) throws Exception {
        readTable();
       // mobileData("13488755628",70);
    }

    public static class Req {
        public String authId;
        public long ts;
        public String reqId;
        public String sign;
        public boolean omitLocal;
        public int aid;

        public String mobile;
    }

    static String authId = "qydata03";
    static String authPass = "a54cc70444ea4618ad8d586194ba1572";

    public static JSONObject mobileData(String mobile,Integer aid) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.qydata.org:9000/mobile/query/duration");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        mobileData.Req req = new mobileData.Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.mobile = mobile;
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();
        req.omitLocal = true;
        //req.aid = aid;
        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        String result = EntityUtils.toString(execute.getEntity());
        JSONObject resultJo = JSONObject.fromObject(result);
        return resultJo;
    }

    public static void readTable() throws Exception{
        InputStream ips=new FileInputStream("D:\\mobile\\mobile.xls");
        HSSFWorkbook wb=new HSSFWorkbook(ips);
        HSSFSheet sheet=wb.getSheetAt(0);

        for(Iterator ite = sheet.rowIterator(); ite.hasNext();){
            HSSFRow row=(HSSFRow)ite.next();
            String fileContent = "";
            for(Iterator itet=row.cellIterator();itet.hasNext();){
                HSSFCell cell=(HSSFCell)itet.next();
                switch(cell.getCellType()){
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        //得到Boolean对象的方法
                        System.out.print(cell.getBooleanCellValue()+" ");
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        //先看是否是日期格式
                        if(HSSFDateUtil.isCellDateFormatted(cell)){
                            //读取日期格式
                            System.out.print(cell.getDateCellValue()+" ");
                        }else{
                            //读取数字
                            System.out.print(cell.getNumericCellValue()+" ");
                        }
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        //读取公式
                        System.out.print(cell.getCellFormula()+" ");
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        String value = cell.getRichStringCellValue().toString().trim();
                        JSONObject jsonObject = mobileData(value,null);
                        fileContent = jsonObject.toString();
                        //JSONObject jo = jsonObject.getJSONObject("result");
                        //String result = jo.getString("resultCode");
                        break;
                }
            }
            File file = new File("D:\\mobile\\result_0814.txt");
            if(!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileContent + "\r\n");
            bw.close();
        }

    }

}
