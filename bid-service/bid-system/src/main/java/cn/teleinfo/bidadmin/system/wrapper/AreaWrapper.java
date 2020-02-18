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
package cn.teleinfo.bidadmin.system.wrapper;

import cn.teleinfo.bidadmin.system.entity.Area;
import cn.teleinfo.bidadmin.system.vo.AreaVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2019-10-08
 */
public class AreaWrapper extends BaseEntityWrapper<Area, AreaVO>  {

    public static AreaWrapper build() {
        return new AreaWrapper();
    }

	@Override
	public AreaVO entityVO(Area area) {
		AreaVO articleDataVO = BeanUtil.copy(area, AreaVO.class);

		return articleDataVO;
	}

}
