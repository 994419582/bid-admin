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
import cn.teleinfo.bidadmin.blockchain.service.IBidDocumentService;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.utils.BeanUtil;
import cn.teleinfo.bidadmin.blockchain.entity.TrustanchorManage;
import cn.teleinfo.bidadmin.blockchain.vo.TrustanchorManageVO;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2019-10-29
 */
public class TrustanchorManageWrapper extends BaseEntityWrapper<TrustanchorManage, TrustanchorManageVO>  {

    public static TrustanchorManageWrapper build() {
        return new TrustanchorManageWrapper();
    }

	private static IBidDocumentService service;
	static {
		service= SpringUtil.getBean(IBidDocumentService.class);
	}

	@Override
	public TrustanchorManageVO entityVO(TrustanchorManage trustanchorManage) {
		TrustanchorManageVO trustanchorManageVO = BeanUtil.copy(trustanchorManage, TrustanchorManageVO.class);
		if (!StringUtil.isEmpty(trustanchorManage.getOwner())){
			BidDocument bid = new BidDocument();
			bid.setBid(trustanchorManage.getOwner());
			bid=service.getOne(Condition.getQueryWrapper(bid));
			if (bid!=null && bid.getId()>0){
				trustanchorManageVO.setBalance(bid.getBalance());
				trustanchorManageVO.setOwnerName(bid.getName());
			}
		}
		return trustanchorManageVO;
	}

}
