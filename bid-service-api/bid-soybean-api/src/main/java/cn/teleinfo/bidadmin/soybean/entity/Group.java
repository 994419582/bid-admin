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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
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
    @ApiModelProperty(value = "机构名", required = true)
    @NotBlank(message = "名称不能为空")
    @Pattern(regexp = "^(?!null).*", message = "名称不能包含字符串null")
    @Excel(name = "下级机构*")
    private String name;
    /**
     * 群组名全称
     */
    @ApiModelProperty(value = "机构名全称", required = false)
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
    @ApiModelProperty(value = "机构logo")
    private String logo;
    /**
     * 群人数
     */
    @ApiModelProperty(value = "机构人数", required = false)
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Integer userAccount;
    /**
     * 群管理员
     */
    @ApiModelProperty(value = "机构管理员")
    private String managers;
    /**
     * 群创建人
     */
    @ApiModelProperty(value = "机构创建人")
    @JsonSerialize(nullsUsing = NullSerializer.class)
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
    @JsonSerialize(nullsUsing = NullSerializer.class)
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
    @ApiModelProperty(value = "机构状态(0:正常，1:删除，2:审核中)")
    private Integer status;

    /**
     * 群类型（组织，个人）
     */
    @ApiModelProperty(value = "机构类型（组织0，个人1）",required = true)
    @NotNull(message = "机构类型不能为空")
    @Min(value = 0, message = "机构类型只能是0或者1")
    @Max(value = 1,message = "机构类型只能是0或者1")
    private Integer groupType;

    /**
     * 公司地址ID（只有公司和社区需要）
     */
    @ApiModelProperty(value = "单位地址ID（只有公司和社区需要）")
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Integer addressId;
    /**
     * 公司地址名称
     */
    @ApiModelProperty(value = "公司地址名称")
    @Excel(name = "单位地址")
    private String addressName;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    @Excel(name = "单位详细地址")
    private String detailAddress;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人",required = false)
    @Excel(name = "管理员")
    private String contact;

    @ApiModelProperty(value = "联系电话",required = false)
    @Excel(name = "联系电话")
    private String phone;

    @ApiModelProperty(value = "统计管理员",required = false)
    private String dataManagers;

    @ApiModelProperty(value = "上级机构ID, 管理后台使用")
    @TableField(exist = false)
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Integer parentId;

    @ApiModelProperty(value = "排序, 管理后台使用")
    @TableField(exist = false)
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Integer sort;

    /**
     * 父群组名称
     */
    @JsonIgnore
    @TableField(exist = false)
    @Excel(name = "上级机构*")
    private String parentName;

    @ApiModelProperty(value = "机构唯一码")
    private String groupCode;

    @ApiModelProperty(value = "机构标识")
    private String groupIdentify;
}
