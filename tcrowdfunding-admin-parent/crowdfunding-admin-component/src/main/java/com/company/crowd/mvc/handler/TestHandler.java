package com.company.crowd.mvc.handler;

import com.company.crowd.service.api.AdminService;
import com.company.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2020-11-14 17:14
 * @description: 测试
 **/
@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap) {
        List<Admin> adminList = adminService.getAdminList();
        modelMap.addAttribute("adminList", adminList);
        return "target";
    }
}
