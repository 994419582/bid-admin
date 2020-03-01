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
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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

	private IUserService userService;

	/**
	 * 获取群组分页打卡信息
	 */
	@GetMapping("/hospitalization")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "群打卡信息分页", notes = "传入群ID和打卡日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupId", value = "群组ID", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "clockInTime", value = "打卡日期", paramType = "query"),
			@ApiImplicitParam(name = "hospitalization", value = "健康参数:1确诊，2隔离，3，出隔离，4其他", paramType = "query")
	})
	public R<IPage<ClocklnVO>> hospitalization(@RequestParam(name = "groupId") Integer groupId, @RequestParam("clockInTime") @DateTimeFormat(pattern ="yyyy-MM-dd")Date clocklnTime,@RequestParam("hospitalization") Integer hospitalization, Query query) {
		List<Integer> ids=groupService.selectUserIdByParentId(groupId);
		if (ids.size()>0) {
			IPage<ClocklnVO> pages = clocklnService.selectClocklnPageByGroup(Condition.getPage(query), ids, clocklnTime, null, null, hospitalization);
			return R.data(pages);
		}
		return R.data(null);
	}

	/**
	 * 获取群组分页打卡信息
	 */
	@GetMapping("/region")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "群打卡信息分页", notes = "传入群ID和打卡日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupId", value = "群组ID", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "clockInTime", value = "打卡日期", paramType = "query"),
			@ApiImplicitParam(name = "region", value = "健康参数:1武汉，2湖北，3，北京，4其他", paramType = "query")
	})
		public R<IPage<ClocklnVO>> region(@RequestParam(name = "groupId") Integer groupId, @RequestParam("clockInTime") @DateTimeFormat(pattern ="yyyy-MM-dd")Date clocklnTime,@RequestParam("region") Integer region, Query query) {
		List<Integer> ids=groupService.selectUserIdByParentId(groupId);
		if (ids.size()>0) {
			IPage<ClocklnVO> pages = clocklnService.selectClocklnPageByGroup(Condition.getPage(query), ids, clocklnTime, null, region, null);
			return R.data(pages);
		}
		return R.data(null);
	}

	/**
	 * 获取群组分页打卡信息
	 */
	@GetMapping("/healthy")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "群打卡信息分页", notes = "传入群ID和打卡日期")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupId", value = "群组ID", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "clockInTime", value = "打卡日期", paramType = "query"),
			@ApiImplicitParam(name = "healthy", value = "健康参数:1健康，2有发烧、咳嗽等症状，0其他", paramType = "query")
	})
	public R<IPage<ClocklnVO>> healthy(@RequestParam(name = "groupId") Integer groupId, @RequestParam("clockInTime") @DateTimeFormat(pattern ="yyyy-MM-dd")Date clocklnTime,@RequestParam("healthy") Integer healthy, Query query) {
		List<Integer> ids=groupService.selectUserIdByParentId(groupId);
		if (ids.size()>0) {
			IPage<ClocklnVO> pages = clocklnService.selectClocklnPageByGroup(Condition.getPage(query), ids, clocklnTime, healthy, null, null);
			return R.data(pages);
		}
		return R.data(null);
	}
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
	public R<IPage<UserVO>> list(@RequestParam(name = "groupId") Integer groupId, @RequestParam("clockInTime") @DateTimeFormat(pattern ="yyyy-MM-dd")Date clocklnTime, Query query) {
		if (groupId == null){
			return R.fail("群组ID不能为空");
		}
		Group group= groupService.getById(groupId);
		if (group==null){
			return R.fail("该群组不存在,请输入正确的群组ID");
		}
		IPage<UserVO> users=groupService.selectUserPageByParentId(groupId,Condition.getPage(query));
		users.getRecords().forEach(x ->{
			Clockln clockln=clocklnService.selectClocklnByUserID(x.getId(),clocklnTime);
			if (clockln !=null) {
				x.setClockInId(clockln.getId());
				x.setHealthy(clockln.getHealthy());
				x.setAdmitting(clockln.getAdmitting());
				x.setComfirmed(clockln.getComfirmed());
			}else {
				x.setClockInId(0);
				x.setHealthy(0);
				x.setComfirmed(0);
				x.setAdmitting(0);
			}
		});
		return R.data(users);
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
	public String  census(@RequestParam(name = "groupId") Integer groupId, @RequestParam("clockInTime") @DateTimeFormat(pattern ="yyyy-MM-dd") Date clocklnTime) {
		String pattern ="\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}";
		LocalDate today = LocalDate.now();

		if (groupId == null){
			return ("群组ID不能为空");
		}
		Group group= groupService.getById(groupId);
		if (group==null){
			return ("该群组不存在,请输入正确的群组ID");
		}
		List<Integer> ids=groupService.selectUserIdByParentId(groupId);
		List<Clockln> list =new ArrayList<>();
		if (ids.size() >0){
			list=clocklnService.selectClocklnByGroup(ids,clocklnTime);
		}


		double healthy=0.0;
		double healthyPer=0.0;
		double ferver=0.0;
		double ferverPer=0.0;
		double other=0.0;
		double otherPer=0.0;

		double beijing=0.0;
		double beijingPer=0.0;
		double hubei=0.0;
		double hubeiPer=0.0;
		double wuhan=0.0;
		double wuhanPer=0.0;
		double otherRegion=0.0;
		double otherRegionPer=0.0;

		double isolator = 0.0;
		double diagnosis = 0.0;
		double outisolator=0.0;

		double isolatorPer = 0.0;
		double diagnosisPer = 0.0;
		double outisolatorPer=0.0;
		double otherIsolatorPer=0.0;

		int gobackBeijing=0;

		for (Clockln c:list ) {

			if (c.getComfirmed() !=null && c.getComfirmed()==2){
				diagnosis++;
			}
			if (!StringUtil.isEmpty(c.getGobacktime())){
				try {
					String str=c.getGobacktime();
					if (str.contains("T")){
						str=str.substring(0,str.indexOf("T"));
					}
					LocalDate local=null;
					if(!StringUtil.isEmpty(str)  && Pattern.matches(pattern, str)){
						local = LocalDate.parse(str);
					}

//					if (local !=null && today.compareTo(local) == 0){
//						gobackBeijing++;
//					}
					if (local !=null && today.compareTo(local) >= 0){
						if (c.getComfirmed() ==null || c.getComfirmed()!=2) {
							//返京时间+14天 出隔离器时间
							LocalDate localDate = local.plusDays(14);
							if (today.compareTo(localDate) > 0) {
								outisolator++;
							} else {
								isolator++;
							}
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}

			}

			if (c.getHealthy() != null && c.getHealthy()==1){
				healthy++;
			}else if(c.getHealthy()!= null && c.getHealthy()==2){
				ferver++;
			}else {
				other++;
			}

			if (!StringUtil.isEmpty(c.getAddress()) && c.getAddress().contains("北京")){
				beijing++;
			}else if(!StringUtil.isEmpty(c.getAddress()) && c.getAddress().contains("湖北")){
				if (!StringUtil.isEmpty(c.getAddress()) && c.getAddress().contains("武汉")){
					wuhan++;
				}else {
					hubei++;
				}
			}else {
				otherRegion++;
			}
		}
		if (list.size()>0) {
			healthyPer = healthy / list.size() * 100;
			ferverPer = ferver / list.size() * 100;
			otherPer = other / list.size() * 100;
			beijingPer = beijing / list.size() * 100;
			hubeiPer = hubei / list.size() * 100;
			otherRegionPer = otherRegion / list.size() * 100;
			wuhanPer = wuhan / list.size() *100;
			diagnosisPer = diagnosis/list.size()*100;
			isolatorPer =isolator/list.size() *100;
			outisolatorPer=outisolator/list.size()*100;
			otherIsolatorPer= (list.size()-isolator-diagnosis-outisolator)/list.size()*100;

		}
		StringBuffer buffer=new StringBuffer("{");
		//写入总体统计数据
		buffer.append("\"totality\":{\"total\":"+ids.size()+",\"clockIn\":"+list.size()+",\"unClockIn\":"+(ids.size()-list.size())+",\"notInbeijing\":"+new Double(list.size()-beijing).intValue()+
				",\"goBackBeijing\":"+new Double(isolator+outisolator).intValue()+",\"abnormalbody\":"+new Double(list.size()-healthy).intValue()+",\"diagnosis\":"+new Double(diagnosis).intValue()+"},");


		//计算并写入第一张饼图数据
		buffer.append("\"healthy\":[{\"name\":\"健康\",\"value\":"+ new Double(healthy).intValue()+",\"percent\":"+format(healthyPer)+"}," +
				"{\"name\":\"发烧，咳嗽\",\"value\":"+new Double(ferver).intValue()+",\"percent\":"+format(ferverPer)+"}," +
				"{\"name\":\"其他症状\",\"value\":"+new Double(other).intValue()+",\"percent\":"+format(otherPer)+"}],");

		//计算并写入第二张饼图数据
		buffer.append("\"region\":[{\"name\":\"北京\",\"value\":"+new Double(beijing).intValue()+",\"percent\":"+format(+beijingPer)+"}," +
				"{\"name\":\"湖北\",\"value\":"+new Double(hubei).intValue()+",\"percent\":"+format(hubeiPer)+"}," +
				"{\"name\":\"武汉\",\"value\":"+new Double(wuhan).intValue()+",\"percent\":"+format(wuhanPer)+"}," +
				"{\"name\":\"其他地区\",\"value\":"+new Double(otherRegion).intValue()+",\"percent\":"+format(otherRegionPer)+"}],");

		//计算并写入第三张饼图数据
		buffer.append("\"hospitalization\":[{\"name\":\"确诊\",\"value\":"+new Double(diagnosis).intValue()+",\"percent\":"+format(+diagnosisPer)+"}," +
				"{\"name\":\"隔离期\",\"value\":"+new Double(isolator).intValue()+",\"percent\":"+format(isolatorPer)+"}," +
				"{\"name\":\"出隔离期\",\"value\":"+new Double(outisolator).intValue()+",\"percent\":"+format(outisolatorPer)+"}," +
				"{\"name\":\"其他\",\"value\":"+new Double(list.size()-diagnosis-outisolator-isolator).intValue()+",\"percent\":"+format(otherIsolatorPer)+"}]}");

		String str =buffer.toString();
		JSONObject object=JSONObject.parseObject(str);
//		System.out.println(str);
//		str=str.replace("\"","");
		return object.toJSONString();
	}




	private String format(double in){
		DecimalFormat df = new DecimalFormat("#.00");
		if (in==0){
			return "0";
		}
		return df.format(in);
	}
	
}
