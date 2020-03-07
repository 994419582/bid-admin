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
package cn.teleinfo.bidadmin.soybean.wrapper;

import cn.teleinfo.bidadmin.soybean.entity.HealthQrcode;
import cn.teleinfo.bidadmin.soybean.vo.HealthQrcodeVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-02-21
 */
public class HealthQrcodeWrapper extends BaseEntityWrapper<HealthQrcode, HealthQrcodeVO>  {

    public static HealthQrcodeWrapper build() {
        return new HealthQrcodeWrapper();
    }

	@Override
	public HealthQrcodeVO entityVO(HealthQrcode healthQrcode) {
		HealthQrcodeVO healthQrcodeVO = BeanUtil.copy(healthQrcode, HealthQrcodeVO.class);

		return healthQrcodeVO;
	}

}
