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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Data
@TableName("soybean_quarantine")
@ApiModel(value = "Quarantine对象", description = "Quarantine对象")
public class Quarantine implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer userId;
    /**
     * 是否从其他城市返回
     */
    @ApiModelProperty(value = "是否从其他城市返回")
    private Integer otherCity;
    /**
     * 开始观察时间
     */
    @ApiModelProperty(value = "开始观察时间")
    private LocalDateTime startTime;
    /**
     * 当前地点
     */
    @ApiModelProperty(value = "当前地点")
    private String address;
    /**
     * 体温
     */
    @ApiModelProperty(value = "体温")
    private Integer temperature;
    /**
     * 发热
     */
    @ApiModelProperty(value = "发热")
    private Integer fever;
    /**
     * 乏力
     */
    @ApiModelProperty(value = "乏力")
    private Integer fatigue;
    /**
     * 干咳
     */
    @ApiModelProperty(value = "干咳")
    private Integer hoose;
    /**
     * 呼吸困难
     */
    @ApiModelProperty(value = "呼吸困难")
    private Integer dyspnea;
    /**
     * 腹泻
     */
    @ApiModelProperty(value = "腹泻")
    private Integer diarrhea;
    /**
     * 肌肉酸疼
     */
    @ApiModelProperty(value = "肌肉酸疼")
    private Integer muscle;
    /**
     * 其他不适症状
     */
    @ApiModelProperty(value = "其他不适症状")
    private String other;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;


}
