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
import java.util.List;

import cn.teleinfo.bidadmin.common.constant.Global;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.google.common.collect.Lists;
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
@TableName("cms_category")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "栏目对象", description = "栏目对象")
public class Category extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.AUTO)

  private Integer id;
    /**
     * 父级编号
     */
    @ApiModelProperty(value = "父级编号")
    @TableField("PARENT_ID")
  private Integer parentId;
    /**
     * 所有父级编号
     */
    @ApiModelProperty(value = "所有父级编号")
    @TableField("PARENT_IDS")
  private String parentIds;
    /**
     * 站点ID
     */
    @ApiModelProperty(value = "站点ID")
    @TableField("SITE_ID")
  private Integer siteId;
    /**
     * 单位ID
     */
    @ApiModelProperty(value = "单位ID")
    @TableField("OFFICE_ID")
  private Integer officeId;
    /**
     * 文章模型
     */
    @ApiModelProperty(value = "文章模型")
    @TableField("MODULE")
  private Integer module; // 栏目模型（article：文章；picture：图片；download：下载；link：链接；special：专题）
    /**
     * 栏目名称
     */
    @ApiModelProperty(value = "栏目名称")
    @TableField("NAME")
  private String name;
    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    @TableField("IMAGE")
  private String image;
    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    @TableField("HREF")
  private String href;
    /**
     * 目标
     */
    @ApiModelProperty(value = "目标")
    @TableField("TARGET")
  private String target;
    /**
     * 鎻忚堪
     */
    @ApiModelProperty(value = "描述")
    @TableField("DESCRIPTION")
  private String description;
    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    @TableField("KEYWORDS")
  private String keywords;
    /**
     * 排序（升序）
     */
    @ApiModelProperty(value = "排序（升序）")
    @TableField("SORT")
  private Integer sort;
    /**
     * 是否在导航中显示
     */
    @ApiModelProperty(value = "是否在导航中显示")
    @TableField("IN_MENU")
  private Integer inMenu; // 是否在导航中显示（1：显示；0：不显示）
    /**
     * 是否在分类页中显示列表
     */
    @ApiModelProperty(value = "是否在分类页中显示列表")
    @TableField("IN_LIST")
  private Integer inList; // 是否在分类页中显示列表（1：显示；0：不显示）
    /**
     * 是否允许评论
     */
    @ApiModelProperty(value = "是否允许评论")
    @TableField("ALLOW_COMMENT")
  private Integer allowComment;
    /**
     * 是否需要审核
     */
    @ApiModelProperty(value = "是否需要审核")
    @TableField("IS_AUDIT")
  private Integer isAudit;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    @TableField("REMARKS")
  private String remarks;



//  public Category(){
//    super();
//    this.module = "";
//    this.sort = 30;
//    this.inMenu = Global.HIDE;
//    this.inList = Global.SHOW;
//    this.allowComment = Global.NO;
//    this.isAudit = Global.NO;
//  }
}
