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
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-10-09
 */
@Data
@TableName("sys_resource")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Resource对象", description = "Resource对象")
public class Resource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String name;
    /**
     * 文件hash值
     */
    @ApiModelProperty(value = "文件hash值")
    private String hash;
    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private Long size;
    /**
     * 文件最大大小
     */
    @ApiModelProperty(value = "文件最大大小")
    @TableField("large_Size")
  private Long largeSize;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String type;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("REMARKS")
  private String remarks;


}
