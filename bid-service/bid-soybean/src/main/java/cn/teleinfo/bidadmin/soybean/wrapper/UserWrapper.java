package cn.teleinfo.bidadmin.soybean.wrapper;

import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;

public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

    public static UserWrapper build() {
        return new UserWrapper();
    }

    @Override
    public UserVO entityVO(User user) {
        UserVO userVO = BeanUtil.copy(user, UserVO.class);
        return userVO;
    }
}
