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

import java.io.Serializable;
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
@TableName("cms_comment")
@ApiModel(value = "评论对象", description = "评论对象")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
  private Integer id;
    /**
     * 栏目ID
     */
    @ApiModelProperty(value = "栏目编号")
    @TableField("CATEGORY_ID")
  private Integer categoryId;
    /**
     * 栏目内容的编号
     */
    @ApiModelProperty(value = "文章编号")
    @TableField("CONTENT_ID")
  private Integer contentId;
    /**
     * 栏目内容的标题
     */
    @ApiModelProperty(value = "文章标题")
    @TableField("TITLE")
  private String title;
    /**
     * 评论
     */
    @ApiModelProperty(value = "评论")
    @TableField("CONTENT")
  private String content;
  /**
   * 评论人名称
   */
  @ApiModelProperty(value = "评论人BID")
  @TableField("BID")
  private String bid;
    /**
     * 评论人名称
     */
    @ApiModelProperty(value = "评论人名称")
    @TableField("NAME")
  private String name;
    /**
     * 评论IP
     */
    @ApiModelProperty(value = "评论IP")
    @TableField("IP")
  private String ip;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATETIME")
  private LocalDateTime createtime;
    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    @TableField("AUDIT_USER_ID")
  private Integer auditUserId;
    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @TableField("AUDIT_DATE")
  private LocalDateTime auditDate;
  /**
   * 审核时间
   */
  @ApiModelProperty(value = "评论状态")
  @TableField("status")
  private Integer status;


}
