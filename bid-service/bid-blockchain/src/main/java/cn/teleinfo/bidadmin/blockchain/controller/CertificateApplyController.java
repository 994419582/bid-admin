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

import cn.teleinfo.bidadmin.app.dto.AuthenticationDTO;
import cn.teleinfo.bidadmin.app.feign.IUserIdentityClient;
import cn.teleinfo.bidadmin.blockchain.service.ICertificateApplyService;
import cn.teleinfo.bidadmin.blockchain.util.FileUtils;
import cn.teleinfo.bidadmin.blockchain.util.ImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 控制器
 *
 * @author Blade
 * @since 2019-12-09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/front/certificate")
@Api(value = "", tags = "接口")
public class CertificateApplyController extends BladeController {

    private ICertificateApplyService certificateService;
    @Autowired
    private IUserIdentityClient userIdentityClient;

    /**
     * 详情
     */
    @PostMapping("/apply")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "申请颁发证书", notes = "传入照片file，名字name，身份证号idCardNumber，地址bid和公钥publicKey")
    public R issuerCertificate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("idCardNumber") String idCardNumber,
            @RequestParam("bid") String bid,
            @RequestParam("publicKey") String publicKey
    ) {
        String image = FileUtils.multipartFileToString(file);

        // 压缩图片
        File f = new File("./tmp_img.jpg");
        ImageUtils.base64ToImage(image, f);
        ImageUtils.compress(f);
        String r = ImageUtils.imageToBase64(f);

        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setIdCard(idCardNumber);
        authenticationDTO.setUserName(name);
        authenticationDTO.setPhoto(r);

        // 调用公安接口验证身份信息
        R authentication = userIdentityClient.authentication(authenticationDTO);

        // 如果验证通过，则颁发证书
        if (authentication.isSuccess()) {
            return certificateService.issuerCertificate(name, idCardNumber, bid, publicKey);
        } else {
            return R.fail(authentication.getMsg());
        }

    }

    /**
     * 详情
     */
    @PostMapping("/applyBase64")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "申请颁发证书", notes = "传入照片Base64格式的image，名字name，身份证号idCardNumber，地址bid和公钥publicKey")
    public R issuerCertificateBase64(
            @RequestParam("image") String image,
            @RequestParam("name") String name,
            @RequestParam("idCardNumber") String idCardNumber,
            @RequestParam("bid") String bid,
            @RequestParam("publicKey") String publicKey
    ) {
        // 压缩图片
        File file = new File("./tmp_img.jpg");
        ImageUtils.base64ToImage(image, file);
        ImageUtils.compress(file);
        String r = ImageUtils.imageToBase64(file);

        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setIdCard(idCardNumber);
        authenticationDTO.setUserName(name);
        authenticationDTO.setPhoto(r);

        // 调用公安接口验证身份信息
        R authentication = userIdentityClient.authentication(authenticationDTO);

        // 如果验证通过，则颁发证书
        if (authentication.isSuccess()) {
            return certificateService.issuerCertificate(name, idCardNumber, bid, publicKey);
        } else {
            return R.fail(authentication.getMsg());
        }

    }

}
