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
@TableName("bif_dpos_candidate")
@ApiModel(value = "DposCandidate对象", description = "DposCandidate对象")
public class DposCandidate implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "ID",type = IdType.AUTO)
  private Integer id;
    /**
     * 候选人BID
     */
    @ApiModelProperty(value = "候选人BID")
    @TableField("Owner")
  private String owner;
    /**
     * 获投票数
     */
    @ApiModelProperty(value = "获投票数")
    @TableField("VoteCount")
  private Integer voteCount;
    /**
     * 是否激活
     */
    @ApiModelProperty(value = "是否激活")
    @TableField("Active")
  private Integer active;
    /**
     * 超级节点URL
     */
    @ApiModelProperty(value = "超级节点URL")
    @TableField("Url")
  private String url;
    /**
     * 超级节点IP
     */
    @ApiModelProperty(value = "超级节点IP")
    @TableField("IP")
  private String ip;
    /**
     * 超级节点位置
     */
    @ApiModelProperty(value = "超级节点位置")
    private String location;
    /**
     * 获得的总激励数
     */
    @ApiModelProperty(value = "获得的总激励数")
    @TableField("TotalBounty")
  private String totalBounty;
    /**
     * 已提取激励数
     */
    @ApiModelProperty(value = "已提取激励数")
    @TableField("ExtractedBounty")
  private String extractedBounty;
    /**
     * 最后提取时间
     */
    @ApiModelProperty(value = "最后提取时间")
    @TableField("LastExtractTime")
  private LocalDateTime lastExtractTime;
    /**
     * 网址
     */
    @ApiModelProperty(value = "网址")
    @TableField("Website")
  private String website;
    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    @TableField("Name")
  private String name;


}
