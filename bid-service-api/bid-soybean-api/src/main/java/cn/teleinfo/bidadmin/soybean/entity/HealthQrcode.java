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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-03-05
 */
@Data
@TableName("soybean_health_qrcode")
@ApiModel(value = "HealthQrcode对象", description = "HealthQrcode对象")
public class HealthQrcode implements Serializable {

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
     * 是否离京
     */
    @ApiModelProperty(value = "是否离京")
    private Boolean isLeave;
    /**
     * 返京日期
     */
    @ApiModelProperty(value = "返京日期")
    private LocalDate returnDate;
    /**
     * 打卡地点
     */
    @ApiModelProperty(value = "打卡地点")
    private String currentAddress;
    /**
     * 近14天是否接触过病例
     */
    @ApiModelProperty(value = "近14天是否接触过病例")
    private Boolean isTouchCase;
    /**
     * 当前健康状况
     */
    @ApiModelProperty(value = "当前健康状况")
    private String currentHealth;
    /**
     * 生成日期
     */
    @ApiModelProperty(value = "生成日期")
    private LocalDate recordDate;
    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "生成时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
