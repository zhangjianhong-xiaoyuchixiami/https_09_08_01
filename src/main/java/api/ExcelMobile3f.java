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
 * Created by jonhn on 2017/5/23.
 */
public class ExcelMobile3f {


    public static void main(String[] args) throws Exception {
        readTable();
    }

    public static class Req {
        public String authId;
        public long ts;
        public String reqId;
        public String sign;
        public boolean omitLocal;
        public int aid;

        public String mobile;
        public String realName;
        public String idNo;
    }

    static String authId = "qydata03";
    static String authPass = "a54cc70444ea4618ad8d586194ba1572";

    public static JSONObject mobile3f(String mobile,String idNo,String realName,Integer aid) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.qydata.org:9000/mobile/verify/3f");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        ExcelMobile3f.Req req = new ExcelMobile3f.Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.mobile = mobile;
        req.idNo = idNo;
        req.realName = realName;
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();
        req.omitLocal = true;
        req.aid = aid;
        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        String result = EntityUtils.toString(execute.getEntity());
        JSONObject resultJo = JSONObject.fromObject(result);
        return resultJo;
    }

    public static void readTable() throws Exception{
        InputStream ips=new FileInputStream("D:\\mobile.xls");
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

                        String [] strYiDong = {"134","135","136","137","138","139","147","150","151","152","157","158","159","178","182","183","184","187","188"};
                        String [] strLianTong = {"130","131","132","145","155","156","171","175","176","185","186"};
                        String [] strDianxing = {"133","153","173","177","180","181","189","170"};
                        String value = cell.getRichStringCellValue().toString().trim();
                        String valueParam = value.substring(0,3);

                        for (int i = 0; i < strYiDong.length ; i++) {
                            if (valueParam.contains(strYiDong[i])){
                                String [] param = value.split("-");
                                String mobile = param[0];
                                String idNo = param[1];
                                String realName = param[2];
//                                JSONObject jsonObject = mobile3f(mobile,idNo,realName,80);
//                                JSONObject jo = jsonObject.getJSONObject("result");
//                                String result = jo.getString("resultCode");
//                                if ("-1".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%移动" +"&无记录";
//                                }
//                                if ("1".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%移动" +"&全匹配";
//                                }
//                                if ("2".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%移动" +"&部分匹配";
//                                }
//                                if ("3".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%移动" +"&无匹配";
//                                }
//                                if ("4".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%移动" +"&不匹配";
//                                }
                            }
                        }
                        for (int i = 0; i < strLianTong.length ; i++) {
                            if (valueParam.contains(strLianTong[i])){
                                String [] param = value.split("-");
                                String mobile = param[0];
                                String idNo = param[1];
                                String realName = param[2];
                               // JSONObject jsonObject = mobile3f(mobile,idNo,realName,1);
                                //JSONObject jo = jsonObject.getJSONObject("result");
                                //String result = jo.getString("resultCode");
//                                if ("-1".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%联通" +"&无记录";
//                                }
//                                if ("1".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%联通" +"&全匹配";
//                                }
//                                if ("2".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%联通" +"&部分匹配";
//                                }
//                                if ("3".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%联通" +"&无匹配";
//                                }
//                                if ("4".equals(result)){
//                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%联通" +"&不匹配";
//                                }
                                fileContent = mobile +"%联通";
                            }
                        }
                        for (int i = 0; i < strDianxing.length ; i++) {
                            if (valueParam.contains(strDianxing[i])){
                                String [] param = value.split("-");
                                String mobile = param[0];
                                String idNo = param[1];
                                String realName = param[2];
                                JSONObject jsonObject = mobile3f(mobile,idNo,realName,28);
                                JSONObject jo = jsonObject.getJSONObject("result");
                                String result = jo.getString("resultCode");
                                if ("-1".equals(result)){
                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%电信" +"&无记录";
                                }
                                if ("1".equals(result)){
                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%电信" +"&全匹配";
                                }
                                if ("2".equals(result)){
                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%电信" +"&部分匹配";
                                }
                                if ("3".equals(result)){
                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%电信" +"&无匹配";
                                }
                                if ("4".equals(result)){
                                    fileContent = mobile +"-"+ idNo +"@"+ realName +"%电信" +"&不匹配";
                                }
                            }
                        }
                        break;
                }
            }
            File file = new File("E:\\result_dianxin.txt");
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
