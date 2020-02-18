package cn.teleinfo.bidadmin.app.data;

import cn.teleinfo.bidadmin.app.config.IdentityConfig;
import com.fri.ctid.security.jit.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

/**
 * Created by on 2018/3/29.
 * 保留数据区
 * 加密保留数据与照片工具类
 */
@Component
public class EncryptedReservedData {
    //private static String certID = "rzfwyw02";
    private static String certID = "rzfw01";
    private static BASE64Encoder be = new BASE64Encoder();
//    static com.fri.ctid.security.bjca.Api api2 = new com.fri.ctid.security.bjca.Api();

    @Autowired
    private IdentityConfig config;

    //加密保留数据(吉大正元)
    public byte[] encryptEnvelope(byte[] msg) throws Exception {
        Api api = config.signerAPI();
        byte[] result = api.encryptEnvelope(msg, certID);
        return result;
    }

//    //加密保留数据(北京CA)
//    public String encodeEnvelopedDataForJit(String inData) {
//        String envelopeCertID = "7f7ac12ae25029cf";
//        byte[] DecodeEnvelopeData = api2.encryptEnvelope(inData.getBytes(), envelopeCertID);
//        String de = be.encode(DecodeEnvelopeData);
//        return de;
//    }
}
