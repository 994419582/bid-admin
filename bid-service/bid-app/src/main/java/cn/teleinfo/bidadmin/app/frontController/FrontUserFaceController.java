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
package cn.teleinfo.bidadmin.app.frontController;

import cn.teleinfo.bidadmin.app.api.BidFaceApi;
import cn.teleinfo.bidadmin.app.entity.UserFace;
import cn.teleinfo.bidadmin.app.service.IUserFaceService;
import cn.teleinfo.bidadmin.blockchain.feign.ISendIntegralClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 控制器
 *
 * @author Blade
 * @since 2019-11-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/app/userface")
@Api(value = "用户人脸识别", tags = "用户人脸识别接口")
public class FrontUserFaceController extends BladeController {

    private IUserFaceService faceService;

    private BidFaceApi api;

    private ISendIntegralClient client;

    @PostMapping("/addFace")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "注册人脸", notes = "传入file")
    public R addFace(@RequestParam("file") MultipartFile file, @RequestParam("bid") String bid) {
        if (StringUtil.isEmpty(bid)) {
            return R.fail("bid 为空");
        }
        if (!file.isEmpty()) {
            String image = api.multipartFileToString(file);
            R r = api.addUser(bid, image);
            if (r.isSuccess()) {
                String msg = r.getMsg();
                UserFace face = new UserFace();
                face.setBid(bid);
                face.setFaceToken(msg);
                boolean flag = faceService.saveOrUpdate(face);
                if (!flag) {
                    return R.fail("人脸注册失败");
                }
            } else {
                return R.fail(r.getMsg());
            }

        }
        //TODO 还没有开发完毕
        R r = client.sendIntegral(bid);
        if (r.isSuccess()) {
            return R.success("注册成功");
        }
        return R.fail("注册失败");
    }

    @PostMapping("/addFaceBase64")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "注册人脸", notes = "传入Base64编码的file字符串")
    public R addFace(@RequestParam("image") String image, @RequestParam("bid") String bid) {
        if (StringUtil.isEmpty(bid)) {
            return R.fail("bid 为空");
        }

        R r = api.addUser(bid, image);
        if (r.isSuccess()) {
            String msg = r.getMsg();
            UserFace face = new UserFace();
            face.setBid(bid);
            face.setFaceToken(msg);
            boolean flag = faceService.saveOrUpdate(face);
            if (!flag) {
                return R.fail("人脸注册失败");
            }
        } else {
            return R.fail(r.getMsg());
        }

        //TODO 还没有开发完毕
        R rr = client.sendIntegral(bid);
        if (rr.isSuccess()) {
            return R.success("注册成功");
        }
        return R.fail("注册失败");
    }

    @PostMapping("/search")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "验证人脸", notes = "传入file")
    public R search(@RequestParam("file") MultipartFile file, @RequestParam("bid") String bid) {
        if (StringUtil.isEmpty(bid)) {
            return R.fail("bid是空的");
        }
        if (file.isEmpty()) {
            return R.fail("上传的头像是空的");
        } else {
            String image = api.multipartFileToString(file);
            return R.status(faceService.search(bid, image));
        }
    }

    @PostMapping("/searchBase64")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "验证人脸", notes = "传入Base64编码的file字符串")
    public R search(@RequestParam("image") String image, @RequestParam("bid") String bid) {
        if (StringUtil.isEmpty(bid)) {
            return R.fail("bid是空的");
        }
        return R.status(faceService.search(bid, image));
    }

    @PostMapping("/exist")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "人脸是否被注册", notes = "bid地址")
    public R exist(@RequestParam("bid") String bid) {
        if (StringUtil.isEmpty(bid)) {
            return R.fail("bid是空的");
        }
        return R.status(faceService.search(bid));
    }
}
