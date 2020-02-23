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
package cn.teleinfo.bidadmin.soybean.wxfront;

import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import cn.teleinfo.bidadmin.soybean.wrapper.ClocklnWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 *  控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/clockln/census/")
@Api(value = "统计界面接口", tags = "统计界面接口")
public class WxClocklnCensusController extends BladeController {

	private IClocklnService clocklnService;

	private IGroupService groupService;



	/**
	* 获取群组分页打卡信息
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "群打卡信息分页", notes = "传入群ID和打卡日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupId", value = "群组ID", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "clockInTime", value = "打卡日期", paramType = "query")
	})
	public R<IPage<ClocklnVO>> list(@RequestParam(name = "groupId") Integer groupId, @RequestParam("clockInTime") Date clocklnTime, Query query) {
		if (groupId == null){
			return R.fail("群组ID不能为空");
		}
		Group group= groupService.getById(groupId);
		if (group==null){
			return R.fail("该群组不存在,请输入正确的群组ID");
		}
		IPage<ClocklnVO> pages = clocklnService.selectClocklnPageByGroup(Condition.getPage(query),groupId,clocklnTime);
		return R.data(pages);
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/census")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取统计页面所需统计数据", notes = "传入群ID和打卡日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupId", value = "群组ID", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "clockInTime", value = "打卡日期", paramType = "query")
	})
	public R census(@RequestParam(name = "groupId") Integer groupId, @RequestParam("clockInTime") Date clocklnTime) {
		DecimalFormat df = new DecimalFormat("#.00");
		if (groupId == null){
			return R.fail("群组ID不能为空");
		}
		Group group= groupService.getById(groupId);
		if (group==null){
			return R.fail("该群组不存在,请输入正确的群组ID");
		}
		List<ClocklnVO> list = clocklnService.selectClocklnByGroup(groupId,clocklnTime);

		StringBuffer buffer=new StringBuffer("{");
		//写入总体统计数据
		buffer.append("\"totality\":{\"total\":"+group.getUserAccount()+",\"clockIn\":"+list.size()+",\"unClockIn\":"+(group.getUserAccount()-list.size())+"},");

		double healthy=0;
		double healthyPer=0;
		double ferver=0;
		double ferverPer=0;
		double other=0;
		double otherPer=0;

		double beijing=0;
		double beijingPer=0;
		double hubei=0;
		double hubeiPer=0;
		double otherRegion=0;
		double otherRegionPer=0;

		for (ClocklnVO c:list ) {
			if (c.getHealthy() != null && c.getHealthy()==1){
				healthy++;
			}else if(c.getHealthy()!= null && c.getHealthy()==2){
				ferver++;
			}else {
				other++;
			}

			if (StringUtil.isEmpty(c.getAddress()) && c.getAddress().contains("北京")){
				beijing++;
			}else if(StringUtil.isEmpty(c.getAddress()) && c.getAddress().contains("湖北")){
				hubei++;
			}else {
				otherRegion++;
			}
		}
		if (list.size()>0) {
			healthyPer = healthy / list.size() * 100;
			ferverPer = healthy / list.size() * 100;
			otherPer = healthy / list.size() * 100;
			beijing = healthy / list.size() * 100;
			hubeiPer = healthy / list.size() * 100;
			otherRegionPer = healthy / list.size() * 100;

		}


		//计算并写入第一张饼图数据
		buffer.append("\"healthy\":{[\"name\":\"健康\",\"value\":"+ healthy+",\"percent\":"+df.format(healthyPer)+"]," +
				"[\"name\":\"发烧，咳嗽\",\"value\":"+ferver+",\"percent\":"+df.format(ferverPer)+"]," +
				"[\"name\":\"其他症状\",\"value\":"+other+",\"percent\":"+df.format(otherPer)+"]},");

		//计算并写入第二张饼图数据
		buffer.append("\"region\":{[\"name\":\"北京\",\"value\":"+ beijing+",\"percent\":"+df.format(+beijingPer)+"]," +
				"[\"name\":\"湖北\",\"value\":"+hubei+",\"percent\":"+df.format(hubeiPer)+"]," +
				"[\"name\":\"其他地区\",\"value\":"+otherRegion+",\"percent\":"+df.format(otherRegionPer)+"]}}");


		return R.data(buffer.toString());
	}

	
}
