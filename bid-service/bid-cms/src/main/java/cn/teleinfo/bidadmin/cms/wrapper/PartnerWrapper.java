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
package cn.teleinfo.bidadmin.cms.wrapper;

import cn.teleinfo.bidadmin.system.feign.IDictClient;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import cn.teleinfo.bidadmin.cms.entity.Partner;
import cn.teleinfo.bidadmin.cms.vo.PartnerVO;
import org.springblade.core.tool.utils.SpringUtil;

/**
 * 官网合作伙伴包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2019-10-15
 */
public class PartnerWrapper extends BaseEntityWrapper<Partner, PartnerVO>  {

    public static PartnerWrapper build() {
        return new PartnerWrapper();
    }
	private static IDictClient dictClient;

	static {
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	@Override
	public PartnerVO entityVO(Partner partner) {
		PartnerVO partnerVO = BeanUtil.copy(partner, PartnerVO.class);
		R<String> dict = dictClient.getValue("partner", partnerVO.getType());
		if (dict.isSuccess()) {
			String categoryName = dict.getData();
			partnerVO.setCategoryName(categoryName);
		}
		return partnerVO;
	}

}
