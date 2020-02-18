package cn.teleinfo.bidadmin.app;

import cn.teleinfo.bidadmin.common.CommonApplication;
import cn.teleinfo.bidadmin.common.constant.AppConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springblade.core.launch.BladeApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@MapperScan("cn.teleinfo.bidadmin.app.mapper")
public class AppApplication {
    public static void main(String[] args) {
        CommonApplication.run(AppConstant.APPLICATION_APP_NAME, AppApplication.class, args);
    }
}
