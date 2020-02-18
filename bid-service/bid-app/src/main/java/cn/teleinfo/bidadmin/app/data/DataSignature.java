package cn.teleinfo.bidadmin.app.data;

import cn.com.jit.new_vstk.exception.NewCSSException;
import cn.teleinfo.bidadmin.app.config.ApiProperties;
import cn.teleinfo.bidadmin.app.config.IdentityConfig;
import com.fri.ctid.security.jit.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by on 2018/3/21.
 * 签名数据
 */
@Component
public class DataSignature {

    private static String certID = "ywzd14";

    static BASE64Encoder be = new BASE64Encoder();
    static BASE64Decoder bd = new BASE64Decoder();

    @Autowired
    IdentityConfig config;

//    static Api api = new Api();

    //签名方法(吉大正元)
    public  String signature(byte[] data) throws NewCSSException, IOException {
        byte[] signReturn = null;
        try {
            Api api=config.signerAPI();
            signReturn = api.p7Sign(data,certID);
        } catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        
        System.out.println("签名"+Arrays.toString(signReturn));
        String sr = new String(be.encodeBuffer(signReturn));
        System.out.println("BASE64后"+sr);
        return sr;
    }


}