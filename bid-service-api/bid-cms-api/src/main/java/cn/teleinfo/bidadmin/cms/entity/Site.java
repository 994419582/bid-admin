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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-10-08
 */
@Data
@TableName("cms_site")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "站点对象", description = "站点对象")
public class Site extends BaseEntity {

    private static final long serialVersionUID = 1L;

  /** 任务ID */
  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.AUTO)
  @ApiModelProperty(value = "主键id")
  private Integer id;
    /**
     * 站点名称
     */
    @ApiModelProperty(value = "站点名称")
    @TableField("NAME")
  private String name;
    /**
     * 站点标题
     */
    @ApiModelProperty(value = "站点标题")
    @TableField("TITLE")
  private String title;
    /**
     * 站点logo
     */
    @ApiModelProperty(value = "站点logo")
    @TableField("LOGO")
  private String logo;

  /**
   * 站点logo
   */
  @ApiModelProperty(value = "站点logoHash")
  @TableField("LOGO_HASH")
  private String logoHash;
    /**
     * 站点域名
     */
    @ApiModelProperty(value = "站点域名")
    @TableField("DOMAIN")
  private String domain;
    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    @TableField("KEYWORDS")
  private String keywords;
    /**
     * 版权信息
     */
    @ApiModelProperty(value = "版权信息")
    @TableField("COPYRIGHT")
  private String copyright;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("REMARKS")
  private String remarks;


}
