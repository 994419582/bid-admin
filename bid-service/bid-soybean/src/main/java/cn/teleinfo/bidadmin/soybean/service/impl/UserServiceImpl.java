package cn.teleinfo.bidadmin.soybean.service.impl;

import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.mapper.UserMapper;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User findByWechatId(String openid) {
        return baseMapper.selectOne(Wrappers.<User>query().eq("wechat_id", openid));
    }
}

