//package cn.teleinfo.bidadmin.soybean.mysql.data.transfer;
//
//import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity.ClocklnEntity;
//import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity.UserEntity;
//import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util.DatabaseUtils;
//import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util.JsonUtils;
//
//import java.util.List;
//
//public class MainClass {
//
//    private static String userPath = "/Users/shanpengfei/work/idea/bif/github/bid-admin/bid-service/bid-soybean/src/main/java/cn/teleinfo/bidadmin/soybean/mysql/data/transfer/file/user.json";
//    private static String clocklnPath = "/Users/shanpengfei/work/idea/bif/github/bid-admin/bid-service/bid-soybean/src/main/java/cn/teleinfo/bidadmin/soybean/mysql/data/transfer/file/clockln.json";
//
//    public static void main(String[] args) {
//
////      用户表的数据
//
////        List<? extends UserEntity> userJsonList = JsonUtils.filePathToEntity(userPath, new UserEntity().getClass());
////        System.out.println(userJsonList);
////        for (UserEntity user : userJsonList) {
////            DatabaseUtils.userInsert(user);
////        }
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
//        System.out.println("finish ~~~");
//    }
//
//}
