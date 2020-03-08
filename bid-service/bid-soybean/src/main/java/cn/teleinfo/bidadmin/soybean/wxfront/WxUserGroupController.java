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
import cn.teleinfo.bidadmin.soybean.service.IGroupLogService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.vo.GroupTreeVo;
import cn.teleinfo.bidadmin.soybean.vo.UserGroupVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.MyBatisSystemException;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 控制器
 *
 * @author Blade
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wx/usergroup")
@Api(value = "", tags = "微信用户群组接口")
public class WxUserGroupController extends BladeController {

    private IUserGroupService userGroupService;

    private IGroupService groupService;

    private IGroupLogService groupLogService;

    /**
     * 用户加群
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "用户加群", notes = "传入userGroup")
    public R save(@Valid @RequestBody UserGroup userGroup) {
        try {
            return R.status(userGroupService.saveUserGroup(userGroup));
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 获取用户已加入的群
     */
    @GetMapping("/user/currentGroup")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "获取用户已加入的群", notes = "获取用户已加入的群")
    public R<Group> save(@Valid @RequestParam(name = "userId", required = true) Integer userId) {
        LambdaQueryWrapper<UserGroup> queryWrapper = Wrappers.<UserGroup>lambdaQuery().
                eq(UserGroup::getUserId, userId).eq(UserGroup::getStatus, UserGroup.NORMAL);
        try {
            UserGroup userGroup = userGroupService.getOne(queryWrapper);
            if (userGroup == null) {
                return R.data(null);
            } else {
                return R.data(groupService.getById(userGroup.getGroupId()));
            }
        } catch (MyBatisSystemException e) {
            return R.fail("数据异常, 请联系管理员");
        }
    }

    /**
     * 用户退群
     */
    @PostMapping("/quit")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "用户退群", notes = "传入用户ID和群ID")
    public R remove(@Valid @RequestBody UserGroup userGroup) {
        try {
            return R.status(userGroupService.quitGroup(userGroup));
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }


    /**
     * 管理员删除群用户
     */
    @PostMapping("/manager/remove")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "群管理员踢除用户", notes = "传入要删除的用户userId，群组groupId，操作人managerId")
    public R save(@Valid @RequestBody UserGroupVO userGroup) {
        try {
            Integer groupId = userGroup.getGroupId();
            Integer userId = userGroup.getUserId();
            Integer managerId = userGroup.getManagerId();
            if (!userGroupService.existUserGroup(groupId, userId)) {
                throw new ApiException("用户已退群");
            }
            List<GroupTreeVo> groupAndParent = groupService.selectAllGroupAndParent();
            //获取管理员管理的群
            List<Group> managerGroups = groupService.getUserManageGroups(managerId);
            //去除子群
            ArrayList<Integer> removeManagerIds = new ArrayList<>();
            for (Group managerGroup : managerGroups) {
                for (Group group : managerGroups) {
                    if (groupService.isChildrenGroup(groupAndParent, managerGroup.getId(), group.getId())) {
                        removeManagerIds.add(group.getId());
                    }
                }
            }
            managerGroups.removeIf(group -> {
                return removeManagerIds.contains(group.getId());
            });
            //获取被删除人管理的群
            List<Group> userManageGroups = groupService.getUserManageGroups(userId);
            //去除子群
            ArrayList<Integer> removeUserManagerIds = new ArrayList<>();
            for (Group userManageGroup : userManageGroups) {
                for (Group group : managerGroups) {
                    if (groupService.isChildrenGroup(groupAndParent, userManageGroup.getId(), group.getId())) {
                        removeUserManagerIds.add(group.getId());
                    }
                }
            }
            userManageGroups.removeIf(group -> {
                return removeUserManagerIds.contains(group.getId());
            });
            //校验用户管理的群是否为管理员的父群，false代表是用户管理的群不是管理员管理群的父群
            boolean flag = false;
            for (Group userManageGroup : userManageGroups) {
                for (Group managerGroup : managerGroups) {
                    if (groupService.isChildrenGroup(groupAndParent, userManageGroup.getId(), managerGroup.getId()) || userManageGroup.getId().equals(managerGroup.getId())) {
                        flag = true;
                    }
                }
            }
            //当用户管理的群是管理员管理器的父群或者同群时
            if (flag == true) {
                throw new ApiException("您没有权限");
            }
            //校验用户加入的群是否为管理员管理的子群，false代表不是子群
            boolean childFlag = false;
            for (Group managerGroup : managerGroups) {
                if (groupService.isChildrenGroup(groupAndParent, managerGroup.getId(), groupId) || managerGroup.getId().equals(groupId)) {
                    childFlag = true;
                }
            }
            //用户加入的群不是管理员管理的子群时，提示没有权限
            if (childFlag == false) {
                throw new ApiException("您没有权限");
            }
            // 删除
            return R.status(userGroupService.managerRemoveUser(userGroup));
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

}
