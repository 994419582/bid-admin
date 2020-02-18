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
package cn.teleinfo.bidadmin.app.service.impl;

import cn.teleinfo.bidadmin.app.entity.Version;
import cn.teleinfo.bidadmin.app.vo.VersionVO;
import cn.teleinfo.bidadmin.app.mapper.VersionMapper;
import cn.teleinfo.bidadmin.app.service.IVersionService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-11-27
 */
@Service
public class VersionServiceImpl extends BaseServiceImpl<VersionMapper, Version> implements IVersionService {

	@Override
	public IPage<VersionVO> selectVersionPage(IPage<VersionVO> page, VersionVO version) {
		return page.setRecords(baseMapper.selectVersionPage(page, version));
	}

}
