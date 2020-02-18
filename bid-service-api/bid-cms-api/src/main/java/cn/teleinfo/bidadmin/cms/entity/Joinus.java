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
@TableName("website_joinus")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Joinus对象", description = "官网合作伙伴")
public class Joinus extends BaseEntity {

    private static final long serialVersionUID = 1L;

  @TableId(value = "ID", type = IdType.AUTO)
  private Integer id;
    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    @TableField("NAME")
  private String name;
    /**
     * 岗位要求
     */
    @ApiModelProperty(value = "岗位要求")
    @TableField("QUALIFICATIONS")
  private String qualifications;
    /**
     * LOGO
     */
    @ApiModelProperty(value = "LOGO")
    @TableField("IMAGE")
  private String image;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("REMARKS")
  private String remarks;

    /**
     * 岗位职责
     */
    @ApiModelProperty(value = "岗位职责")
    @TableField("DESCRIPTION")
  private String description;


}
