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
 * @since 2019-10-08
 */
@Data
@TableName("cms_article_data")
@ApiModel(value = "文章详情", description = "文章详情")
public class ArticleData  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId("ID")
  private Integer id;
    /**
     * 正文内容
     */
    @ApiModelProperty(value = "正文内容")
    @TableField("CONTENT")
  private String content;
    /**
     * 文章来源
     */
    @ApiModelProperty(value = "文章来源")
    @TableField("COPYFROM")
  private String copyfrom;
    /**
     * 附件链接
     */
    @ApiModelProperty(value = "附件链接")
    @TableField("RELATION")
  private String relation;
    /**
     * 允许评论
     */
    @ApiModelProperty(value = "允许评论")
    @TableField("ALLOW_COMMENT")
  private String allowComment;


}
