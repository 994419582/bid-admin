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

import cn.teleinfo.bidadmin.cms.entity.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import org.springblade.core.tool.node.INode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 视图实体类
 *
 * @author Blade
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CategoryVO对象", description = "CategoryVO对象")
public class CategoryVO extends Category implements INode {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 父节点ID
	 */
	private Integer parentId;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<INode> children;

	@Override
	public List<INode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}


	@ApiModelProperty(value = "站点名称")
	private String siteName;

	@ApiModelProperty(value = "父菜单名称")
	private String parentName;

	@ApiModelProperty(value = "部门名称")
	private String officeName;

	@ApiModelProperty(value = "模型名称")
	private String moduleName;

	private Date beginDate;	// 开始时间
	private Date endDate;	// 结束时间
	private String cnt;//信息量
	private String hits;//点击量
	private String tops;
	private String stepons;

}
