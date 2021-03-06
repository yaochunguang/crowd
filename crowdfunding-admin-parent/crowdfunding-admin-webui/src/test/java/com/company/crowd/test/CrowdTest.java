package com.company.crowd.test;

import com.company.crowd.mapper.AdminMapper;
import com.company.crowd.service.api.AdminService;
import com.company.crowd.util.CrowdUtil;
import com.company.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description:
 * @author: chunguang.yao
 * @date: 2020-10-15 0:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 注意：配置classpath的时候不能有空格
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testSaveAdminMulti() {
        for (int i = 0; i < 352; i++) {
            String pwd = CrowdUtil.md5("userPswd" + i);
            adminMapper.insert(new Admin(null, "loginAcct" + i, pwd, "userName" + i,
                    "email" + i + "@qq.com", null));
        }
    }

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "6666", "6666", "6666", "6666@123.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testInsert() {
        Admin admin = new Admin(null, "123", "123", "张三", "123@123.com", null);
        int count = adminMapper.insert(admin);
        System.out.println("受影响的行数：" + count);
    }

    @Test
    public void testDataSource() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testMd5() {
        System.out.println(CrowdUtil.md5("6666"));
    }
}
