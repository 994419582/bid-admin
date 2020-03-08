package cn.teleinfo.bidadmin.soybean.mysql.data.transfer;

import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.config.TransferConfig;
import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity.ClocklnEntity;
import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity.UserEntity;
import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util.DatabaseUtils;
import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util.JsonUtils;

import java.util.List;

import static cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util.DatabaseUtils.userGroupUpdate;

public class MainClass {

//    public static void main(String[] args) {
////      用户表的数据
//        List<? extends UserEntity> userJsonList = JsonUtils.filePathToEntity(TransferConfig.userPath, new UserEntity().getClass());
//        System.out.println(userJsonList);
//        for (UserEntity user : userJsonList) {
//            DatabaseUtils.userInsert(user);
//        }
//        int count = userGroupUpdate();
//        System.out.println("user finish ~~~, update count:" + count);
//    }

//    public static void main(String[] args) {
////      打卡表的数据
//        List<? extends ClocklnEntity> clocklnJsonList = JsonUtils.filePathToEntity(TransferConfig.clocklnPath, new ClocklnEntity().getClass());
//        System.out.println(clocklnJsonList);
//        for(ClocklnEntity clocklnEntity : clocklnJsonList) {
//            DatabaseUtils.clocklnInsert(clocklnEntity);
//        }
//        System.out.println("clock finish ~~~");
//    }
}
