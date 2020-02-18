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
package cn.teleinfo.bidadmin.blockchain.controller;

import cn.teleinfo.bidadmin.blockchain.entity.Certificate;
import cn.teleinfo.bidadmin.blockchain.entity.CertificateInfo;
import cn.teleinfo.bidadmin.blockchain.service.ICertificateService;
import cn.teleinfo.bidadmin.blockchain.vo.CertificateVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.CertificateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 控制器
 *
 * @author Blade
 * @since 2019-12-09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/certificate")
@Api(value = "", tags = "接口")
public class CertificateController extends BladeController {

    private ICertificateService certificateService;

    /**
     * 详情
     */
    @GetMapping("/registerCertificate")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入CertificateInfo")
    public R registerCertificate(CertificateInfo certificate) {
        certificateService.registerCertificate(certificate);
        return R.success("注册成功");
    }

    /**
     * 详情
     */
    @GetMapping("/revocedCertificate")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "撤消", notes = "传入bid地址")
    public R revocedCertificate(String bid) {
        certificateService.revocedCertificate(bid);
        return R.success("撤消成功");
    }

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "详情", notes = "传入certificate")
    public R<CertificateVO> detail(Certificate certificate) {
        Certificate detail = certificateService.getOne(Condition.getQueryWrapper(certificate));
        return R.data(CertificateWrapper.build().entityVO(detail));
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "分页", notes = "传入certificate")
    public R<IPage<CertificateVO>> list(Certificate certificate, Query query) {
        IPage<Certificate> pages = certificateService.page(Condition.getPage(query), Condition.getQueryWrapper(certificate));
        return R.data(CertificateWrapper.build().pageVO(pages));
    }

    /**
     * 自定义分页
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "分页", notes = "传入certificate")
    public R<IPage<CertificateVO>> page(CertificateVO certificate, Query query) {
        IPage<CertificateVO> pages = certificateService.selectCertificatePage(Condition.getPage(query), certificate);
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增", notes = "传入certificate")
    public R save(@Valid @RequestBody Certificate certificate) {
        return R.status(certificateService.save(certificate));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "修改", notes = "传入certificate")
    public R update(@Valid @RequestBody Certificate certificate) {
        return R.status(certificateService.updateById(certificate));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "新增或修改", notes = "传入certificate")
    public R submit(@Valid @RequestBody Certificate certificate) {
        return R.status(certificateService.saveOrUpdate(certificate));
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(certificateService.removeByIds(Func.toIntList(ids)));
    }
}
