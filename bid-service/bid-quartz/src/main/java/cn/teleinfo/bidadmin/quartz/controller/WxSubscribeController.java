package cn.teleinfo.bidadmin.quartz.controller;

import cn.teleinfo.bidadmin.common.cache.CacheNames;
import cn.teleinfo.bidadmin.common.constant.ScheduleConstants;
import cn.teleinfo.bidadmin.quartz.entity.SysJob;
import cn.teleinfo.bidadmin.quartz.service.ISysJobService;
import cn.teleinfo.bidadmin.quartz.vo.SysJobVO;
import cn.teleinfo.bidadmin.quartz.wrapper.SysJobWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 控制器
 *
 * @author Chill
 * @since 2018-09-29
 */
@RestController
@RequestMapping("/subscribe")
@AllArgsConstructor
@Api(value = "微信订阅消息定时任务", tags = "微信订阅消息定时任务接口")
public class WxSubscribeController extends BladeController implements CacheNames {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ?");
    private ISysJobService jobService;
//    /**
//     * 详情
//     */
//    @GetMapping("/detail")
//    @ApiOperationSupport(order = 1)
//    @ApiOperation(value = "详情", notes = "传入sysJobLog")
//    public R<SysJob> detail(SysJob sysJob) {
//        SysJob detail = jobService.getOne(Condition.getQueryWrapper(sysJob));
//        return R.data(SysJobWrapper.build().entityVO(detail));
//    }
//
//    /**
//     * 分页
//     */
//    @GetMapping("/list")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "jobName", value = "任务名称", paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "status", value = "任务执行状态", paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "methodName", value = "方法名", paramType = "query", dataType = "string")
//    })
//    @ApiOperationSupport(order = 2)
//    @ApiOperation(value = "分页", notes = "传入SysJob")
//    public R<IPage<SysJobVO>> list(@ApiIgnore @RequestParam Map<String, Object> SysJob, Query query) {
//
//        IPage<SysJob> pages = jobService.page(Condition.getPage(query), Condition.getQueryWrapper(SysJob, SysJob.class));
//        return R.data(SysJobWrapper.build().pageVO(pages));
//    }


    /**
     * 设置群组提醒
     */
    @PostMapping("/group")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "群组ID", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "sendDate", value = "消息发送时间，格式：2020-02-02 02:02:02", paramType = "query", required = true)
    })
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "设置群组提醒，提醒的时候判断用户当天是否打卡", notes = "设置群组提醒，提醒的时候判断用户当天是否打卡")
    public R submit(@RequestParam(name = "groupId", required = true) Integer groupId, @RequestParam(name = "sendDate", required = true) @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss") Date sendDate) {
        SysJob sysJob = new SysJob();
        sysJob.setMisfirePolicy(ScheduleConstants.MISFIRE_FIRE_AND_PROCEED);
        sysJob.setJobName("wxSubscribeTask");
        sysJob.setJobGroup("soybean-WxSubscribe");
        sysJob.setMethodName("sendGroup");
        sysJob.setMethodParams(groupId+"");
        sysJob.setCronExpression(sdf.format(sendDate));
        return R.data(jobService.insertJobCron(sysJob));
    }

    /**
     * 设置个人提醒
     */
    @PostMapping("/send")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openIds", value = "用户openId，如果有多个用户,中间用英文逗号,隔开", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sendDate", value = "消息发送时间，格式：2020-02-02 02:02:02", paramType = "query", required = true)
    })
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "设置个人提醒，不会再判断用户当天是否打卡", notes = "设置个人提醒，不会再判断用户当天是否打卡")
    public R submit(@RequestParam(defaultValue = "", required = true)String openIds, @RequestParam(name = "sendDate", required = true) @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss") Date sendDate) {
        SysJob sysJob = new SysJob();
        sysJob.setMisfirePolicy(ScheduleConstants.MISFIRE_FIRE_AND_PROCEED);
        sysJob.setJobName("wxSubscribeTask");
        sysJob.setJobGroup("soybean-WxSubscribe");
        sysJob.setMethodName("sendBatch");
        sysJob.setMethodParams(openIds);
        sysJob.setCronExpression(sdf.format(sendDate));
        return R.data(jobService.insertJobCron(sysJob));
    }

//    /**
//     * 修改
//     */
//    @PostMapping("/update")
//    @ApiOperationSupport(order = 4)
//    @ApiOperation(value = "修改", notes = "传入SysJob")
//    public R update(@RequestBody SysJob sysJob) {
//        return R.data(jobService.updateJobCron(sysJob));
//    }
//
//
//    /**
//     * 删除
//     */
//    @PostMapping("/remove")
//    @ApiOperationSupport(order = 6)
//    @ApiOperation(value = "逻辑删除", notes = "传入SysJob")
//    public R remove(@ApiParam(value = "主键集合") @RequestParam String ids) {
//        Integer temp = jobService.deleteJobByIds(ids);
//        return R.data(temp);
//    }
//
//
//    /**R
//     * 修改任务状态运行
//     */
//    @PostMapping("/changeStatus")
//    @ApiOperationSupport(order = 7)
//    @ApiOperation(value = "修改任务状态", notes = "传入SysJob")
//    public R changeStatus(@ApiParam(value = "主键集合") @RequestParam String ids)
//    {
//        return R.data(jobService.changeStatus(ids));
//    }
//
//    /**
//     * 运行
//     */
//    @PostMapping("/run")
//    @ApiOperationSupport(order = 8)
//    @ApiOperation(value = "运行一次定时任务", notes = "传入SysJob")
//    public R run(@ApiParam(value = "主键集合") @RequestParam String ids)
//    {
//        return R.data(jobService.run(ids));
//    }
//
//
//    /**
//     * 校验cron表达式是否有效
//     */
//    @PostMapping("/checkCronExpressionIsValid")
//    @ApiOperationSupport(order = 9)
//    @ApiOperation(value = "检查cron表达式", notes = "传入SysJob")
//    public R checkCronExpressionIsValid(SysJob job)
//    {
//        return R.data(jobService.checkCronExpressionIsValid(job.getCronExpression()));
//    }
}
