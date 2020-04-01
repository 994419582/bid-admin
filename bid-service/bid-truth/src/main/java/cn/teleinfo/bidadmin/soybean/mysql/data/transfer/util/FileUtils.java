package cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util;

import java.io.*;

public class FileUtils {
    public static String ReadFile(String filePath) {
        StringBuilder result = new StringBuilder();

        File file = new File(filePath);

        InputStreamReader reader= null;
        try {
            reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bfreader=new BufferedReader(reader);
        String line = null;
        while(true) {
            try {
                if (!((line=bfreader.readLine())!=null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }//包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null
            result.append(line);
        }
        return result.toString();
    }
}
