/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.teleinfo.bidadmin.soybean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Data
@TableName("soybean_user_group")
@ApiModel(value = "UserGroup对象", description = "UserGroup对象")
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    //正常
    public static final Integer NORMAL = 0;
    //待审批
    public static final Integer APPROVAL_PENDING = 1;
    //删除
    public static final Integer DELETE = 2;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键")
    @NotNull
    private Integer userId;
    /**
     * 群组主键
     */
    @ApiModelProperty(value = "群组主键")
    @NotNull
    private Integer groupId;

    /**
     * 群用户状态
     */
    @ApiModelProperty(value = "群用户状态")
    private Integer status;
}
