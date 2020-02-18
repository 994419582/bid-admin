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
package cn.teleinfo.bidadmin.blockchain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-10-17
 */
@Data
@ApiModel(value = "BidDocument对象", description = "BidDocument对象")
public class BidDocument implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "Id", type = IdType.AUTO)
  private Integer id;
    /**
     * bid
     */
    @ApiModelProperty(value = "bid")
    private String bid;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String name;
    /**
     * 模板
     */
    @ApiModelProperty(value = "模板")
    @TableField("Contexts")
  private String Contexts;
    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    @TableField("TYPE")
  private Integer type;
    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    @TableField("ISENABLE")
  private Integer isEnable;

  @TableField("BALANCE")
  private String balance;


}
