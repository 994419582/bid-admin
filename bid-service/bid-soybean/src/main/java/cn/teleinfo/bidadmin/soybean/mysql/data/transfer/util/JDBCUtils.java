package cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {
    
//    private static final String connectionURL = "jdbc:mysql://39.99.132.122:3306/bidadmin?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
    private static final String connectionURL = "jdbc:mysql://39.99.132.122:3306/bidadmin-test?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
    private static final String username = "root";
    private static final String password = "bif123*()";
    
    //创建数据库的连接
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return   DriverManager.getConnection(connectionURL,username,password);
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return null;
    }
    
    //关闭数据库的连接
    public static void close(ResultSet rs,Statement stmt,Connection con) throws SQLException {
        if(rs!=null)
            rs.close();
        if(stmt!=null)
            stmt.close();
        if(con!=null)
            con.close();
    }
}