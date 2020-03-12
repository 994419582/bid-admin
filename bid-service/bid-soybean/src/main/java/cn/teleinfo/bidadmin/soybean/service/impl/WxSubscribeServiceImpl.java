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

import cn.teleinfo.bidadmin.soybean.entity.WxSubscribe;
import cn.teleinfo.bidadmin.soybean.vo.WxSubscribeVO;
import cn.teleinfo.bidadmin.soybean.mapper.WxSubscribeMapper;
import cn.teleinfo.bidadmin.soybean.service.IWxSubscribeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2020-03-12
 */
@Service
public class WxSubscribeServiceImpl extends ServiceImpl<WxSubscribeMapper, WxSubscribe> implements IWxSubscribeService {

	@Override
	public IPage<WxSubscribeVO> selectWxSubscribePage(IPage<WxSubscribeVO> page, WxSubscribeVO wxSubscribe) {
		return page.setRecords(baseMapper.selectWxSubscribePage(page, wxSubscribe));
	}

	@Override
	public WxSubscribe selectWxSubscribe(String wechatId, Integer groupId, Date sendDate) {
		return baseMapper.selectByWechatIdOrGroupId(wechatId, groupId, sendDate);
	}

}
