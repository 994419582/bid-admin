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
package cn.teleinfo.bidadmin.blockchain.wrapper;

import cn.teleinfo.bidadmin.blockchain.entity.BidDocument;
import cn.teleinfo.bidadmin.blockchain.entity.DposCandidate;
import cn.teleinfo.bidadmin.blockchain.service.IBidDocumentService;
import cn.teleinfo.bidadmin.blockchain.vo.DposCandidateVO;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2019-10-17
 */
public class DposCandidateWrapper extends BaseEntityWrapper<DposCandidate, DposCandidateVO>  {

	private static IBidDocumentService service;
	static {
		service=SpringUtil.getBean(IBidDocumentService.class);
	}

    public static DposCandidateWrapper build() {
        return new DposCandidateWrapper();
    }

	@Override
	public DposCandidateVO entityVO(DposCandidate dposCandidate) {
		DposCandidateVO dposCandidateVO = BeanUtil.copy(dposCandidate, DposCandidateVO.class);
		if (!StringUtil.isEmpty(dposCandidate.getOwner())){
			BidDocument bid = new BidDocument();
			bid.setBid(dposCandidate.getOwner());
			bid=service.getOne(Condition.getQueryWrapper(bid));
			if (bid!=null && bid.getId()>0){
				dposCandidateVO.setBalance(bid.getBalance());
				dposCandidateVO.setOwnerName(bid.getName());
			}
		}

		return dposCandidateVO;
	}

}
