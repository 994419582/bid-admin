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

import cn.teleinfo.bidadmin.soybean.entity.Notice;
import cn.teleinfo.bidadmin.soybean.vo.NoticeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 通知公告表 服务类
 *
 * @author Blade
 * @since 2020-03-25
 */
public interface INoticeService extends IService<Notice> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param notice
	 * @return
	 */
	IPage<NoticeVO> selectNoticePage(IPage<NoticeVO> page, NoticeVO notice);

	/**
	 * 读取通知
	 * @param noticeIdList
	 * @return
	 */
    boolean read(List<Integer> noticeIdList);
}
