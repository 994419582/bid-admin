package cn.teleinfo.bidadmin.soybean.mysql.data.transfer.config;

public class TransferConfig {
    //    private static final String connectionURL = "jdbc:mysql://39.99.132.122:3306/bidadmin?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
//    public static final String connectionURL = "jdbc:mysql://39.99.132.122:3306/bidadmin-test?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
    public static final String connectionURL = "jdbc:mysql://39.99.132.122:3306/bidadmin-pro?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowMultiQueries=true&serverTimezone=GMT%2B8";
    public static final String username = "root";
    public static final String password = "bif123*()";

//    public static final String userTableName = "spf_soybean_user";
//    public static final String groupTableName = "spf_soybean_user_group";
//    public static final String clocklnTableName = "spf_soybean_clockln";

    public static final String groupTN = "soybean_group";

    public static final String userTableName = "soybean_user";
    public static final String groupTableName = "soybean_user_group";
    public static final String clocklnTableName = "soybean_clockln";

    public static final String userPath = "/Users/shanpengfei/work/idea/bif/github/bid-admin/bid-service/bid-soybean/src/main/java/cn/teleinfo/bidadmin/soybean/mysql/data/transfer/file/database_export-user_info.json";
    public static final String clocklnPath = "/Users/shanpengfei/work/idea/bif/github/bid-admin/bid-service/bid-soybean/src/main/java/cn/teleinfo/bidadmin/soybean/mysql/data/transfer/file/database_export-user_healthy(1).json";

}
