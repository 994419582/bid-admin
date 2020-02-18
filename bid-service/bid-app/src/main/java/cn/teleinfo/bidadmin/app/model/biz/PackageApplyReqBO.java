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
public class PackageApplyReqBO {

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
         * 读卡控件版本
         */
        private String cardReaderVersion;

        /**
         * 活体控件版本
         */
        private String liveDetectionControlVersion;

        /**
         * 认证码控件版本
         */
        private String authCodeControlVersion;


        /**
         * 认证模式:
         * OXOF(DN+人像+网证+安全策略),
         * 0X4F(DN+人像+网证+安全策略+2项信息),
         * OX1F(DN+人像+网证+安全策略+4项信息),
         * 0X13(DN+人像+4项信息),
         * 0X1D(DN+网证+安全策略+4项信息),
         * 0X06(人像+网证),
         * 0X16(人像+网证+4项信息),
         * 0X10(4项信息),
         * 0X12(人像+4项信息),
         * 0X40(2项信息),
         * 0X42(人像+2项信息),
         */
        private String authMode;


        public BizPackage(String customerNumber, String appName, String timeStamp, String cardReaderVersion, String liveDetectionControlVersion, String authCodeControlVersion, String authMode) {
            this.customerNumber = customerNumber;
            this.appName = appName;
            this.timeStamp = timeStamp;
            this.cardReaderVersion = cardReaderVersion;
            this.liveDetectionControlVersion = liveDetectionControlVersion;
            this.authCodeControlVersion = authCodeControlVersion;
            this.authMode = authMode;
        }
    }


}
