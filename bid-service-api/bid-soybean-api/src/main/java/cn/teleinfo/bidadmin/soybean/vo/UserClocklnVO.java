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
package cn.teleinfo.bidadmin.soybean.vo;

import cn.teleinfo.bidadmin.soybean.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图实体类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserClocklnVO对象", description = "UserClocklnVO对象")
public class UserClocklnVO extends User {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "回京时间")
	private String gobacktime;
	@ApiModelProperty(value = "打卡地址")
	private String address;

	@ApiModelProperty(value = "是否离京标识")
	private Integer leave;
	@ApiModelProperty(value = "近14天，是否接触过湖北情况标识")
	private Integer hubei;
	@ApiModelProperty(value = "是否接触过武汉情况标识")
	private Integer wuhan;
	@ApiModelProperty(value = "目前健康状态标识")
	private Integer healthy;

	@ApiModelProperty(value = "是否离京")
	private String leaveString;
	@ApiModelProperty(value = "近14天，是否接触过湖北情况")
	private String hubeiString;
	@ApiModelProperty(value = "是否接触过武汉情况")
	private String wuhanString;
	@ApiModelProperty(value = "目前健康状态")
	private String healthyString;
}
