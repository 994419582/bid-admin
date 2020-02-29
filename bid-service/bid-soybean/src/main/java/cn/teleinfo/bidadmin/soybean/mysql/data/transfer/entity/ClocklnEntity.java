package cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ClocklnEntity implements Serializable {
//    goHBFlag=1
//    date=2020-02-23,
//    bodyStatusFlag=0,
//    isGoBackFlag=0,
//    remark=,
//    noGoBackFlag=null,
//    phone=18940047983,
//    suregobackdate=null,
//    trainnumber=null,
//    bodystatusotherremark=null,
    String date;
    String goHBFlag;
    String bodyStatusFlag;
    String isGoBackFlag;
    String isQueZhenFlag;
    String remark;
    String goHospitalFlag;
    String noGoBackFlag;
    String phone;
    String addtime;
    String suregobackdate;
    String _openid;
    String temperature;
    String name;
    String _id;
    String place;
    String isLeaveBjFlag;
    String gobackdate;
    String leavedate;
    String trainnumber;
    String bodystatusotherremark;
    List<UserEntity> userinfo;
}
