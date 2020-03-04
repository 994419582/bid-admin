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

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //逗号分隔数字类型正则
    public static final String PATTERN_STRING_LIST = "\\d+(,\\d+)*";

    //群状态(0:正常，1:删除，2:审核中)
    public static final Integer NORMAL = 0;
    public static final Integer DELETE = 1;
    public static final Integer APPROVAL_PENDING = 2;

    //群类型
    public static final Integer TYPE_ORGANIZATION = 0;
    public static final Integer TYPE_PERSON = 1;

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
    @Excel(name = "组织名称")
    private String name;
    /**
     * 群组名全称
     */
    @ApiModelProperty(value = "群组名全称", required = false)
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
    @ApiModelProperty(value = "群人数", required = false)
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
    private Integer createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Integer updateUser;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 群状态(0:正常，1:删除，2:审核中)
     */
    @ApiModelProperty(value = "群状态(0:正常，1:删除，2:审核中)")
    private Integer status;

    /**
     * 群类型（组织，个人）
     */
    @ApiModelProperty(value = "群组类型（组织0，个人1）",required = true)
    @NotNull(message = "群组类型不能为空")
    @Excel(name = "组织类型", replace = {"组织_0", "个人_1"})
    @Min(value = 0, message = "组织类型只能是0或者1")
    @Max(value = 1,message = "组织类型只能是0或者1")
    private Integer groupType;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人",required = false)
    private String contact;

    @ApiModelProperty(value = "联系电话",required = false)
    private String phone;

    @ApiModelProperty(value = "数据管理员",required = false)
    private String dataManagers;

    @ApiModelProperty(value = "父群主")
    @TableField(exist = false)
    private String parentGroups;

    /**
     * 父群组名称
     */
    @JsonIgnore
    @TableField(exist = false)
    @Excel(name = "父组织名称")
    private String parentName;
}
