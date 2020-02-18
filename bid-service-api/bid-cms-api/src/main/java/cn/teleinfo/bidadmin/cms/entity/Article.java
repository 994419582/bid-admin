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

import java.math.BigDecimal;
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
 * 实体类
 *
 * @author Blade
 * @since 2019-10-08
 */
@Data
@TableName("cms_article")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文章对象", description = "文章对象")
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.AUTO)
  private Integer id;
    /**
     * 栏目编号
     */
    @ApiModelProperty(value = "栏目编号")
    @TableField("CATEGORY_ID")
  private Integer categoryId;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField("TITLE")
  private String title;
    /**
     * 文章链接
     */
    @ApiModelProperty(value = "文章链接")
    @TableField("LINK")
  private String link;
    /**
     * 标题颜色
     */
    @ApiModelProperty(value = "标题颜色")
    @TableField("COLOR")
  private String color;
    /**
     * 文章图片
     */
    @ApiModelProperty(value = "文章图片")
    @TableField("IMAGE")
  private String image;
    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    @TableField("KEYWORDS")
  private String keywords;
    /**
     * 描述、摘要
     */
    @ApiModelProperty(value = "描述、摘要")
    @TableField("DESCRIPTION")
  private String description;
    /**
     * 权重，越大越靠前
     */
    @ApiModelProperty(value = "权重，越大越靠前")
    @TableField("WEIGHT")
  private BigDecimal weight;
    /**
     * 权重期限
     */
    @ApiModelProperty(value = "权重期限")
    @TableField("WEIGHT_DATE")
  private LocalDateTime weightDate;
    /**
     * 点击数
     */
    @ApiModelProperty(value = "点击数")
    @TableField("HITS")
  private BigDecimal hits;
    /**
     * 推荐位，多选
     */
    @ApiModelProperty(value = "推荐位，多选")
    @TableField("POSID")
  private String posid;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("REMARKS")
  private String remarks;
    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    @TableField("TOPS")
  private Integer tops;
    /**
     * 踩的数量
     */
    @ApiModelProperty(value = "踩的数量")
    @TableField("STEPONS")
  private Integer stepons;


}
