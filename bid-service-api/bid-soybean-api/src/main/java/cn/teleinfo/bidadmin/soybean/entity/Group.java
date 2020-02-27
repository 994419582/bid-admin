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

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import org.springblade.core.mp.base.BaseEntity;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Data
@TableName("soybean_group")
@ApiModel(value = "Group对象", description = "Group对象")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    //顶级ID
    public static final Integer TOP_PARENT_ID = 0;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 群组名
     */
    @ApiModelProperty(value = "群组名", required = true)
    @NotBlank(message = "名称不能为空")
    @Pattern(regexp = "^(?!null).*", message = "名称不能包含字符串null")
    private String name;
    /**
     * 群组名全称
     */
    @ApiModelProperty(value = "群组名全称", required = true)
    @NotBlank(message = "全称不能为空")
    @Pattern(regexp = "^(?!null).*", message = "全称不能包含字符串null")
    private String fullName;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;
    /**
     * 群logo
     */
    @ApiModelProperty(value = "群logo")
    private String logo;
    /**
     * 群人数
     */
    @ApiModelProperty(value = "群人数", required = true)
    @NotNull(message = "创建人ID不能为空")
    @Min(value = 1,message = "群人数不能小于1")
    private Integer userAccount;
    /**
     * 群管理员
     */
    @ApiModelProperty(value = "群管理员")
    private String managers;
    /**
     * 群创建人
     */
    @ApiModelProperty(value = "群创建人")
    @Min(value = 1,message = "创建人ID不能小于1")
    private Integer createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @Min(value = 1,message = "更新人ID不能小于1")
    private Integer updateUser;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    /**
     * 群状态(0:正常，1:删除，2:审核中)
     */
    @ApiModelProperty(value = "群状态(0:正常，1:删除，2:审核中)")
    @Min(value = 0)
    private Integer status;
    /**
     * 是否需要审批(0:否，1:是)
     */
    @ApiModelProperty(value = "是否需要审批(0:否，1:是)")
    @Min(value = 0)
    private Integer approval;
    /**
     * 群组类型（公司，社区，其他）
     */
    @ApiModelProperty(value = "群组类型（公司，社区，其他）",required = true)
    @NotNull(message = "群组类型不能为空")
    @Range(min = 0,max = 2, message = "群组类型必须为公司(0),社区(1),其他(2)中的一项")
    private Integer groupType;
    /**
     * 公司地址ID（只有公司和社区需要）
     */
    @ApiModelProperty(value = "公司地址ID（只有公司和社区需要）")
    @Min(value = 0,message = "地址ID不能小于1")
    private Integer addressId;
    /**
     * 公司地址名称
     */
    @ApiModelProperty(value = "公司地址名称")
    private String addressName;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "父群主")
    @TableField(exist = false)
    @NotBlank(message = "父群组不能为空")
    private String parentGroups;
}
