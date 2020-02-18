package cn.teleinfo.bidadmin.app.service.impl;

import cn.teleinfo.bidadmin.app.config.IdentityConfig;
import cn.teleinfo.bidadmin.app.config.IdentityProperties;
import cn.teleinfo.bidadmin.app.data.DataSignature;
import cn.teleinfo.bidadmin.app.data.EncryptedReservedData;
import cn.teleinfo.bidadmin.app.entity.Result;
import cn.teleinfo.bidadmin.app.service.IAuthenticateInfoService;
import cn.teleinfo.bidadmin.app.util.HttpSend;
import cn.teleinfo.bidadmin.app.vo.AuthenticationApplicationData;
import cn.teleinfo.bidadmin.app.vo.AuthenticationData;
import cn.teleinfo.bidadmin.app.vo.ReservedDataEntity;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Service
public class AuthenticateInfoServiceImpl implements IAuthenticateInfoService {
	
	@SuppressWarnings("restriction")
	static BASE64Encoder be = new BASE64Encoder();

	@Autowired
	private IdentityConfig config;

//    @Value("${api.identity.fri.request.url:http://MA1.chnctid.cn:8100/ctid/api/v1/request}")
    private String REQUEST_URL = "http://MA1.chnctid.cn:8100/ctid/api/v1/request";

//    @Value("${api.identity.fri.auth.url:http://MA1.chnctid.cn:8200/ctid/api/v1/authentication}")
    private String AUTH_URL = "http://MA1.chnctid.cn:8200/ctid/api/v1/authentication";

    @Autowired
    DataSignature ds ;

    @Autowired
    EncryptedReservedData erd;

	public Result authenticateIdentity(ReservedDataEntity reservedDataEntity ,String photo) {
		Result result = null;
		try {
			result = doAuthenticate(reservedDataEntity,photo);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("接口异常消息："+ e.getMessage());
			result = new Result();
			result.setAuthResult("XXXX");
			result.setBusinessSerialNumber(null);
			result.setErrorDesc("接口签名异常");
			result.setSuccess(null);
			result.setTimeStamp(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		}
		return result;
	}
	
	private Result doAuthenticate(ReservedDataEntity reservedDataEntity,String photo) throws Exception{
		log.info("authenticateTwoItems()--start");
		IdentityProperties properties=config.getProperties();
		/**
		 * 身份认证申请数据
		 */
		log.info("请求数据reservedDataEntity:" + reservedDataEntity.toString());
        String customerNumber = properties.getCustomerNumber();
        String appName = properties.getAppName();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String cardReaderVersion = properties.getCardReaderVersion();
        String liveDetectionControlVersion = properties.getLiveDetectionControlVersion();
        String authCodeControlVersion = properties.getAuthCodeControlVersion();
        String authMode = properties.getAuthMode();
        AuthenticationApplicationData.BizPackageBean bizPackageBean = new AuthenticationApplicationData.BizPackageBean();
        bizPackageBean.setCustomerNumber(customerNumber);
        bizPackageBean.setAppName(appName);
        bizPackageBean.setTimeStamp(timeStamp);
        bizPackageBean.setCardReaderVersion(cardReaderVersion);
        bizPackageBean.setLiveDetectionControlVersion(liveDetectionControlVersion);
        bizPackageBean.setAuthCodeControlVersion(authCodeControlVersion);
        bizPackageBean.setAuthMode(authMode);
        JSONObject applyObj = JSONObject.fromObject(bizPackageBean);
        String apply = applyObj.toString();
		//申请数据签名,吉大正元签名
        String signature = ds.signature(applyObj.toString().getBytes());

        AuthenticationApplicationData appData = new AuthenticationApplicationData();
        appData.setBizPackage(bizPackageBean);
        appData.setSign(signature);
        JSONObject obj = JSONObject.fromObject(appData);
        log.info("身份认证申请数据"+obj.toString());
        /**
         * 身份认证申请应答数据
         */
        HttpSend hs = new HttpSend();
        String json = hs.HttpResponse(REQUEST_URL,obj);
        log.info("身份认证申请应答数据:"+json);
        /**
         * 身份认证请求数据
         */
        JSONObject jsonobject=JSONObject.fromObject(json);
        AuthenticationData data= (AuthenticationData) JSONObject.toBean(jsonobject,AuthenticationData.class);
        AuthenticationData.BizPackageBean adb = data.getBizPackage();
        //获取业务流水号
        String BusinessSerialNumber = adb.getBusinessSerialNumber();
        log.info("业务流水号："+adb.getBusinessSerialNumber());
        adb.setCustomNumber(customerNumber);
        adb.setAppName(appName);
        adb.setTimeStamp(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        adb.setBusinessSerialNumber(BusinessSerialNumber);
        adb.setAuthMode(authMode);
        //照片
        adb.setPhotoData(photo);
        //认证码数据
        adb.setAuthCode(null);
        //ID验证数据
        adb.setIdcardAuthData(null);

        JSONObject jo = JSONObject.fromObject(reservedDataEntity);
        //加密保留数据(吉大正元)
        String authApplyRetainData=be.encode(erd.encryptEnvelope(jo.toString().getBytes()));

        adb.setAuthApplyRetainData(authApplyRetainData);
        AuthenticationData add = new AuthenticationData();
        JSONObject objAdd = JSONObject.fromObject(adb);
        add.setBizPackage(adb);
        //身份认证请求包签名(吉大正元)
//        System.out.println(objAdd.toString());
        String signature2 = ds.signature(objAdd.toString().getBytes());
        log.info("身份认证请求数据"+Arrays.toString(objAdd.toString().getBytes()));
//        System.out.println(new String(objAdd.toString().getBytes()));
        add.setSign(signature2);
        JSONObject obj2 = JSONObject.fromObject(add);
        /**
         *获取身份认证应答数据，返回结果result 
         */
        String json2 = hs.HttpResponse(AUTH_URL,obj2);
        log.info("身份认证请求应答数据" + json2);
        Result result = new Result();
        JSONObject authResult = JSONObject.fromObject(json2);
        JSONObject object = authResult.getJSONObject("bizPackage");
        result.setSuccess(object.getString("success"));
        result.setBusinessSerialNumber(object.getString("businessSerialNumber"));
        result.setAuthResult(object.getString("authResult"));
        result.setErrorDesc(object.getString("errorDesc"));
        result.setTimeStamp(object.getString("timeStamp"));
        result.setxM(reservedDataEntity.getsFXX().getxM());
        result.setgMSFZHM(reservedDataEntity.getsFXX().getgMSFZHM());
        log.info("最终响应结果result:" + result.toString());
        log.info("authenticateTwoItems()--end");
		return result;
	}

}
