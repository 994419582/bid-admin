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

import cn.teleinfo.bidadmin.blockchain.entity.DposStake;
import cn.teleinfo.bidadmin.blockchain.service.IBidDocumentService;
import cn.teleinfo.bidadmin.blockchain.service.IDposStakeService;
import cn.teleinfo.bidadmin.blockchain.service.impl.DposStakeServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.utils.BeanUtil;
import cn.teleinfo.bidadmin.blockchain.entity.DposVoter;
import cn.teleinfo.bidadmin.blockchain.vo.DposVoterVO;
import org.springblade.core.tool.utils.SpringUtil;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author Blade
 * @since 2019-10-17
 */
public class DposVoterWrapper extends BaseEntityWrapper<DposVoter, DposVoterVO>  {

    public static DposVoterWrapper build() {
        return new DposVoterWrapper();
    }

	private static IDposStakeService service;
	static {
		service= SpringUtil.getBean(IDposStakeService.class);
	}
	@Override
	public DposVoterVO entityVO(DposVoter dposVoter) {
		DposVoterVO dposVoterVO = BeanUtil.copy(dposVoter, DposVoterVO.class);
		if (dposVoter !=null){

			DposStake dposStake=new DposStake() ;
			dposStake.setOwner(dposVoter.getOwner());
			dposStake=service.getOne(Condition.getQueryWrapper(dposStake));
			if (dposStake!=null){
				dposVoterVO.setStakeCount(dposStake.getStakeCount());
				dposVoterVO.setStakeTime(dposStake.getStakeTime());
			}
		}

		return dposVoterVO;
	}

}
