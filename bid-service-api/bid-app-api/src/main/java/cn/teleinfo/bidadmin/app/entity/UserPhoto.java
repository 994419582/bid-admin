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
package cn.teleinfo.bidadmin.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-11-27
 */
@Data
@TableName("app_user_photo")
@ApiModel(value = "UserPhoto对象", description = "UserPhoto对象")
public class UserPhoto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户BID
     */
    @ApiModelProperty(value = "用户BID")
    @TableId(value = "ID", type = IdType.INPUT)
    private String id;
    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String photo;


}
