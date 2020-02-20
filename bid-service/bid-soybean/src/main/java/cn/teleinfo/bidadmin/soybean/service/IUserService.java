package cn.teleinfo.bidadmin.soybean.service;

import cn.teleinfo.bidadmin.soybean.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<User> {
    /**
     * 根据openid查询
     *
     * @param openid
     * @return
     */
    User findByWechatId(String openid);
}
