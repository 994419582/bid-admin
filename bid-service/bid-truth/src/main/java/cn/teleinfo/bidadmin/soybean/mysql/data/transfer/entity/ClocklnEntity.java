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

    String trafficToolStatusFlag;//交通工具, 0飞机  1火车  2轮船 3其他
    String workStatusFlag;//个人状态，0在岗办公 1居家办公 2居家隔离 3监督隔离
    String roommateHealthyStatusFlag;//共同居住人员亲属（含合租人员）健康状况, 0健康 1有发热、咳嗽等症状 2其他症状
    String roHealthystatusotherremark; //共同居住人员亲属（含合租人员）健康状况为其他的值
    String roommateCompanyDiagStatusFlag;//共同居住人员亲属（含合租人员）所在单位/公司是否有疑似病例、确诊病例, 0有确诊病例 1有疑似病例 2都无 3其他
    String roMaCoDistatusotherremark;//共同居住人员亲属（含合租人员）所在单位/公司是否有疑似病例、确诊病例为其他时的值
    String residentAreaStatusFlag;//居住小区是否有疑似病例、确诊病例,0有确诊病例 1有疑似病例 2都无 3其他
    String reArstatusotherremark;//居住小区是否有疑似病例、确诊病例为其他时的取值

    String temperotherremark;//temperStatusFlag=0:正常；temperStatusFlag=1：具体温度值
    String temperStatusFlag;//体温，0正常 1表示37.3以上
    List<UserEntity> userinfo;
}
