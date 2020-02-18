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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("app_user_contacts")
@ApiModel(value = "UserContacts对象", description = "UserContacts对象")
public class UserContacts implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 用户BID
     */
    @ApiModelProperty(value = "用户BID")
    private String bid;
    /**
     * 联系人BID
     */
    @ApiModelProperty(value = "联系人BID")
    private String contactsBid;
    /**
     * 联系人昵称
     */
    @ApiModelProperty(value = "联系人昵称")
    private String contactsName;
    /**
     * 联系人备注
     */
    @ApiModelProperty(value = "联系人备注")
    private String contactsRemark;


}
