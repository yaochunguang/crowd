package com.company.crowd.mvc.handler;

import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.service.api.RoleService;
import com.company.crowd.util.ResultEntity;
import com.company.entity.Role;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author: yaochunguang
 * @date: 2020-11-27 17:36
 * @description: 角色Controller
 **/
@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    /*@RequestMapping("/role/get/page.html")
    public String getRolePage(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                              ModelMap modelMap, HttpSession session) {
        PageInfo<Role> pageInfo = roleService.getRoleListByKeyword(pageNum, pageSize, keyword);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        // 回显查询条件
        session.setAttribute("keyword", keyword);
        return "role-page";
    }*/

    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
            @RequestParam(value="keyword", defaultValue="") String keyword) {
        // 调用Service方法获取分页数据
        PageInfo<Role> pageInfo = roleService.getRoleListByKeyword(pageNum, pageSize, keyword);
        // 封装到ResultEntity对象中返回（如果上面的操作抛出异常，交给异常映射机制处理）
        return ResultEntity.successWithData(pageInfo);
    }
}
