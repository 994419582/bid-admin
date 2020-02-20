package cn.teleinfo.bidadmin.soybean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author tbc
 */
@Data
@TableName("soybean_user")
@ApiModel(value = "微信用户", description = "微信用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父主键
     */
    @ApiModelProperty(value = "微信openid")
    private String wechatId;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 证件类型ID
     */
    @ApiModelProperty(value = "证件类型ID")
    private String idType;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    private String idNumber;

    /**
     * 当前状态
     */
    @ApiModelProperty(value = "当前状态")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     * 国家
     */
    @ApiModelProperty(value = "国家")
    private String country;

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatarUrl;
}
