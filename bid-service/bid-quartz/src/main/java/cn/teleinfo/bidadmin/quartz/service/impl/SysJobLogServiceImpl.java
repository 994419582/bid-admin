package cn.teleinfo.bidadmin.quartz.service.impl;

import cn.teleinfo.bidadmin.quartz.entity.SysJobLog;
import cn.teleinfo.bidadmin.quartz.mapper.SysJobLogMapper;
import cn.teleinfo.bidadmin.quartz.service.ISysJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 定时任务调度日志信息 服务层
 * 
 * @author ruoyi
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements ISysJobLogService
{

    /**
     * 获取quartz调度器日志的计划任务
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog,Date beginTime, Date endTime)
    {
        return baseMapper.selectJobLogList(jobLog,beginTime,endTime);
    }



    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog()
    {
        baseMapper.cleanJobLog();
    }
}
