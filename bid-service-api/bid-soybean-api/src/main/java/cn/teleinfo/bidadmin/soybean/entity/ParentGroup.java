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
package cn.teleinfo.bidadmin.soybean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Data
@TableName("soybean_parent_group")
@ApiModel(value = "ParentGroup对象", description = "ParentGroup对象")
public class ParentGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    //群状态(0:正常，1:已审核，2:删除)
    public static final Integer NORMAL = 0;
    public static final Integer APPROVAL_PENDING = 1;
    public static final Integer DELETE = 2;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "父群ID")
    private Integer parentId;

    @ApiModelProperty(value = "子群ID")
    private Integer groupId;

    @ApiModelProperty(value = "状态（0:未读，1:已读，2:删除）")
    private Integer status;

    @ApiModelProperty(value = "排序")
    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    private Integer sort;
}
