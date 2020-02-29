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
package cn.teleinfo.bidadmin.soybean.service;

import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 *  服务类
 *
 * @author Blade
 * @since 2020-02-21
 */
public interface IClocklnService extends IService<Clockln> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param clockln
	 * @return
	 */
	IPage<ClocklnVO> selectClocklnPage(IPage<ClocklnVO> page, ClocklnVO clockln);

	/**
	 *  根据组ID和时间统计当日打卡数据
	 * @param page 分页信息
	 * @param ids 用户ids
	 * @param clockInTime 统计日期
	 * @return
	 */
	IPage<ClocklnVO> selectClocklnPageByGroup(IPage<ClocklnVO> page,  List<Integer> ids,  Date clockInTime,Integer param3,Integer param4,Integer param5);

	/**
	 *  根据群组ID和日期查询当日打卡数据 非分页
	 * @param groupId
	 * @param clockInTime
	 * @return
	 */
	List<Clockln> selectClocklnByGroup(List<Integer> ids, Date clockInTime);

	/**
	 *  根据群组ID和日期查询当日打卡数据 非分页
	 * @param userId
	 * @param clockInTime
	 * @return
	 */
	Clockln selectClocklnByUserID(Integer userId, Date clockInTime);


	List<ClocklnVO> findByUserIdInAndCreatetimeBetween(List<Integer> collect, Date from, Date to);
	List<ClocklnVO> findByUserIdInAndCreateTime(@RequestParam("ids") List<Integer> ids, @RequestParam("clockInTime") Date from);
}
