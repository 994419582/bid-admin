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
import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-10-22
 */
@Data
@TableName("bif_events")
@ApiModel(value = "Events对象", description = "Events对象")
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;
  /**
   * 主键
   */
  @ApiModelProperty(value = "主键")
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 交易hash
     */
    @ApiModelProperty(value = "交易hash")
    @TableId("transactionHash")
  private String transactionHash;
    /**
     * 交易索引
     */
    @ApiModelProperty(value = "交易索引")
    @TableField("transactionIndex")
  private Integer transactionIndex;
    /**
     * 区块hash
     */
    @ApiModelProperty(value = "区块hash")
    @TableField("blockHash")
  private String blockHash;
    /**
     * 区块高度
     */
    @ApiModelProperty(value = "区块高度")
    @TableField("blockNumber")
  private Integer blockNumber;
    /**
     * gas
     */
    @ApiModelProperty(value = "gas")
    @TableField("cumulativeGasUsed")
  private String cumulativeGasUsed;
    /**
     * gas使用量
     */
    @ApiModelProperty(value = "gas使用量")
    @TableField("gasUsed")
  private String gasUsed;
    /**
     * 合约地址
     */
    @ApiModelProperty(value = "合约地址")
    @TableField("contractAddress")
  private String contractAddress;
    /**
     * 收据根哈希
     */
    @ApiModelProperty(value = "收据根哈希")
    private String root;
    /**
     * 发送者
     */
    @ApiModelProperty(value = "发送者")
    private String txfrom;
    /**
     * 接受者
     */
    @ApiModelProperty(value = "接受者")
    private String txto;
    /**
     * 交易事件
     */
    @ApiModelProperty(value = "交易事件")
    private String events;
    /**
     * log布隆过滤
     */
    @ApiModelProperty(value = "log布隆过滤")
    @TableField("logsBloom")
  private String logsBloom;

  @ApiModelProperty(value = "状态")
  private String status;

}
