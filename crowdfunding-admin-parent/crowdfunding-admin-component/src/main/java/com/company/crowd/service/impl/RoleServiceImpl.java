package com.company.crowd.service.impl;

import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.exception.LoginAcctAlreadyInUseException;
import com.company.crowd.exception.RoleExistException;
import com.company.crowd.mapper.RoleMapper;
import com.company.crowd.service.api.RoleService;
import com.company.entity.Role;
import com.company.entity.RoleExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2020-11-27 17:10
 * @description: 角色sevice实现
 **/
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getRoleListByKeyword(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roleList = roleMapper.getRoleListByKeyword(keyword);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

    @Override
    public Role getRoleById(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public void addRole(Role role) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(role.getName());
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if (roles != null && roles.size() > 0) {
            throw new RoleExistException(CrowdConstant.MESSAGE_ROLE_NAME_EXIST);
        }
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        try {
            roleMapper.updateByPrimaryKeySelective(role);
        } catch (Exception e) {
            logger.info("异常全类名=" + e.getClass().getName());
            if (e instanceof DuplicateKeyException) {
                throw new RoleExistException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void deleteRoleById(Integer roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public void batchDeleteRole(List<Integer> roleIdList) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(roleIdList);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> selectAssignRoleByAdminId(Integer adminId) {
        return roleMapper.selectAssignRoleByAdminId(adminId);
    }

    @Override
    public List<Role> selectUnAssignRoleByAdminId(Integer adminId) {
        return roleMapper.selectUnAssignRoleByAdminId(adminId);
    }

    @Override
    public void saveAdminAssignRoleRelationShip(Integer adminId, List<Integer> roleIdList) {
        roleMapper.deleteOldRelationShipByAdminId(adminId);
        if (roleIdList != null && roleIdList.size() > 0) {
            roleMapper.insertNewRelationShip(adminId, roleIdList);
        }
    }
}
