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
package cn.teleinfo.bidadmin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-10-16
 */
@Data
@TableName("sys_area")
@ApiModel(value = "Area对象", description = "Area对象")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private Integer pid;
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称")
    private String shortName;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 全称
     */
    @ApiModelProperty(value = "全称")
    private String mergerName;
    /**
     * 层级 0 1 2 省市区县
     */
    @ApiModelProperty(value = "层级 0 1 2 省市区县")
    private Integer level;
    /**
     * 拼音
     */
    @ApiModelProperty(value = "拼音")
    private String pinyin;
    /**
     * 长途区号
     */
    @ApiModelProperty(value = "长途区号")
    private String phoneCode;
    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编")
    private String zipCode;
    /**
     * 首字母
     */
    @ApiModelProperty(value = "首字母")
    private String first;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String lng;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String lat;
    /**
     * 地区编号
     */
    @ApiModelProperty(value = "地区编号")
    private String areaCode;

  private  Integer isDeleted;
}
