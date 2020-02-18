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
public class PackageAuthReqBO {

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
         * 申请获取的业务流水号
         */
        private String businessSerialNumber;

        /**
         * 认证模式必须和申请的模式一致
         */
        private String authMode;

        /**
         * 照片数据 Base64 编码串 按认证模式需要(可为空)
         */
        private String photoData;

        /**
         * 认证码数据(可为空)
         */
        private String authCode;

        /**
         * ID 验证数据(可为空)
         */
        private String idcardAuthData;


        /**
         * 认证保留数据加密后 Base64 编码串(可为空)
         */
        private String authApplyRetainData;

        public BizPackage(String customNumber, String appName, String timeStamp, String businessSerialNumber, String authMode, String photoData, String authCode, String idcardAuthData, String authApplyRetainData) {
            this.customNumber = customNumber;
            this.appName = appName;
            this.timeStamp = timeStamp;
            this.businessSerialNumber = businessSerialNumber;
            this.authMode = authMode;
            this.photoData = photoData;
            this.authCode = authCode;
            this.idcardAuthData = idcardAuthData;
            this.authApplyRetainData = authApplyRetainData;
        }
    }

    /**
     * 保留数据
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservedData {

        // 身份信息
        private SFXXBean sFXX;

        // 网站信息
        private WZXXBean wZXX;

        // 照片
        private ZPBean zP;

        /**
         * 身份信息
         */
        @Data
        public static class SFXXBean {
            // 姓名
            private String xM = "";
            // 公民身份证号码
            private String gMSFZHM = "";
            // 有效期起始日期
            private String yXQQSRQ = "";
            // 有效期截止日期
            private String yXQJZRQ = "";
            // DN 信息
            private String dN = "";
            // 民族代码
            private String mZDM = "";
            // 签发机关
            private String qFJG = "";
            // 性别代码
            private String xBDM = "";
            // 出生日期
            private String cSRQ = "";
            // 住址
            private String zZ = "";
        }

        @Data
        public static class WZXXBean {
            // 网站类型，例如：门户网站
            private String businessType = "";
            // 创建时间，例如：20171009
            private String dealDate = "";
            // 网站名称
            private String venderName = "";
            // 网址，例如：www.chnctid.cn
            private String vendorIp = "";
        }

        @Data
        public static class ZPBean {
            // BASE64 编码
            private String wLTZP = "";
        }


    }
}