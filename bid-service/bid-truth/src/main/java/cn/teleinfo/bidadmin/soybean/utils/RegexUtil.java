package cn.teleinfo.bidadmin.soybean.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    private static String regStr = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";

    public static boolean dateFormat(String date) {
        if (date == null || "".equals(date)) {
            return true;
        }
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(date);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
