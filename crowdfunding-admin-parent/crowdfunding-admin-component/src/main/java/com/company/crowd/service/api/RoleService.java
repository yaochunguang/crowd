package com.company.crowd.service.api;

import com.company.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2020-11-27 17:06
 * @description: 角色Service
 **/
public interface RoleService {

    /**
     * 通过关键字查询角色列表（带分页）
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    PageInfo<Role> getRoleListByKeyword(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 根据id获取角色
     * @param roleId
     * @return
     */
    Role getRoleById(Integer roleId);

    /**
     * 新增角色
     * @param role
     */
    void addRole(Role role);

    /**
     * 更新角色
     * @param role
     */
    void updateRole(Role role);

    /**
     * 通过角色id删除角色
     * @param roleId
     */
    void deleteRoleById(Integer roleId);

    /**
     * 根据id批量删除角色
     * @param roleIdList
     */
    void batchDeleteRole(List<Integer> roleIdList);

    /**
     * 通过管理员id查询已经分配的角色
     * @param adminId
     * @return
     */
    List<Role> selectAssignRoleByAdminId(Integer adminId);

    /**
     * 通过管理员id查询还没有分配的角色
     * @param adminId
     * @return
     */
    List<Role> selectUnAssignRoleByAdminId(Integer adminId);
}
