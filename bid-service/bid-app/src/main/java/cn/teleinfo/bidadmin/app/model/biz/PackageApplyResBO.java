package cn.teleinfo.bidadmin.app.model.biz;

import lombok.Data;

/**
 * 身份认证申请
 *
 * @author tbc
 */
@Data
public class PackageApplyResBO {

    private String sign;

    private BizPackage bizPackage;


    @Data
    public static class BizPackage {

        /**
         * 客户号
         */
        private String customerNumber;

        /**
         * 应用名称
         */
        private String appName;

        /**
         * “YYYYMMddHHmmssSS S”时间戳
         */
        private String timeStamp;

        /**
         * 业务流水号
         */
        private String businessSerialNumber;


        /**
         * 随机数 客户端控件需要 Base64 解码
         */
        private String randomNumber;


        /**
         * 申请结果 true/false true 代表服务访问成功 false 代表服务访问失败
         */
        private Boolean success;

        /**
         * 错误信息描述(可为空)
         */
        private String errorDesc;
    }
}
