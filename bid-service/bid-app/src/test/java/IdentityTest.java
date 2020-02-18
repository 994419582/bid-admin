import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.teleinfo.bidadmin.app.AppApplication;
import cn.teleinfo.bidadmin.app.entity.Result;
import cn.teleinfo.bidadmin.app.service.IAuthenticateInfoService;
import cn.teleinfo.bidadmin.app.vo.ReservedDataEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = AppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-app", profile = "dev", enableLoader = true)
public class IdentityTest {

    @Autowired
    private IAuthenticateInfoService service;

    /**
     * 人像比对
     */
    @Test
    public void simpleVerify() {
        // 照片
//        String photoData = Base64.encode(FileUtil.file("classpath:identity/timg.png"));
//        String name = "张三";
//        String code = "1100************";
//        IdentityBO ok = identityUtil.simpleVerify(name, code, photoData);
//        System.out.println("(0：比对成功)信息比对结果：" + "(" + ok.getFirstAuth() + ")" + ok.getFirstMsg());
//        System.out.println("(0：比对成功)人像比对结果：" + "(" + ok.getSecondAuth() + ")" + ok.getSecondMsg());

    }


    /**
     * 人像比对
     */
    @Test
    public void verify() {
        // 公共字段
//        String photoData = Base64.encode(FileUtil.file("/Users/shanpengfei/work/idea/bif/bid-admin/bid-service/bid-app/src/test/resources/pic/shiweijun.jpg"));
        String photoData = Base64.encode(FileUtil.file("classpath:shiweijun.jpg"));
        System.out.println(photoData);

        // 保留数据
        ReservedDataEntity.SFXXBean sfxxBean = new ReservedDataEntity.SFXXBean();
        sfxxBean.setxM("");
        sfxxBean.setgMSFZHM("");

        ReservedDataEntity.WZXXBean wzxxBean = new ReservedDataEntity.WZXXBean();
        wzxxBean.setBusinessType("test");
//        rw.setDealDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        wzxxBean.setDealDate("201809131559000000");
        wzxxBean.setVenderName("yingxininfo");
        ReservedDataEntity.ZPBean zpBean = new ReservedDataEntity.ZPBean();

        ReservedDataEntity dataExEntit = new ReservedDataEntity();
        dataExEntit.setsFXX(sfxxBean);
        dataExEntit.setzP(zpBean);
        dataExEntit.setwZXX(wzxxBean);

        Result result = service.authenticateIdentity(dataExEntit, photoData);
        System.out.println(result);
    }

}
