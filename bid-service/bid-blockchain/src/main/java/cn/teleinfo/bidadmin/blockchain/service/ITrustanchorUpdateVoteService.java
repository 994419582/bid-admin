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
package cn.teleinfo.bidadmin.blockchain.service;

import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorUpdateVote;
import cn.teleinfo.bidadmin.blockchain.vo.TrustanchorUpdateVoteVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务类
 *
 * @author Blade
 * @since 2019-10-29
 */
public interface ITrustanchorUpdateVoteService extends IService<TrustanchorUpdateVote> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param trustanchorUpdateVote
	 * @return
	 */
	IPage<TrustanchorUpdateVoteVO> selectTrustanchorUpdateVotePage(IPage<TrustanchorUpdateVoteVO> page, TrustanchorUpdateVoteVO trustanchorUpdateVote);

	void saveOrUpdateTrustanchorUpdateVote(List<TrustanchorUpdateVote> votes);
}
