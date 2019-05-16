package tree.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTrans {
    public static Date stringToDate(String str)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDateTime(String str)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateTimeToString(Date date)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }

    public static boolean isStringDateFormat(String str)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        boolean result = false;
        try {
            sdf.parse(str);
            result = true;
        } catch (ParseException e) {
        }
        return result;
    }

    public static double getTwoDigitDouble(double d){
        BigDecimal bg = new BigDecimal(d);
        double d2 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d2;
    }
}
