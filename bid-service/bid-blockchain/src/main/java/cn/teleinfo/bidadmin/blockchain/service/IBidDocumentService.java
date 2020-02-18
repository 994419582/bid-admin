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

import cn.teleinfo.bidadmin.blockchain.entity.BidDocument;
import cn.teleinfo.bidadmin.blockchain.vo.BidDocumentVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服务类
 *
 * @author Blade
 * @since 2019-10-17
 */
public interface IBidDocumentService extends IService<BidDocument> {

    /**
     * 自定义分页
     *
     * @param page
     * @param bidDocument
     * @return
     */
    IPage<BidDocumentVO> selectBidDocumentPage(IPage<BidDocumentVO> page, BidDocumentVO bidDocument);

    /**
     * 保存document文档，如果数据(bid字段)存在，则忽略
     *
     * @param jsonString
     * @param balance
     * @return
     */
//	void saveDocument(String jsonString, String balance);
    void saveDocument(String jsonString, String address, String balance);

    BidDocument getByBId(String bid);

    BidDocument saveOrUpdateBID(BidDocument entity);

    BidDocument FindDDOByType(int type, String address);

    String FindDDOByType(String address);
}
