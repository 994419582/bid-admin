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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Data
@TableName("soybean_clockln")
@ApiModel(value = "Clockln对象", description = "Clockln对象")
public class Clockln implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键")
    private Integer userId;
    /**
     * 打卡地址
     */
    @ApiModelProperty(value = "打卡地址")
    private String address;
    /**
     * 健康状态
     */
    @ApiModelProperty(value = "健康状态")
    private Integer healthy;
    /**
     * 是否有就诊入院
     */
    @ApiModelProperty(value = "是否有就诊入院")
    private Integer hospital;
    /**
     * 是否接触过武汉人同或经过武汉午
     */
    @ApiModelProperty(value = "是否接触过武汉人或经过武汉")
    private Integer wuhan;
    /**
     * 计划反京时间
     */
    @ApiModelProperty(value = "计划反京时间")
    private String gobacktime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;
    /**
     * 是否在隔离器
     */
    @ApiModelProperty(value = "是否在隔离期")
    private Integer quarantine;
    /**
     * 未返京原因
     */
    @ApiModelProperty(value = "未返京原因")
    private String reason;

    @ApiModelProperty(value = "打卡时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 体温
     */
    @ApiModelProperty(value = "体温")
    private Double temperature;
    /**
     * 未返京原因
     */
    @ApiModelProperty(value = "未返京原因")
    private Integer nobackreason;
    /**
     * 是否确诊
     */
    @ApiModelProperty(value = "是否确诊")
    private Integer comfirmed;
    /**
     * 是否就诊入院
     */
    @ApiModelProperty(value = "是否就诊入院")
    private Integer admitting;
    /**
     * 是否离京
     */
    @ApiModelProperty(value = "14天内是否离开过工作地")
    @TableField("`leave`")
    private Integer leave;
    /**
     * 是否离京
     */
    @ApiModelProperty(value = "近14天是否离过打卡城市")
    @TableField("`leave_city`")
    private Integer leaveCity;
    /**
     * 离京时间
     */
    @ApiModelProperty(value = "到达时间")
    private String leavetime;

    /**
     * 返京航班/车次
     */
    @ApiModelProperty(value = "返京航班/车次")
    private String flight;
    /**
     * 健康状况其他情况
     */
    @ApiModelProperty(value = "健康状况其他情况")
    private String otherhealthy;
    /**
     * 是否到过湖北或途经湖北
     */
    @ApiModelProperty(value = "是否到过湖北或途经湖北")
    private Integer hubei;
    /**
     * 现在是否在北京
     */
    @ApiModelProperty(value = "现在是否在北京")
    private Integer beijing;
    /**
     * 交通方式
     */
    @ApiModelProperty(value = "交通方式")
    private Integer transport;
    /**
     * 在岗状态
     */
    @ApiModelProperty(value = "在岗状态")
    private Integer jobstatus;

    @ApiModelProperty(value = "共同居住人员亲属（含合租人员）健康状况")
    private Integer roomPerson;
    @ApiModelProperty(value = "其他症状，共同居住人员亲属（含合租人员）健康状况")
    private String roomPersonOther;

    @ApiModelProperty(value = "共同居住人员亲属（含合租人员）所在单位/公司是否有疑似病例、确诊病例")
    private Integer roomCompany;
    @ApiModelProperty(value = "其他，共同居住人员亲属（含合租人员）所在单位/公司是否有疑似病例、确诊病例")
    private String roomCompanyOther;

    @ApiModelProperty(value = "居住小区是否有疑似病例、确诊病例")
    private Integer neighbor;
    @ApiModelProperty(value = "其他，居住小区是否有疑似病例、确诊病例")
    private String neighborOther;

    @ApiModelProperty(value = "城市")
    private String city;
}
