package cn.teleinfo.bidadmin.soybean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ExcelGroupVo {

    /**
     * 群logo
     */
    @ApiModelProperty(value = "群logo", required = true)
    private String logo;

    /**
     * 群组名
     */
    @ApiModelProperty(value = "群组名", required = true)
    @NotBlank(message = "机构名称不能为空")
    private String name;

    /**
     * 公司地址名称
     */
    @ApiModelProperty(value = "公司地址名称", required = true)
    @NotBlank(message = "公司地址名称不能为空")
    @Pattern(regexp = "[^，]+，[^，]+，[^，]+",message = "地址格式不正确")
    private String addressName;

    /**
     * 公司详细地址
     */
    @ApiModelProperty(value = "公司详细地址名称", required = true)
    @NotBlank(message = "公司详细地址名称不能为空")
    private String detailAddress;

    /**
     * 备注
     */
    @ApiModelProperty(value = "简介", required = true)
    @NotBlank(message = "简介不能为空")
    private String remarks;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人",required = true)
    @NotBlank(message = "联系人不能为空")
    private String contact;

    @ApiModelProperty(value = "联系电话",required = true)
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    @ApiModelProperty(value = "Excel文件地址",required = false)
    private String excelFile;

    /**
     * 群创建人
     */
    @ApiModelProperty(value = "用户ID",required = true)
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
}
