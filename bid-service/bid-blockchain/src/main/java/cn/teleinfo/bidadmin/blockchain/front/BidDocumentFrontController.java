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
package cn.teleinfo.bidadmin.blockchain.front;

import cn.teleinfo.bidadmin.blockchain.entity.BidDocument;
import cn.teleinfo.bidadmin.blockchain.service.IBidDocumentService;
import cn.teleinfo.bidadmin.blockchain.vo.BidDocumentVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.BidDocumentWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author Blade
 * @since 2019-10-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/front/document/")
@Api(value = "bid ddo 文档前端访问", tags = "bid ddo 文档前端访问接口")
public class BidDocumentFrontController extends BladeController {

    private IBidDocumentService bidDocumentService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入bidDocument")
    public R<BidDocumentVO> detail(BidDocument bidDocument) {
        BidDocument detail = bidDocumentService.getOne(Condition.getQueryWrapper(bidDocument));
        if (detail == null) {
            return R.fail("没有找到该记录");
        }
        return R.data(BidDocumentWrapper.build().entityVO(detail));

    }

    @GetMapping("/{address}")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "通过地址拿到ddo文档", notes = "传入address")
    public String detail(@PathVariable("address") String address) {
        String detail = bidDocumentService.FindDDOByType(address);
        if (StringUtil.isEmpty(detail)) {
            return "not select this record";
        }
        return detail;
    }
}
