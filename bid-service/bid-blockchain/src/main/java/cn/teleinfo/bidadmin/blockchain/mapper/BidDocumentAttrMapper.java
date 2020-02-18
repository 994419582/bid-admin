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
package cn.teleinfo.bidadmin.blockchain.mapper;

import cn.teleinfo.bidadmin.blockchain.entity.BidDocumentAttr;
import cn.teleinfo.bidadmin.blockchain.vo.BidDocumentAttrVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Blade
 * @since 2019-10-17
 */
@Component
public interface BidDocumentAttrMapper extends BaseMapper<BidDocumentAttr> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bidDocumentAttr
	 * @return
	 */
	List<BidDocumentAttrVO> selectBidDocumentAttrPage(IPage page, BidDocumentAttrVO bidDocumentAttr);

	BidDocumentAttr findDocumentAttrByOwnerType(@Param("owner") String owner, @Param("Type") String type);

	BidDocumentAttr saveDocumentAttr(BidDocumentAttr bidDocumentAttr);

	BidDocumentAttr updateDocumentAttr(BidDocumentAttr bidDocumentAttr);

	@Deprecated
	int saveBatchDocumentAttrs(List<BidDocumentAttr> bidDocumentAttrs);
}
