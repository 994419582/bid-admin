package cn.teleinfo.bidadmin.soybean.utils;

import cn.teleinfo.bidadmin.soybean.entity.Group;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HttpClient {
    public static InputStream doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                try {
                    List<Group> metaGroups = ExcelUtils.importExcel(is, 0,1, false, Group.class);
                    metaGroups.forEach(x ->{
                        System.out.println("的值是：---"+ x.toString() + "，当前方法=HttpClient.main()");
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();// 关闭远程连接
        }

        return is;
    }

    public static InputStream doPost(String httpUrl, String param) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(param.getBytes());
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                try {
                    List<Group> metaGroups = ExcelUtils.importExcel(is, 0,1, false, Group.class);
                    metaGroups.forEach(x ->{
                        System.out.println("的值是：---"+ x.toString() + "，当前方法=HttpClient.main()");
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return is;
    }
//
//    public static void main(String[] args) {
//        InputStream is =HttpClient.doGet("http://39.99.132.122:8080/ipfs/Qma2uV833wTSSEZaQyBnW7NtoaBTWQezoQH4PuP2wGYDJv");
////        try {
////            List<Group> metaGroups = ExcelUtils.importExcel(is, 0,1, false, Group.class);
////            metaGroups.forEach(x ->{
////                System.out.println("的值是：---"+ x.toString() + "，当前方法=HttpClient.main()");
////            });
////        }catch (Exception e){
////            e.printStackTrace();
////        }
//    }
}