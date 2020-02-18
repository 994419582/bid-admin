package cn.teleinfo.bidadmin.quartz.controller;

import cn.teleinfo.bidadmin.common.cache.CacheNames;
import cn.teleinfo.bidadmin.quartz.entity.SysJobLog;
import cn.teleinfo.bidadmin.quartz.service.ISysJobLogService;
import cn.teleinfo.bidadmin.quartz.vo.SysJobLogVO;
import cn.teleinfo.bidadmin.quartz.wrapper.SysJobLogWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 * @since 2018-09-29
 */
@RestController
@RequestMapping("joblog")
@AllArgsConstructor
@Api(value = "定时任务执行日志", tags = "定时任务执行日志接口")
public class SysJobLogController extends BladeController implements CacheNames {

	private ISysJobLogService sysJobLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入sysJobLog")
	public R<SysJobLogVO> detail(SysJobLog sysJobLog) {
		SysJobLog detail = sysJobLogService.getOne(Condition.getQueryWrapper(sysJobLog));
		return R.data(SysJobLogWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "jobName", value = "任务名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "status", value = "任务执行状态", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "methodName", value = "方法名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入SysJobLog")
	public R<IPage<SysJobLogVO>> list(@ApiIgnore @RequestParam Map<String, Object> SysJobLog, Query query) {
		IPage<SysJobLog> pages = sysJobLogService.page(Condition.getPage(query), Condition.getQueryWrapper(SysJobLog, SysJobLog.class));
		return R.data(SysJobLogWrapper.build().pageVO(pages));
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入SysJobLog")
	public R save(@RequestBody SysJobLog SysJobLog) {
		return R.status(sysJobLogService.save(SysJobLog));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入SysJobLog")
	public R update(@RequestBody SysJobLog SysJobLog) {
		return R.status(sysJobLogService.updateById(SysJobLog));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入SysJobLog")
	public R submit(@RequestBody SysJobLog SysJobLog) {
		return R.status(sysJobLogService.saveOrUpdate(SysJobLog));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入SysJobLog")
	public R remove(@ApiParam(value = "主键集合") @RequestParam String ids) {
		boolean temp = sysJobLogService.removeByIds(Func.toIntList(ids));
		return R.status(temp);
	}

}
