package com.company.crowd.mvc.handler;

import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.service.api.AdminService;
import com.company.crowd.util.CrowdUtil;
import com.company.crowd.util.StringUtils;
import com.company.entity.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 这种写法主要是为了演练restful风格，使用PathVariable来接受参数
     * @param adminId
     * @param pageNum
     * @param keyword
     * @return
     */
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeAdmin(@PathVariable("adminId")Integer adminId, @PathVariable("pageNum")Integer pageNum,
                              @PathVariable("keyword")String keyword){
        adminService.removeAdmin(adminId);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/update.html")
    public String updateAdmin(Admin admin, @RequestParam("oldPassword")String oldPassword,
                              @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword) {
        if (StringUtils.isNotEmpty(oldPassword) && !oldPassword.equals(admin.getUserPswd())) {
            admin.setUserPswd(CrowdUtil.md5(admin.getUserPswd()));
        }
        adminService.updateAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/to/edit/page.html")
    public String editAdmin(@RequestParam("adminId")Integer adminId, ModelMap modelMap) {
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin", admin);
        return "admin-edit";
    }

    @RequestMapping("/admin/save.html")
    public String addAdmin(Admin admin, ModelMap modelMap) {
        modelMap.addAttribute("admin", admin);
        adminService.saveAdmin(admin);
        // 重定向到分页页面，使用重定向是为了避免刷新浏览器重复提交表单
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/get/page.html")
    public String getAdminPage(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                               ModelMap modelMap, HttpSession session) {
        PageInfo<Admin> pageInfo = adminService.selectAdminListByKeyword(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        session.setAttribute("keyword", keyword);
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
