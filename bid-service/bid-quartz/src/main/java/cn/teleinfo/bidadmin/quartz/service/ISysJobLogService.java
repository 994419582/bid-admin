package cn.teleinfo.bidadmin.quartz.service;


import cn.teleinfo.bidadmin.quartz.entity.SysJobLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * 定时任务调度日志信息信息 服务层
 * 
 * @author ruoyi
 */
public interface ISysJobLogService extends IService<SysJobLog>
{
    /**
     * 获取quartz调度器日志的计划任务
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog , Date beginTime, Date endTime);

    /**
     * 清空任务日志
     */
    public void cleanJobLog();
}
