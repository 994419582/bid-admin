package cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util;

import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.config.TransferConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {

    //创建数据库的连接
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return   DriverManager.getConnection(TransferConfig.connectionURL, TransferConfig.username,TransferConfig.password);
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