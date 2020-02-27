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

import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.vo.GroupTreeVo;
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
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
	 * 新增群（包含中间表）
	 * @param group 群组
	 * @return
	 */
	boolean saveGroupMiddleTable(Group group);

	/**
	 * 更新群（包含中间表）
	 * @param group 群组
	 * @return
	 */
	boolean updateGroupMiddleTable(Group group);



	/**
	 * TableId 存在更新记录，否插入一条记录（包含中间表）
	 * @param group 群组
	 * @return
	 */
	boolean saveOrUpdateGroupMiddleTable(Group group);

	/**
	 * 删除群组和中间表
	 * @param ids 群组
	 * @return
	 */
	boolean removeGroupMiddleTableById(List<Integer> ids);

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
	 */
	List<GroupTreeVo> treeChildren();

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
}
