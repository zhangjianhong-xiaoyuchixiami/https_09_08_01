package api.test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jonhn on 2017/8/23.
 */
public class Test {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = sdf.format(new Date(1503476833195L));
        System.out.println(date);
        System.out.println(System.currentTimeMillis());
    }

}
