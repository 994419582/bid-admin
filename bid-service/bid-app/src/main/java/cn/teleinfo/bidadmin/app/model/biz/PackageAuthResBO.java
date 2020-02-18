package cn.teleinfo.bidadmin.app.model.biz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 身份认证申请
 *
 * @author tbc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageAuthResBO {

    private String sign;

    private BizPackage bizPackage;

    @Data
    public static class BizPackage {

        /**
         * 客户号
         */
        private String customNumber;

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
         * 认证结果
         */
        private String authResult;


        /**
         * 认证结果保留数据 (JSON 类型)
         */
        private String authResultRetainData;


        /**
         * 成功标志 true/false true:认证服务请求正常false: 认证服务请求异 常
         */
        private Boolean success;

        /**
         * 错误信息描述(可为空)
         */
        private String errorDesc;

    }
}
