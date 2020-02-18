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
 * @since 2019-10-29
 */
@Data
@TableName("bif_trustanchor_manage")
@ApiModel(value = "TrustanchorManage对象", description = "TrustanchorManage对象")
public class TrustanchorManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 信任锚地址
     */
    @ApiModelProperty(value = "信任锚地址")
    @TableField("Owner")
    private String Owner;
    /**
     * 信任锚类型
     */
    @ApiModelProperty(value = "信任锚类型")
    @TableField("Type")
    private Integer Type;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @TableField("Name")
    private String Name;
    /**
     * 所属公司
     */
    @ApiModelProperty(value = "所属公司")
    private String company;
    /**
     * 公司网址
     */
    @ApiModelProperty(value = "公司网址")
    @TableField("companyUrl")
    private String companyUrl;
    /**
     * 信任锚地址
     */
    @ApiModelProperty(value = "信任锚地址")
    @TableField("Website")
    private String Website;
    /**
     * 文档链接
     */
    @ApiModelProperty(value = "文档链接")
    @TableField("DocumentUrl")
    private String DocumentUrl;
    /**
     * 服务链接
     */
    @ApiModelProperty(value = "服务链接")
    @TableField("ServerUrl")
    private String ServerUrl;
    /**
     * 服务邮箱
     */
    @ApiModelProperty(value = "服务邮箱")
    private String email;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField("Description")
    private String Description;
    /**
     * 服务状态
     */
    @ApiModelProperty(value = "服务状态")
    @TableField("Status")
    private Integer Status;
    /**
     * 获得票数
     */
    @ApiModelProperty(value = "获得票数")
    @TableField("VoteCount")
    private Integer VoteCount;
    /**
     * 是否为信任锚
     */
    @ApiModelProperty(value = "是否为信任锚")
    @TableField("Active")
    private Integer Active;
    /**
     * 获得的激励数
     */
    @ApiModelProperty(value = "获得的激励数")
    @TableField("TotalBounty")
    private String TotalBounty;
    /**
     * 已提取的激励
     */
    @ApiModelProperty(value = "已提取的激励")
    @TableField("ExtractedBounty")
    private String ExtractedBounty;
    /**
     * 上次提取时间
     */
    @ApiModelProperty(value = "上次提取时间")
    @TableField("LastExtractTime")
    private LocalDateTime LastExtractTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("CreateTime")
    private LocalDateTime CreateTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField("UpdateTime")
    private LocalDateTime UpdateTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "stake")
    @TableField("stake")
    private Integer stake;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "createDate")
    @TableField("createDate")
    private LocalDateTime createDate;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "certificateAccount")
    @TableField("certificateAccount")
    private Integer certificateAccount;
}
