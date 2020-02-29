package cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEntity implements Serializable {
    String _id;
    String _openid;
    String updated_at;
    String company_district;
    String home_detail;
    String created_at;
    String name;
    String company_department;
    String certificate_number;
    String home_district;
    String phone;
    String certificate_type;
    String company_detail;
    String usertype;
    String superuser;
}
