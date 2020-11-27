package com.company.crowd.test;

import com.company.crowd.service.api.RoleService;
import com.company.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author: yaochunguang
 * @date: 2020-11-27 17:28
 * @description: 角色Service测试
 **/
@RunWith(SpringJUnit4ClassRunner.class)
// 注意：配置classpath的时候不能有空格
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void addRole() {
        Role role = new Role();
        role.setName("管理员");
        roleService.addRole(role);
    }
}
