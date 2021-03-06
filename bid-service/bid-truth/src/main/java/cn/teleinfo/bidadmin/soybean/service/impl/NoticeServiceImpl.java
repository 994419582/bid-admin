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

import cn.teleinfo.bidadmin.soybean.entity.Notice;
import cn.teleinfo.bidadmin.soybean.vo.NoticeVO;
import cn.teleinfo.bidadmin.soybean.mapper.NoticeMapper;
import cn.teleinfo.bidadmin.soybean.service.INoticeService;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通知公告表 服务实现类
 *
 * @author Blade
 * @since 2020-03-25
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

	@Override
	public IPage<NoticeVO> selectNoticePage(IPage<NoticeVO> page, NoticeVO notice) {
		return page.setRecords(baseMapper.selectNoticePage(page, notice));
	}

    @Override
    @Transactional
    public boolean read(List<Integer> noticeIdList) {
        for (Integer noticeId : noticeIdList) {
            if (getById(noticeId) == null) {
                throw new ApiException("通知ID:"+noticeId+"不存在");
            }
            Notice notice = new Notice();
            notice.setId(noticeId);
            notice.setStatus(Notice.STATUS_READ);
            updateById(notice);
        }
	    return true;
    }

}
