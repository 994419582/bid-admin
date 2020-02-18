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
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 区块信息表实体类
 *
 * @author Blade
 * @since 2019-10-22
 */
@Data
@TableName("bif_block")
@ApiModel(value = "Block对象", description = "区块信息表")
public class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 区块高度
     */
    @ApiModelProperty(value = "区块高度")
    private Long number;
    /**
     * 区块哈希
     */
    @ApiModelProperty(value = "区块哈希")
    private String hash;
    /**
     * 父哈希
     */
    @ApiModelProperty(value = "父哈希")
    @TableField("parentHash")
  private String parentHash;
    /**
     * 随机值
     */
    @ApiModelProperty(value = "随机值")
    private String nonce;
    /**
     * 叔区块加密值
     */
    @ApiModelProperty(value = "叔区块加密值")
    @TableField("sha3Uncles")
  private String sha3Uncles;
    /**
     * 过滤器
     */
    @ApiModelProperty(value = "过滤器")
    @TableField("logsBloom")
  private String logsBloom;
    /**
     * 交易根哈希
     */
    @ApiModelProperty(value = "交易根哈希")
    @TableField("transactionsRoot")
  private String transactionsRoot;
    /**
     * 状态根哈希
     */
    @ApiModelProperty(value = "状态根哈希")
    @TableField("stateRoot")
  private String stateRoot;
    /**
     * 收据根哈希
     */
    @ApiModelProperty(value = "收据根哈希")
    @TableField("receiptsRoot")
  private String receiptsRoot;
    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;
    /**
     * 矿工地址
     */
    @ApiModelProperty(value = "矿工地址")
    private String miner;
    /**
     * 最大值哈希
     */
    @ApiModelProperty(value = "最大值哈希")
    @TableField("mixHash")
  private String mixHash;
    /**
     * 挖矿难度
     */
    @ApiModelProperty(value = "挖矿难度")
    private String difficulty;
    /**
     * 总难度
     */
    @ApiModelProperty(value = "总难度")
    @TableField("totalDifficulty")
  private String totalDifficulty;
    /**
     * 其他字段
     */
    @ApiModelProperty(value = "其他字段")
    @TableField("extraData")
  private String extraData;
    /**
     * 区块大小
     */
    @ApiModelProperty(value = "区块大小")
    private String size;
    /**
     * gasLimit
     */
    @ApiModelProperty(value = "gasLimit")
    @TableField("gasLimit")
  private String gasLimit;
    /**
     * gasUsed
     */
    @ApiModelProperty(value = "gasUsed")
    @TableField("gasUsed")
  private String gasUsed;
    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private LocalDateTime timestamp;
    @ApiModelProperty(value = "交易数")
    private Integer transactionCount ;


}
