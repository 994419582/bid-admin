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
package cn.teleinfo.bidadmin.soybean.wxfront;

import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IParentGroupService;
import cn.teleinfo.bidadmin.soybean.service.impl.GroupServiceImpl;
import cn.teleinfo.bidadmin.soybean.vo.GroupTreeVo;
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
import cn.teleinfo.bidadmin.soybean.wrapper.GroupWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
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
import java.util.List;

/**
 * 控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/group")
@Api(value = "", tags = "群组接口")
public class WxGroupController extends BladeController {

    private IGroupService groupService;

    private IParentGroupService parentGroupService;

    /**
     * 根据ID查看群组详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "群组详情", notes = "传入groupId")
    public R<GroupVO> detail(Group group) {
        if (group.getId() == null) {
            throw new ApiException("群组id不能为空");
        }
        Group detail = groupService.detail(group);
        return R.data(GroupWrapper.build().entityVO(detail));
    }

    /**
     * 群组列表
     *
     * @return
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "群组列表", notes = "查询所有群")
    public R<List<GroupVO>> select() {
        List<Group> groups = groupService.select();
        return R.data(GroupWrapper.build().listVO(groups));
    }

    /**
     * 分页查询
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页查询", notes = "传入group")
    public R<IPage<GroupVO>> list(Group group, Query query) {
        IPage<Group> pages = groupService.page(Condition.getPage(query), Condition.getQueryWrapper(group));
        return R.data(GroupWrapper.build().pageVO(pages));
    }

    /**
     * 树形下拉列表字典样式
     * @return
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "下拉树形图", notes = "返回所有群组，字段含有parentId")
    public R<List<GroupTreeVo>> tree() {
        return R.data(groupService.tree());
    }

    /**
     * 带有children的树形下拉列表字典样式
     * @return
     */
    @GetMapping("/tree/children")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "树形下拉列表字典", notes = "返回所有群组，字段含有children")
    public R<List<GroupTreeVo>> treeChildren() {
        List<GroupTreeVo> tree = groupService.treeChildren();
        return R.data(tree);
    }

    @GetMapping("/tree/user")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "树形下拉列表字典", notes = "返回用户所有群组，带有children字段")
    public R<List<GroupTreeVo>> treeChildren(@ApiParam(value = "用户ID", required = true)@RequestParam Integer userId) {
        List<GroupTreeVo> tree = groupService.treeUser(userId);
        return R.data(tree);
    }


    /**
     * 新增群组
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增群组", notes = "传入group,用户Id不能为null")
    public R save(@Valid @RequestBody GroupVO group) {
        Integer userId = group.getUserId();
        if (userId == null) {
            return R.status(false);
        }
        group.setCreateUser(userId);
        return R.status(groupService.saveGroupMiddleTable(group));
    }

    /**
     * 修改群组
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改群组", notes = "传入group，修改时用户Id必须和所有群组创建人一致")
    public R update(@Valid @RequestBody GroupVO group) {
        Integer userId = group.getUserId();
        //更新群
        if (userId.equals(group.getCreateUser())) {
            throw new ApiException("用户ID和群创建人ID不一致");
        }
        return R.status(groupService.updateGroupMiddleTable(group));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入group，修改时用户Id必须和所有群组创建人一致")
    public R submit(@Valid @RequestBody GroupVO group) {
        Integer userId = group.getUserId();
        GroupServiceImpl.modifyObject(group);
        //新增群，设置创建人
        if (group.getId() == null) {
            group.setCreateUser(userId);
        } else {
            //更新群
            if (userId.equals(group.getCreateUser())) {
                throw new ApiException("用户ID和群创建人ID不一致");
            }
        }
        return R.status(groupService.saveOrUpdateGroupMiddleTable(group));
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids, 用户Id必须和所有群组创建人一致")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids,
                    @ApiParam(value = "用户ID", required = true) @RequestParam Integer userId) {
        //更新群
        //查询用户Id和创建人是否一致
        List<Integer> idList = Func.toIntList(ids);
        for (Integer id : idList) {
            Group group = groupService.getById(id);
            if (group != null && userId.equals(group.getCreateUser())) {
                throw new ApiException("用户ID和群创建人ID不一致");
            }
        }
        return R.status(groupService.removeGroupMiddleTableById(idList));
    }

    /**
     * 群组添加用户
     *
     * @return
     */
    @PostMapping("/user")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "群组添加用户", notes = "传入groupId和userId")
    public R addUser(@Valid @RequestBody UserGroup userGroup) {
        return R.status(groupService.addUser(userGroup));
    }

    /**
     * 群组删除用户
     *
     * @return
     */
    @DeleteMapping("/user")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "群组删除用户", notes = "传入groupId和userId")
    public R delUser(@Valid @RequestBody UserGroup userGroup) {
        return R.status(groupService.delUser(userGroup));
    }

}
