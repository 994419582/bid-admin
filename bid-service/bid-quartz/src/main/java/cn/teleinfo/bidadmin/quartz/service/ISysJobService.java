package cn.teleinfo.bidadmin.quartz.service;

import cn.teleinfo.bidadmin.quartz.entity.SysJob;
import org.springblade.core.mp.base.BaseService;

/**
 * 定时任务调度信息信息 服务层
 * 
 * @author ruoyi
 */
public interface ISysJobService extends BaseService<SysJob>
{

    /**
     * 暂停任务
     * 
     * @param job 调度信息
     * @return 结果
     */
     boolean pauseJob(SysJob job);

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     * @return 结果
     */
     boolean resumeJob(SysJob job);

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     * @return 结果
     */
     boolean deleteJob(SysJob job);

    /**
     * 批量删除调度信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     Integer deleteJobByIds(String ids);

    /**
     * 任务调度状态修改
     * 
     * @param ids 调度信息
     * @return 结果
     */
     boolean changeStatus(String  ids);

    /**
     * 立即运行任务
     * 
     * @param ids 调度信息
     * @return 结果
     */
     int run(String ids);

    /**
     * 新增任务表达式
     * 
     * @param job 调度信息
     * @return 结果
     */
     boolean insertJobCron(SysJob job);

    /**
     * 更新任务的时间表达式
     * 
     * @param job 调度信息
     * @return 结果
     */
     boolean updateJobCron(SysJob job);
    
    /**
     * 校验cron表达式是否有效
     * 
     * @param cronExpression 表达式
     * @return 结果
     */
     boolean checkCronExpressionIsValid(String cronExpression);
}
