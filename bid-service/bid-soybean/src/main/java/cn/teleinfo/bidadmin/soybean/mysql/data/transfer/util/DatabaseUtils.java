package cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util;

import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity.ClocklnEntity;
import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity.UserEntity;

import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseUtils {
    private static String oldStr = "function getHours() { [native code] }";

//    private static String userTableName = "spf_soybean_user";
//    private static String groupTableName = "spf_soybean_user_group";
//    private static String clocklnTableName = "spf_soybean_clockln";

    private static String userTableName = "soybean_user";
    private static String groupTableName = "soybean_user_group";
    private static String clocklnTableName = "soybean_clockln";

    private static LocalDateTime now = LocalDateTime.now();

    public static void userInsert(UserEntity u) {
        Connection con = JDBCUtils.getConnection();
        PreparedStatement ps=null;
        PreparedStatement ps_g=null;
        ResultSet rs=null;
        PreparedStatement select_ps=null;
        ResultSet select_rs = null;
        int id = 0;//存放数据库返回的用户注册过后的id
        try {
            String select_sql = "select id from "+userTableName+" where wechat_id = ?";
            select_ps = con.prepareStatement(select_sql);
            select_ps.setString(1, u.get_openid());
            select_rs = select_ps.executeQuery();
            if(select_rs.next()) {
                return;
            }

            String sql = "insert into "+userTableName+" (" +
                    "`wechat_id`, `nickname`,`name`,`phone`,`id_type`,`id_number`,`create_time`,`update_time`,`status`," +
                    "`remarks`,`gender`,`country`,`province`,`city`,`avatar_url`,`home_id`,`home_address`," +
                    "`detail_address`,`is_deleted`) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, u.get_openid());// wechat_id
            ps.setString(2, u.getName());//nickname
            ps.setString(3, u.getName());
            ps.setString(4, u.getPhone());
            if ("大陆身份证".equals(u.getCertificate_type())) {
                ps.setInt(5, 1); // id_type
            } else if("护照".equals(u.getCertificate_type())) {
                ps.setInt(5, 2); // id_type
            } else if("军官证".equals(u.getCertificate_type())) {
                ps.setInt(5, 3); // id_type
            } else {
                ps.setInt(5, -1); // id_type
            }

            ps.setString(6, u.getCertificate_number());// id_number
//            ps.setString(7, u.getCreated_at());
//            ps.setString(8, u.getUpdated_at());
            ps.setString(7, now.toString());
            ps.setString(8, now.toString());
            ps.setInt(9, 0);// status
            ps.setString(10, "");// remarks
            ps.setInt(11, 0);//gender
            ps.setString(12, "");// country
            ps.setString(13, "");// province
            ps.setString(14, "");// city
            ps.setString(15, "");// avatar_url
            ps.setInt(16, -1);// home_id
            ps.setString(17, u.getHome_district());// home_address
            ps.setString(18, u.getHome_detail());//detail_address
            ps.setInt(19, 0);
            ps.executeUpdate();
            rs=ps.getGeneratedKeys();//这一句代码就是得到插入的记录的id
            if(rs.next()){
                id=rs.getInt(1);
            }

            String sql_g = "insert into "+groupTableName+" (`user_id`, `group_id`,`status`) values (?,?,?)";
            ps_g=con.prepareStatement(sql_g, Statement.RETURN_GENERATED_KEYS);

            ps_g.setInt(1, id);// user_id
            ps_g.setInt(2, 236);// group_id
            ps_g.setInt(3, 0);// status
            ps_g.executeUpdate();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{

            try {
                select_rs.close();
                select_ps.close();
                if (ps_g != null) {
                    ps_g.close();
                }
                JDBCUtils.close(rs, ps, con);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static Integer clocklnInsert(ClocklnEntity c) {
        Connection con = JDBCUtils.getConnection();
        PreparedStatement ps=null;
        PreparedStatement select_ps=null;
        ResultSet select_rs = null;
        ResultSet rs=null;
        int id = 0;//存放数据库返回的用户注册过后的id
        try {
            int userId = 0;
            String select_sql = "select id from "+userTableName+" where wechat_id = ?";
            select_ps = con.prepareStatement(select_sql);
//            select_ps.setString(1, c.getUserinfo().get(0).get_openid());
            select_ps.setString(1, c.get_openid());
            select_rs = select_ps.executeQuery();
            if(select_rs.next()) {
                userId = select_rs.getInt(1);
            }


            String sql = "insert into "+clocklnTableName+" (" +
                    "`user_id`,`address`,`healthy`,`hospital`,`wuhan`," +
                    "`gobacktime`,`remarks`,`create_time`,`quarantine`,`reason`," +
                    "`temperature`,`nobackreason`,`comfirmed`,`admitting`,`leave`," +
                    "`leavetime`,`flight`,`otherhealthy`,`hubei`," +
                    "`beijing`) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, userId);// user_id
            ps.setString(2, c.getPlace());//address
            ps.setInt(3, "2".equals(c.getBodyStatusFlag())?0:1+Integer.parseInt(c.getBodyStatusFlag())); // healthy
            ps.setInt(4, -1);// hospital
            ps.setInt(5, Integer.parseInt("1".equals(c.getGoHBFlag())?"0":"1")); // wuhan
            ps.setString(6, (c.getSuregobackdate()!=null&&!"".equals(c.getSuregobackdate()))?c.getSuregobackdate():c.getGobackdate());// gobacktime
            ps.setString(7, c.getRemark());//remarks
            ps.setString(8, c.getDate());//create_time
            ps.setInt(9, -1);// quarantine
            ps.setString(10, "");// reason
            try {
                ps.setDouble(11, Double.parseDouble(c.getTemperature()));//temperature
            } catch (Exception e) {
                ps.setDouble(11, 0.0);
            }
            ps.setInt(12, 1+Integer.parseInt((c.getNoGoBackFlag()==null||"".equals(c.getNoGoBackFlag()))?"-2":c.getNoGoBackFlag()));// nobackreason
            ps.setInt(13, Integer.parseInt("1".equals(c.getIsQueZhenFlag())?"1":"2"));// comfirmed
            ps.setInt(14, Integer.parseInt("1".equals(c.getGoHospitalFlag())?"1":"2"));// admitting
            ps.setInt(15, 1+Integer.parseInt((c.getIsLeaveBjFlag()==null||"".equals(c.getIsLeaveBjFlag()))?"0":c.getIsLeaveBjFlag()));// leave
            ps.setString(16, c.getLeavedate());// leavetime
            ps.setString(17, c.getTrainnumber());// flight
            ps.setString(18, c.getBodystatusotherremark());//otherhealthy
            if (c.getPlace().contains("湖北")) {
                ps.setInt(19, 1);
            } else {
                ps.setInt(19, 0);
            }
            if (c.getPlace().contains("北京")) {
                ps.setInt(20, 1);
            } else {
                ps.setInt(20, 0);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                select_rs.close();
                select_ps.close();
                JDBCUtils.close(rs, ps, con);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return id;
    }
}
