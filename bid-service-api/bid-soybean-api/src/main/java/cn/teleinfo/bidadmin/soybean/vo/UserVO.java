package cn.teleinfo.bidadmin.soybean.vo;

import cn.teleinfo.bidadmin.soybean.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 * @author tbc
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "微信用户", description = "微信用户")
public class UserVO extends User {

    private static final long serialVersionUID = 1L;
    
}
