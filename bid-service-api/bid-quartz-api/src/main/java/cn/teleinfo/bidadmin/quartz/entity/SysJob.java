package cn.teleinfo.bidadmin.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("qrtz_job")
@EqualsAndHashCode(callSuper = true)
public class SysJob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称")
    private String jobName;

    /** 任务组名 */
    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    /** 任务方法 */
    @ApiModelProperty(value = "任务方法")
    private String methodName;

    /** 方法参数 */
    @ApiModelProperty(value = "方法参数")
    private String methodParams;

    /** cron执行表达式 */
    @ApiModelProperty(value = "cron执行表达式")
    private String cronExpression;

    /** cron计划策略 */
    @ApiModelProperty(value = "cron计划策略")
    private String misfirePolicy ;

}
