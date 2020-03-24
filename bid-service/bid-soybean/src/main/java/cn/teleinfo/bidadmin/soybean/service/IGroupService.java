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

import cn.teleinfo.bidadmin.soybean.bo.UserBO;
import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.vo.GroupTreeVo;
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务类
 *
 * @author Blade
 * @since 2020-02-21
 */
public interface IGroupService extends IService<Group> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param group
	 * @return
	 */
	IPage<GroupVO> selectGroupPage(IPage<GroupVO> page, GroupVO group);

	/**
	 * 群组下拉树形图
	 * @return
	 */
    List<GroupTreeVo> selectAllGroupAndParent();

	/**
	 * 群组明细
	 * @param group
	 * @return
	 */
	Group detail(Group group);

	/**
	 * 群组下拉树
	 * @return
	 * @param groupId
	 */
	List<GroupTreeVo> treeChildren(Integer groupId);

	/**
	 * 根据父群组查询子群组
	 * @param group
	 * @return
	 */
	List<Group> children(Group group);

	/**
	 * 群组列表
	 * @return
	 */
    List<Group> select();

	/**
	 * 用户管理的所有群组
	 * @return
     * @param userId
     */
	List<GroupTreeVo> treeUser(Integer userId);

	/**
	 * 校验用户是否有管理权限（管理员和创建人有权限）
	 * @param groupId
	 * @param userId
	 * @return
	 */
    boolean isGroupManger(Integer groupId, Integer userId);

	/**
	 * 校验是否为群创建人
	 * @param groupId
	 * @param userId
	 * @return
	 */
	boolean isGroupCreater(Integer groupId, Integer userId);

	/**
	 * 群组是否存在
	 * @param groupId
	 * @return
	 */
    boolean existGroup(Integer groupId);

	/**
	 * 用户是否存在
	 * @param userId
	 * @return
	 */
	boolean existUser(Integer userId);

	/**
	 * 新增群
	 * @param group
	 * @return
	 */
	boolean saveGroup(Group group);

	/**
	 * 更新群组
	 * @param group
	 * @return
	 */
	boolean updateGroup(Group group);

	/**
	 * 保存或者更新群
	 * @param group
	 * @return
	 */
	boolean saveOrUpdateGroup(Group group);

	/**
	 * 关闭群
	 * @param groupId
	 * @param creatorId
	 * @return
	 */
	boolean close(Integer groupId, Integer creatorId);

	/**
	 * 获取状态正常用户
	 */
	Group getGroupById(Integer groupId);

	/**
	 * 分页获取当前群和子群下所有用户
	 * @return
	 */
	IPage<UserVO> selectUserPageByParentId(Integer parentId, IPage<User> page);

	IPage<UserVO> selectUserPageAndCountByParentId(List<Integer> userIds, IPage<User> page);

	UserBO selectUserByParentId(Integer parentId);
	/**
	 * 获取当前群和子群的所有用户列表
     * @return
     */
	List<Integer> selectUserIdByParentId(Integer parentId);

	/**
	 * 删除群并设置用户和群的状态为已删除
	 * @param ids
	 * @return
	 */
    boolean removeGroupByIds(String ids);

	boolean isChildrenGroup(List<GroupTreeVo> groupTreeVos,Integer parentId,Integer checkId);

	/**
	 * 批量导入组织架构
	 * @param group
	 * @param excelFile
	 * @return
	 */
	String excelImport(Group group, String excelFile);

	/**
	 * 获取用户管理的群
	 * @param userId 用户ID
	 * @return
	 */
	List<Group> getUserManageGroups(Integer userId);

	/**
	 * 获取指定机构下用户管理的群
	 * @param userId
	 * @param groupIdentify
	 * @return
	 */
	List<Group> getUserManageGroups(Integer userId, String groupIdentify);

	/**
	 * 获取用户拥有的所有数据管理员权限的组织
	 * @param userId
	 * @return
	 */
	List<Group> getUserDataManageGroups(Integer userId);

	/**
	 * 获取指定机构下用户拥有的所有数据管理员权限的组织
	 * @param userId
	 * @return
	 */
	List<Group> getUserDataManageGroups(Integer userId, String groupIdentify);

	/**
	 * 生成机构唯一码
	 *
	 * @return
	 */
	String generateGroupCode();

	/**
	 * 查询改机构下所有子机构ID
	 * @param groups
	 * @param parentId
	 * @param groupList 子机构ids
	 * @return
	 */
	List<GroupTreeVo> getAllGroupIdByParentId(List<GroupTreeVo> groups, Integer parentId, List groupList);

	/**
	 * wx新增机构
	 * @param group
	 * @return
	 */
	boolean wxSaveGroup(GroupVO group);

	/**
	 * 删除机构及其子机构
	 * @param groupId
	 * @param userId
	 * @return
	 */
	boolean wxRemoveGroup(Integer groupId, Integer userId);

	/**
	 * 根据机构标识码获取一级机构
	 * @param groupIdentify
	 * @return
	 */
	Group getFirstGroup(String groupIdentify);

	/**
	 * 获取当前机构下一级的子机构
	 * @param parentId
	 * @return
	 */
	List<Group> getJuniorGroups(Integer parentId);
}
