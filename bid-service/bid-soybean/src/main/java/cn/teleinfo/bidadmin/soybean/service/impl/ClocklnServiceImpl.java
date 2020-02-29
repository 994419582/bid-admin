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
package cn.teleinfo.bidadmin.soybean.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import cn.teleinfo.bidadmin.soybean.mapper.ClocklnMapper;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Service
public class ClocklnServiceImpl extends ServiceImpl<ClocklnMapper, Clockln> implements IClocklnService {

	@Autowired
	ClocklnMapper mapper;

	@Override
	public IPage<ClocklnVO> selectClocklnPage(IPage<ClocklnVO> page, ClocklnVO clockln) {
		return page.setRecords(baseMapper.selectClocklnPage(page, clockln));
	}

	@Override
	public  IPage<ClocklnVO> selectClocklnPageByGroup(IPage<ClocklnVO> page, @RequestParam("ids") List<Integer> ids, @RequestParam("clockInTime") Date clockInTime,Integer param3,Integer param4,Integer param5){
		return page.setRecords(mapper.selectClocklnPageByGroup(page, ids,clockInTime,param3,param4,param5));
	}

	@Override
	public  List<Clockln> selectClocklnByGroup(List<Integer> ids,  Date clockInTime){
		return baseMapper.selectClocklnByGroup(ids,clockInTime);
	}

	/**
	 *  根据群组ID和日期查询当日打卡数据 非分页
	 * @param userId
	 * @param clockInTime
	 * @return
	 */
	@Override
	public Clockln selectClocklnByUserID(Integer userId, Date clockInTime){
		return baseMapper.selectClocklnByUserID(userId,clockInTime);
	}

	@Override
	public List<ClocklnVO> findByUserIdInAndCreatetimeBetween(List<Integer> ids, Date from, Date to) {
		return baseMapper.findByUserIdInAndCreatetimeBetween(ids, from, to);
	}
	public List<ClocklnVO> findByUserIdInAndCreateTime(@RequestParam("ids") List<Integer> ids, @RequestParam("clockInTime") Date from){
		return baseMapper.findByUserIdInAndCreateTime(ids,from);
	}
}
