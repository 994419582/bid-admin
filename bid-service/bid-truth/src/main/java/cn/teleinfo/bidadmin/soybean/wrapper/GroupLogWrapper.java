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

import cn.teleinfo.bidadmin.system.feign.IDictClient;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import cn.teleinfo.bidadmin.soybean.entity.GroupLog;
import cn.teleinfo.bidadmin.soybean.vo.GroupLogVO;
import org.springblade.core.tool.utils.SpringUtil;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2020-02-21
 */
public class GroupLogWrapper extends BaseEntityWrapper<GroupLog, GroupLogVO>  {
	private static IDictClient dictClient;

	static {
		dictClient = SpringUtil.getBean(IDictClient.class);
	}


	public static GroupLogWrapper build() {
        return new GroupLogWrapper();
    }

	@Override
	public GroupLogVO entityVO(GroupLog groupLog) {
		GroupLogVO groupLogVO = BeanUtil.copy(groupLog, GroupLogVO.class);
		R<String> dict = dictClient.getValue("eventType", groupLog.getEventType());
		if (dict.isSuccess()) {
			String categoryName = dict.getData();
			groupLogVO.setEventName(categoryName);
		}
		return groupLogVO;
	}

}
