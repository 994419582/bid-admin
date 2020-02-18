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
package cn.teleinfo.bidadmin.cms.vo;

import cn.teleinfo.bidadmin.cms.entity.Article;
import cn.teleinfo.bidadmin.cms.entity.ArticleData;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 视图实体类
 *
 * @author Blade
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ArticleVO对象", description = "ArticleVO对象")
public class ArticleVO extends Article {
	private static final long serialVersionUID = 1L;

	/**
	 * 正文内容
	 */
	@ApiModelProperty(value = "正文内容")
	private String content;
	/**
	 * 文章来源
	 */
	@ApiModelProperty(value = "文章来源")
	private String copyfrom;
	/**
	 * 附件链接
	 */
	@ApiModelProperty(value = "附件链接")
	private String relation;
	/**
	 * 允许评论
	 */
	@ApiModelProperty(value = "允许评论")
	private String allowComment;

	@ApiModelProperty(value = "栏目名称")
	private String categoryName;

	@ApiModelProperty(value = "栏目名称")
	private String userName;

	@ApiModelProperty(value = "栏目名称")
	private Integer topId;

	@ApiModelProperty(value = "栏目名称")
	private Integer steponsId;

}
