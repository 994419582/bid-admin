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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.models.auth.In;
import org.springblade.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知公告表实体类
 *
 * @author Blade
 * @since 2020-03-25
 */
@Data
@TableName("soybean_notice")
@ApiModel(value = "Notice对象", description = "通知公告表")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * STATUS_UNREAD 未读
     * STATUS_READ 已读
     */
    public static final Integer STATUS_UNREAD = 0;
    public static final Integer STATUS_READ = 1;

    /**
     * CATEGORY_TRANSFER_GROUP 转让机构
     */
    public static final Integer CATEGORY_TRANSFER_GROUP = 0;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型 转让机构0")
    private Integer category;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 消息接收人ID
     */
    @ApiModelProperty(value = "消息接收人ID")
    private Integer userId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "通知状态 未读0 已读1")
    private Integer status;
}
