package cn.teleinfo.bidadmin.soybean.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel(value = "GroupTree对象", description = "GroupTree对象")
public class GroupTreeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 群组名
     */
    @ApiModelProperty(value = "群组名")
    private String name;

    /**
     * 群人数
     */
    @ApiModelProperty(value = "群人数")
    private Integer userAccount;

    /**
     * 父群组ID
     */
    @ApiModelProperty(value = "父群组ID")
    @JsonIgnore
    private Integer parentId;

    /**
     * 群logo
     */
    @ApiModelProperty(value = "群logo")
    private String logo;

    /**
     * 是否有管理员权限
     */
    @ApiModelProperty(value = "是否有管理权限")
    private boolean permission;

    /**
     * 子群组
     */
    @ApiModelProperty(value = "子群组")
    private List<GroupTreeVo> children = new ArrayList<>();

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
}
