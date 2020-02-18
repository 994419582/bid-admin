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
package cn.teleinfo.bidadmin.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-11-27
 */
@Data
@TableName("app_version")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Version对象", description = "Version对象")
public class Version extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 版本编号
     */
    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

    @ApiModelProperty(value = "版本编号")
    private String versionNum;
    /**
     * 版本标题
     */
    @ApiModelProperty(value = "版本标题")
    private String title;
    /**
     * 更新内容
     */
    @ApiModelProperty(value = "更新内容")
    private String content;


}
