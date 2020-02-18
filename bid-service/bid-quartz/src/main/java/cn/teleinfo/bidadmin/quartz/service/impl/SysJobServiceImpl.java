package cn.teleinfo.bidadmin.quartz.service.impl;

import cn.teleinfo.bidadmin.common.constant.ScheduleConstants;
import cn.teleinfo.bidadmin.common.support.Convert;
import cn.teleinfo.bidadmin.quartz.entity.SysJob;
import cn.teleinfo.bidadmin.quartz.mapper.SysJobMapper;
import cn.teleinfo.bidadmin.quartz.service.ISysJobService;
import cn.teleinfo.bidadmin.quartz.util.CronUtils;
import cn.teleinfo.bidadmin.quartz.util.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 定时任务调度信息 服务层
 * 
 * @author ruoyi
 */
@Service
public class SysJobServiceImpl extends BaseServiceImpl<SysJobMapper, SysJob> implements ISysJobService
{
    @Autowired
    Scheduler scheduler;


    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init()
    {
        List<SysJob> jobList = baseMapper.selectList(null);
        for (SysJob job : jobList)
        {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getId().longValue());
            // 如果不存在，则创建
            if (cronTrigger == null)
            {
                ScheduleUtils.createScheduleJob(scheduler, job);
            }
            else
            {
                ScheduleUtils.updateScheduleJob(scheduler, job);
            }
        }
    }


    /**
     * 暂停任务
     * 
     * @param job 调度信息
     */
    @Override
    public boolean pauseJob(SysJob job)
    {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        boolean flag = updateById(job);
        if (flag == true)
        {
            ScheduleUtils.pauseJob(scheduler, job.getId().longValue());
        }
        return flag;
    }

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     */
    @Override
    public boolean resumeJob(SysJob job)
    {
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        boolean flag = updateById(job);
        if (flag == true)
        {
            ScheduleUtils.resumeJob(scheduler, job.getId().longValue());
        }
        return flag;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     */
    @Override
    public boolean deleteJob(SysJob job)
    {
        boolean flag = removeById(job);
        if (flag == true)
        {
            ScheduleUtils.deleteScheduleJob(scheduler, job.getId().longValue());
        }
        return flag;
    }

    /**
     * 批量删除调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public Integer deleteJobByIds(String ids)
    {
        Integer rows=0;
        Long[] jobIds = Convert.toLongArray(ids);
        for (Long jobId : jobIds)
        {
            SysJob job = getById(jobId);
            if (job!=null) {
                if (deleteJob(job) ==true){
                    rows++;
                }
            }
        }
        return rows;
    }

    /**
     * 任务调度状态修改
     * 
     * @param ids 调度信息
     */
    @Override
    public boolean changeStatus(String  ids)
    {
        boolean flag= false;
        SysJob job=getById(ids);
        if (job !=null) {

            Integer status = job.getStatus();
            if (ScheduleConstants.Status.NORMAL.getValue() == status) {
                flag = pauseJob(job);
            } else if (ScheduleConstants.Status.PAUSE.getValue() == status) {
                flag = resumeJob(job);
            }
        }
        return flag;
    }

    /**
     * 立即运行任务
     * 
     * @param ids 调度信息
     */
    @Override
    public int run(String ids)
    {
        return ScheduleUtils.run(scheduler, getById(ids));
    }

    /**
     * 新增任务
     * 
     * @param job 调度信息 调度信息
     */
    @Override
    public boolean insertJobCron(SysJob job)
    {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        boolean flag= save(job);
        if (flag  == true)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return flag;
    }

    /**
     * 更新任务的时间表达式
     * 
     * @param job 调度信息
     */
    @Override
    public boolean updateJobCron(SysJob job)
    {
        boolean flag= updateById(job);
        if (flag== true)
        {
            ScheduleUtils.updateScheduleJob(scheduler, job);
        }
        return flag;
    }
    
    /**
     * 校验cron表达式是否有效
     * 
     * @param cronExpression 表达式
     * @return 结果
     */
    public boolean checkCronExpressionIsValid(String cronExpression)
    {
        return CronUtils.isValid(cronExpression);
    }   
}
