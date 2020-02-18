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
package cn.teleinfo.bidadmin.blockchain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-10-16
 */
@Data
@TableName("bif_company")
@ApiModel(value = "Company对象", description = "Company对象")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private String companyName;
    /**
     * 营业年限
     */
    @ApiModelProperty(value = "营业年限")
    private Integer businessYear;
    /**
     * 公司规模
     */
    @ApiModelProperty(value = "公司规模")
    private Integer companyPerson;
    /**
     * 主要行业
     */
    @ApiModelProperty(value = "主要行业")
    private Integer industriy;
    /**
     * 主营业务
     */
    @ApiModelProperty(value = "主营业务")
    private String work;
    /**
     * 公司网址
     */
    @ApiModelProperty(value = "公司网址")
    @TableField("companyUrl")
    private String companyUrl;
    /**
     * 国家/地区
     */
    @ApiModelProperty(value = "国家/地区")
    private Integer country;
    /**
     * 地区
     */
    @ApiModelProperty(value = "地区")
    private Integer region;
    /**
     * 街道地址
     */
    @ApiModelProperty(value = "街道地址")
    private String address;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;
    /**
     * 营业执照
     */
    @ApiModelProperty(value = "营业执照")
    private String businessLicense;
    /**
     * 统一社会信用代码
     */
    @ApiModelProperty(value = "统一社会信用代码")
    private String number;
    /**
     * 营业期至
     */
    @ApiModelProperty(value = "营业期至")
    private LocalDateTime businessDate;
    /**
     * 信任锚BID
     */
    @ApiModelProperty(value = "信任锚BID")
    private String bid;
    /**
     * 信任锚名称
     */
    @ApiModelProperty(value = "信任锚名称")
    private String trustName;
    /**
     * 信任锚网址
     */
    @ApiModelProperty(value = "信任锚网址")
    private String website;
    /**
     * 服务文档链接
     */
    @ApiModelProperty(value = "服务文档链接")
    @TableField("documentUrl")
    private String documentUrl;
    /**
     * 服务链接
     */
    @ApiModelProperty(value = "服务链接")
    @TableField("serverUrl")
    private String serverUrl;
    /**
     * 服务邮箱
     */
    @ApiModelProperty(value = "服务邮箱")
    private String email;
    /**
     * 信任描述
     */
    @ApiModelProperty(value = "信任描述")
    private String description;

    /**
     * 信任描述
     */
    @ApiModelProperty(value = "是否删除")
    private String isDeleted;
    /**
     * 信任描述
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
