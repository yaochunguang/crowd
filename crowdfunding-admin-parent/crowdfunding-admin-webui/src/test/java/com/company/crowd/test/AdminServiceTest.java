package com.company.crowd.test;

import com.company.crowd.service.api.AdminService;
import com.company.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.midi.Soundbank;

/**
 * @author: yaochunguang
 * @date: 2020-11-19 11:21
 * @description: 管理员测试类
 **/
@RunWith(SpringJUnit4ClassRunner.class)
// 注意：配置classpath的时候不能有空格
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void testAdminLogin() {
        String loginAcct = "6666";
        String userPswd = "6666";
        Admin admin = adminService.adminLogin(loginAcct, userPswd);
        if (admin != null) {
            System.out.println("登录成功!!!" + admin);
        } else {
            System.out.println("登录失败");
        }
    }
}
