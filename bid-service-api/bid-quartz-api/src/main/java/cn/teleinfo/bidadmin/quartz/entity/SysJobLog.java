package cn.teleinfo.bidadmin.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("qrtz_job_log")
@ApiModel(value = "定时任务日志对象", description = "定时任务日志对象")
public class SysJobLog implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer id;

    /** 任务名称 */
    private String jobName;

    /** 任务组名 */
    private String jobGroup;

    /** 任务方法 */
    private String methodName;

    /** 方法参数 */
    private String methodParams;

    /** 日志信息 */
    private String jobMessage;


    /** 异常信息 */
    private String exceptionInfo;

    private LocalDateTime createTime;

    private Integer status;
}
