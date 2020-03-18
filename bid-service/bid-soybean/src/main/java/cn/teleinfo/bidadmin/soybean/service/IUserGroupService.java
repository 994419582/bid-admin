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
package cn.teleinfo.bidadmin.soybean.service;

import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.vo.UserGroupVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.Valid;
import java.util.List;

/**
 * 服务类
 *
 * @author Blade
 * @since 2020-02-21
 */
public interface IUserGroupService extends IService<UserGroup> {

    /**
     * 自定义分页
     *
     * @param page
     * @param userGroup
     * @return
     */
    IPage<UserGroupVO> selectUserGroupPage(IPage<UserGroupVO> page, UserGroupVO userGroup);

    /**
     * 查询某群组下的所有用户
     *
     * @param groupid
     * @return
     */
    List<User> findUserByGroupId(Integer groupid);

    /**
     * 校验用户组
     * @param userGroup
     */
    void checkAddUserGroup(UserGroup userGroup);

    /**
     * 管理员删除用户
     * @param userGroup
     * @return
     */
    boolean managerRemoveUser(UserGroupVO userGroup);

    /**
     * 退群
     * @return
     * @param userGroup
     */
    boolean quitGroup(UserGroup userGroup);

    /**
     * 用户加群
     * @param userGroup
     * @return
     */
    boolean saveUserGroup(UserGroup userGroup);

    /**
     * 用户退群
     * @param toIntList
     * @return
     */
    boolean removeUserGroupByIds(List<Integer> toIntList);

    /**
     * 根据ID获取状态正常的群组关系
     */
    UserGroup getUserGroupById(Integer id);

    /**
     * 群里是否存在此用户
     * @return
     */
    boolean existUserGroup(Integer groupId,Integer userId);

    /**
     * 修改群人数
     *
     * @param groupId 部门ID
     * @param addAccount 增加的数量
     */
    void motifyUserAccount(Integer groupId, Integer addAccount);

    /**
     * 删除用户所有权限
     * @param userId
     */
    void deleteAllPermission(Integer userId);
}
