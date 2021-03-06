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
package cn.teleinfo.bidadmin.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 官网合作伙伴实体类
 *
 * @author Blade
 * @since 2019-10-15
 */
@Data
@TableName("website_partner")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Partner对象", description = "官网合作伙伴")
public class Partner extends BaseEntity {

    private static final long serialVersionUID = 1L;

  @TableId(value = "ID", type = IdType.AUTO)
  private Integer id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @TableField("NAME")
  private String name;
    /**
     * 官网
     */
    @ApiModelProperty(value = "官网")
    @TableField("WEBSITE")
  private String website;
    /**
     * LOGO
     */
    @ApiModelProperty(value = "LOGO")
    @TableField("LOGO")
  private String logo;
    /**
     * 伙伴类型
     */
    @ApiModelProperty(value = "伙伴类型")
    @TableField("TYPE")
  private Integer type;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("REMARKS")
  private String remarks;


}
