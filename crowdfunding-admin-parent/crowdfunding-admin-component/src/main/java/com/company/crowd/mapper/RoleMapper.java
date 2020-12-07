package com.company.crowd.mapper;

import com.company.entity.Role;
import com.company.entity.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {

    /**
     * 通过关键字查询角色列表
     * @param keyword
     * @return
     */
    List<Role> getRoleListByKeyword(String keyword);

    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

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