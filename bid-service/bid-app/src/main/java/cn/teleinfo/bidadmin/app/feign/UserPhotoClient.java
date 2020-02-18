package cn.teleinfo.bidadmin.app.feign;

import cn.teleinfo.bidadmin.app.entity.UserPhoto;
import cn.teleinfo.bidadmin.app.service.IUserPhotoService;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore()
@RestController
@AllArgsConstructor
public class UserPhotoClient implements IUserPhotoClient {

    @Autowired
    IUserPhotoService service;

    @PostMapping(API_PREFIX + "/upload")
    public R upload(@RequestBody UserPhoto userPhoto){
        System.out.println(userPhoto);
        return R.status(service.saveOrUpdate(userPhoto));
    }
}
