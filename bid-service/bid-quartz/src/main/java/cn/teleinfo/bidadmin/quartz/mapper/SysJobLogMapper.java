package cn.teleinfo.bidadmin.quartz.mapper;


import cn.teleinfo.bidadmin.quartz.entity.SysJobLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * 调度任务日志信息 数据层
 * 
 * @author ruoyi
 */
public interface SysJobLogMapper extends BaseMapper<SysJobLog>
{
    /**
     * 获取quartz调度器日志的计划任务
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog, Date beginTime,Date endTime);

    /**
     * 清空任务日志
     */
    public void cleanJobLog();
}
