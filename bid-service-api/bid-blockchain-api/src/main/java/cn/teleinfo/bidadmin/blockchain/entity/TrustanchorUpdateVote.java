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
 * @since 2019-10-29
 */
@Data
@TableName("bif_trustanchor_update_vote")
@ApiModel(value = "TrustanchorUpdateVote对象", description = "TrustanchorUpdateVote对象")
public class TrustanchorUpdateVote implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "ID", type = IdType.AUTO)
  private Integer id;
    /**
     * 提议ID
     */
    @ApiModelProperty(value = "提议ID")
    @TableField("ProposeId")
  private String ProposeId;
    /**
     * 投票人地址
     */
    @ApiModelProperty(value = "投票人地址")
    private String votebid;
    /**
     * 有效性
     */
    @ApiModelProperty(value = "有效性")
    private Integer validity;
    /**
     * 投票时间
     */
    @ApiModelProperty(value = "投票时间")
    @TableField("voteTime")
  private LocalDateTime voteTime;


}
