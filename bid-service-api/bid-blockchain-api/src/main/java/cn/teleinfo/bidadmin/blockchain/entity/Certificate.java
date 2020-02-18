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
 * @since 2019-12-09
 */
@Data
@TableName("bid_certificate")
@ApiModel(value = "Certificate对象", description = "Certificate对象")
public class Certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 证书ID
     */
    @ApiModelProperty(value = "证书ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("proofId")
    private String proofId;
    /**
     * 拥有者
     */
    @ApiModelProperty(value = "拥有者")
    private String owner;
    /**
     * 颁发者
     */
    @ApiModelProperty(value = "颁发者")
    private String issuer;
    /**
     * 拥有者姓名
     */
    @ApiModelProperty(value = "拥有者姓名")
    @TableField("ownerName")
    private String ownerName;
    /**
     * 拥有者身份证号
     */
    @ApiModelProperty(value = "拥有者身份证号")
    @TableField("ownerIDCard")
    private String ownerIDCard;
    @TableField("IssuedTime")
    private LocalDateTime IssuedTime;
    /**
     * 有效期
     */
    @ApiModelProperty(value = "有效期")
    @TableField("Period")
    private Integer Period;
    @TableField("IsEnable")
    private Integer IsEnable;
}
