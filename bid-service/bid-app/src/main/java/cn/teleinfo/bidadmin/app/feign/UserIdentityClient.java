package cn.teleinfo.bidadmin.app.feign;

import cn.teleinfo.bidadmin.app.dto.AuthenticationDTO;
import cn.teleinfo.bidadmin.app.entity.Result;
import cn.teleinfo.bidadmin.app.service.IAuthenticateInfoService;
import cn.teleinfo.bidadmin.app.vo.ReservedDataEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore()
@RestController
@AllArgsConstructor
@Api(value = "身份认证接口", tags = "身份认证接口")
public class UserIdentityClient implements IUserIdentityClient {

    @Autowired
    IAuthenticateInfoService service;

    @PostMapping(API_PREFIX + "/authentication")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "姓名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "idCard", value = "身份证号", paramType = "query"),
            @ApiImplicitParam(name = "photo", value = "照片", paramType = "query")
    })
    public R authentication(@RequestBody AuthenticationDTO authenticationDTO) {
        ReservedDataEntity.SFXXBean sfxxBean = new ReservedDataEntity.SFXXBean();
        sfxxBean.setxM(authenticationDTO.getUserName());
        sfxxBean.setgMSFZHM(authenticationDTO.getIdCard());

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

        Result result = service.authenticateIdentity(dataExEntit, authenticationDTO.getPhoto());
        if (result == null || !("00XX").equalsIgnoreCase(result.getAuthResult())) {
            return R.fail("验证身份信息异常");
        }
        return R.data(result);
    }
}
