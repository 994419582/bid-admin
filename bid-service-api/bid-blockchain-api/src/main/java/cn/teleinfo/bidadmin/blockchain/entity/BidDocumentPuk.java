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
 * @since 2019-10-17
 */
@Data
@ApiModel(value = "BidDocumentPuk对象", description = "BidDocumentPuk对象")
public class BidDocumentPuk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
  private String id;
    /**
     * 拥有者
     */
    @ApiModelProperty(value = "拥有者")
    private String owner;
    /**
     * 公钥类型
     */
    @ApiModelProperty(value = "公钥类型")
    @TableField("Type")
  private String Type;
    /**
     * 控制者
     */
    @ApiModelProperty(value = "控制者")
    @TableField("Controller")
  private String Controller;
    /**
     * 授权范围
     */
    @ApiModelProperty(value = "授权范围")
    @TableField("Authority")
  private String Authority;
    /**
     * 公钥
     */
    @ApiModelProperty(value = "公钥")
    @TableField("PublicKey")
  private String PublicKey;


}
