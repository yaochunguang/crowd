package com.company.crowd.mvc.handler;

import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.service.api.AdminService;
import com.company.entity.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author: yaochunguang
 * @date: 2020-11-19 11:03
 * @description: 管理员controller
 **/
@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/get/page.html")
    public String getAdminPage(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                               ModelMap modelMap) {
        PageInfo<Admin> pageInfo = adminService.selectAdminListByKeyword(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    /**
     * 管理员登录
     * @param loginAcct
     * @param userPswd
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/login.html")
    public String adminLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd, HttpSession session) {
        // 登录成功会返回管理员信息，失败会抛出异常
        Admin admin = adminService.adminLogin(loginAcct, userPswd);
        // 登录成功将admin放入session返回
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

    /**
     * 管理员退出登录
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/logout.html")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
}
