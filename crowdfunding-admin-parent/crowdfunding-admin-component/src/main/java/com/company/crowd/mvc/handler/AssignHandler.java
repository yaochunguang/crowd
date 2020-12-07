package com.company.crowd.mvc.handler;

import com.company.crowd.service.api.RoleService;
import com.company.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2020-12-07 19:45
 * @description: 管理员分配角色
 **/
@Controller
public class AssignHandler {

    @Autowired
    private RoleService roleService;

    /**
     * 前往管理员分配角色的页面
     * @return
     */
    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAdminAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
        // 查询已经分配的角色
        List<Role> assignedRoleList = roleService.selectAssignRoleByAdminId(adminId);
        // 查询还没有分配的角色
        List<Role> unAssignedRoleList = roleService.selectUnAssignRoleByAdminId(adminId);
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assign-role";
    }
}
