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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 交易信息表实体类
 *
 * @author Blade
 * @since 2019-10-22
 */
@Data
@TableName("bif_transcations")
@ApiModel(value = "Transcations对象", description = "交易信息表")
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 交易哈希
     */
    @ApiModelProperty(value = "交易哈希")
    private String hash;
    /**
     * 交易nonce
     */
    @ApiModelProperty(value = "交易nonce")
    private String nonce;
    /**
     * 区块哈希
     */
    @ApiModelProperty(value = "区块哈希")
    @TableField("blockHash")
  private String blockHash;
    /**
     * 区块高度
     */
    @ApiModelProperty(value = "区块高度")
    @TableField("blockNumber")
  private Long blockNumber;
    /**
     * 交易索引
     */
    @ApiModelProperty(value = "交易索引")
    @TableField("transactionIndex")
  private Integer transactionIndex;
    /**
     * 发送人
     */
    @ApiModelProperty(value = "发送人")
    private String txfrom;
    /**
     * 接收人
     */
    @ApiModelProperty(value = "接收人")
    private String txto;
    /**
     * 交易值
     */
    @ApiModelProperty(value = "交易值")
    private String value;
    /**
     * gasPrice
     */
    @ApiModelProperty(value = "gasPrice")
    @TableField("gasPrice")
  private String gasPrice;
    /**
     * gas
     */
    @ApiModelProperty(value = "gas")
    private String gas;
    /**
     * 输入参数
     */
    @ApiModelProperty(value = "输入参数")
    private String input;
    /**
     * 交易创建时间
     */
    @ApiModelProperty(value = "交易创建时间")
    private String creates;
    /**
     * 用户公钥
     */
    @ApiModelProperty(value = "用户公钥")
    @TableField("publicKey")
  private String publicKey;
    /**
     * 其他字段
     */
    @ApiModelProperty(value = "其他字段")
    private String raw;
    /**
     * 签名字段r
     */
    @ApiModelProperty(value = "签名字段r")
    private String r;
    /**
     * 签名字段s
     */
    @ApiModelProperty(value = "签名字段s")
    private String s;
    /**
     * 签名字段v
     */
    @ApiModelProperty(value = "签名字段v")
    private String v;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @TableField("createDate")
  private LocalDateTime createDate;


}
