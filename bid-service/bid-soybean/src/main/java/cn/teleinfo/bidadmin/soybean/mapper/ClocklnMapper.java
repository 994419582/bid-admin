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
package cn.teleinfo.bidadmin.soybean.mapper;

import cn.teleinfo.bidadmin.soybean.entity.Clockln;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2020-02-21
 */
public interface ClocklnMapper extends BaseMapper<Clockln> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param clockln
	 * @return
	 */
	List<ClocklnVO> selectClocklnPage(IPage page, ClocklnVO clockln);


	List<ClocklnVO> selectClocklnPageByGroup(IPage<ClocklnVO> page, @RequestParam("groupId") Integer groupId, @RequestParam("clockInTime") Date clockInTime);

	List<Clockln> selectClocklnByGroup(@RequestParam("groupId") Integer groupId, @RequestParam("clockInTime") Date clockInTime);
	Clockln selectClocklnByUserID(@RequestParam("userId") Integer userId, @RequestParam("clockInTime") Date clockInTime);

	List<ClocklnVO> findByUserIdInAndCreatetimeBetween(@RequestParam("ids") List<Integer> ids, @RequestParam("from") Date from, @RequestParam("to") Date to);
}