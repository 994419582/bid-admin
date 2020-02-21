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

import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Data
@TableName("soybean_quarantine_trip")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "QuarantineTrip对象", description = "QuarantineTrip对象")
public class QuarantineTrip extends BaseEntity {

    private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private Integer userId;
    /**
     * 隔离
     */
    @ApiModelProperty(value = "隔离")
    private Integer quarantineId;
    /**
     * 返程出发日期
     */
    @ApiModelProperty(value = "返程出发日期")
    private String gobackAddress;
    /**
     * 返程时间
     */
    @ApiModelProperty(value = "返程时间")
    private LocalDateTime gobackTime;
    /**
     * 交通方式
     */
    @ApiModelProperty(value = "交通方式")
    private Integer transport;
    /**
     * 航班/车次
     */
    @ApiModelProperty(value = "航班/车次")
    private String flight;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;


}
