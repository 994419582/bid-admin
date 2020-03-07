package cn.teleinfo.bidadmin.soybean.mysql.data.transfer;

import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity.ClocklnEntity;
import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity.UserEntity;
import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util.DatabaseUtils;
import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util.JsonUtils;

import java.util.List;

import static cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util.DatabaseUtils.userGroupUpdate;

public class MainClass {

    private static String userPath = "/Users/shanpengfei/work/idea/bif/github/bid-admin/bid-service/bid-soybean/src/main/java/cn/teleinfo/bidadmin/soybean/mysql/data/transfer/file/database_export-user_info.json";
    private static String clocklnPath = "/Users/shanpengfei/work/idea/bif/github/bid-admin/bid-service/bid-soybean/src/main/java/cn/teleinfo/bidadmin/soybean/mysql/data/transfer/file/database_export-user_healthy.json";

//    public static void main(String[] args) {
//
////      用户表的数据
//
////        List<? extends UserEntity> userJsonList = JsonUtils.filePathToEntity(userPath, new UserEntity().getClass());
////        System.out.println(userJsonList);
////        for (UserEntity user : userJsonList) {
////            DatabaseUtils.userInsert(user);
////        }
////        userGroupUpdate();
//
//
//
//
////      打卡表的数据
//
//        List<? extends ClocklnEntity> clocklnJsonList = JsonUtils.filePathToEntity(clocklnPath, new ClocklnEntity().getClass());
//        System.out.println(clocklnJsonList);
//        for(ClocklnEntity clocklnEntity : clocklnJsonList) {
//            DatabaseUtils.clocklnInsert(clocklnEntity);
//        }
//
//
//
//
//        System.out.println("finish ~~~");
//    }

}
