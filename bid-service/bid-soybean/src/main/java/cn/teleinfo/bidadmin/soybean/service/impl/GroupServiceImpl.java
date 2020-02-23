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
package cn.teleinfo.bidadmin.soybean.service.impl;

import cn.teleinfo.bidadmin.soybean.entity.ChildrenGroup;
import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.ParentGroup;
import cn.teleinfo.bidadmin.soybean.mapper.GroupMapper;
import cn.teleinfo.bidadmin.soybean.service.IChildrenGroupService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IParentGroupService;
import cn.teleinfo.bidadmin.soybean.vo.GroupVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务实现类
 *
 * @author Blade
 * @since 2020-02-21
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    @Autowired
    private IParentGroupService parentGroupService;

    @Autowired
    private IChildrenGroupService childrenGroupService;

    @Override
    public IPage<GroupVO> selectGroupPage(IPage<GroupVO> page, GroupVO group) {
        return page.setRecords(baseMapper.selectGroupPage(page, group));
    }

    @Override
    @Transactional
    public boolean saveGroupMiddleTable(Group group) {
        //未指定父群组, 直接保存群组
        Integer parentId = group.getParentGroup();
        if (parentId == null || parentId < 1) {
            group.setParentGroup(null);
            return save(group);
        }
        //校验父ID是否存在
        if (getById(parentId) == null) {
            return false;
        }
        //保存群组
        save(group);
        //获取群ID
        Integer groupId = group.getId();
        //保存父群组
        ParentGroup parentGroup = new ParentGroup();
        parentGroup.setGroupId(groupId);
        parentGroup.setParentId(parentId);
        parentGroupService.save(parentGroup);
        //保存子群组
        ChildrenGroup childrenGroup = new ChildrenGroup();
        childrenGroup.setChildId(groupId);
        childrenGroup.setGroupId(parentId);
        childrenGroupService.save(childrenGroup);
        return true;
    }


    @Override
    @Transactional
    public boolean saveOrUpdateGroupMiddleTable(Group group) {
        //新增群组
        if (group.getId() == null) {
            saveGroupMiddleTable(group);
        } else {
            //更新群
            updateGroupMiddleTable(group);
        }
        return true;
    }

    @Override
    public boolean removeGroupMiddleTableById(List<Integer> ids) {
        if (ids.isEmpty()) {
            return false;
        }
        for (Integer id : ids) {
            Group group = getById(id);
            Integer groupId = group.getId();
            //删除子群组
            removeById(groupId);
            //删除父群组
            ParentGroup parentGroup = new ParentGroup();
            parentGroup.setGroupId(groupId);
            parentGroupService.remove(Condition.getQueryWrapper(parentGroup));
            //删除子群组
            ChildrenGroup childrenGroup = new ChildrenGroup();
            childrenGroup.setChildId(groupId);
            childrenGroupService.remove(Condition.getQueryWrapper(childrenGroup));
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateGroupMiddleTable(Group group) {
        //更新群组
        //如果用户去掉父Id则删除父群组和子群组
        Integer parentId = group.getParentGroup();
        if (parentId == null || parentId < 1) {
            //更新群组
            updateById(group);
            //删除父群主
            Integer groupId = group.getId();
            ParentGroup parentGroup = new ParentGroup();
            parentGroup.setGroupId(groupId);
            parentGroupService.remove(Condition.getQueryWrapper(parentGroup));
            //删除子群主
            ChildrenGroup childrenGroup = new ChildrenGroup();
            childrenGroup.setChildId(groupId);
            childrenGroupService.remove(Condition.getQueryWrapper(childrenGroup));
            return true;
        }

        //校验父ID是否存在
        if (getById(parentId) == null) {
            return false;
        }
        //更新群组
        updateById(group);
        //获取群组ID
        Integer groupId = group.getId();
        //保存父群组
        ParentGroup parentGroup = new ParentGroup();
        parentGroup.setGroupId(groupId);
        parentGroup.setParentId(parentId);
        //不存在父群主则创建父群组
        if (parentGroupService.getOne(Condition.getQueryWrapper(parentGroup)) == null) {
            parentGroupService.remove(new LambdaQueryWrapper<ParentGroup>().eq(ParentGroup::getGroupId, groupId));
            parentGroupService.save(parentGroup);
        }
        //不存在子群主则创建子群组
        ChildrenGroup childrenGroup = new ChildrenGroup();
        childrenGroup.setChildId(groupId);
        childrenGroup.setGroupId(parentId);
        if (childrenGroupService.getOne(Condition.getQueryWrapper(childrenGroup)) == null) {
            childrenGroupService.remove(new QueryWrapper<ChildrenGroup>().lambda().eq(ChildrenGroup::getChildId, groupId));
            childrenGroupService.save(childrenGroup);
        }
        return true;
    }

}
