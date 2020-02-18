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
package cn.teleinfo.bidadmin.app.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import cn.teleinfo.bidadmin.app.entity.Feedback;
import cn.teleinfo.bidadmin.app.vo.FeedbackVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2019-11-27
 */
public class FeedbackWrapper extends BaseEntityWrapper<Feedback, FeedbackVO>  {

    public static FeedbackWrapper build() {
        return new FeedbackWrapper();
    }

	@Override
	public FeedbackVO entityVO(Feedback feedback) {
		FeedbackVO feedbackVO =  new FeedbackVO();
    	if (feedback!=null) {
			feedbackVO = BeanUtil.copy(feedback, FeedbackVO.class);
		}

		return feedbackVO;
	}

}
