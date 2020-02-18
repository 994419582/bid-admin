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
 * 实体类
 *
 * @author Blade
 * @since 2019-10-17
 */
@Data
@TableName("bif_dpos_voter")
@ApiModel(value = "DposVoter对象", description = "DposVoter对象")
public class DposVoter implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "ID", type = IdType.AUTO)
  private Integer id;
    /**
     * 投票人地址
     */
    @ApiModelProperty(value = "投票人地址")
    @TableField("Owner")
  private String owner;
    /**
     * 是否为地址投票
     */
    @ApiModelProperty(value = "是否为地址投票")
    @TableField("IsProxy")
  private Integer isProxy;
    /**
     * 收到的代理票数
     */
    @ApiModelProperty(value = "收到的代理票数")
    @TableField("ProxyVoteCount")
  private Integer proxyVoteCount;
    /**
     * 代理人地址
     */
    @ApiModelProperty(value = "代理人地址")
    @TableField("Proxy")
  private String proxy;
    /**
     * 投的票数
     */
    @ApiModelProperty(value = "投的票数")
    @TableField("LastVoteCount")
  private Integer lastVoteCount;
    /**
     * 投票时间
     */
    @ApiModelProperty(value = "投票时间")
    @TableField("voteTime")
  private LocalDateTime voteTime;


}
