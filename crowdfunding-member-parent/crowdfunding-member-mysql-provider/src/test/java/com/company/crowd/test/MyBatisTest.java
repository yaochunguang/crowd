package com.company.crowd.test;

import com.company.crowd.mapper.MemberPOMapper;
import com.company.entity.po.MemberPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: yaochunguang
 * @date: 2021-01-04 16:24
 * @description: MyBatis测试
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest {

    private Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Test
    public void testInsertMember() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String source = "123123";
        String encode = passwordEncoder.encode(source);
        MemberPO memberPO = new MemberPO();
        memberPO.setLoginacct("jack");
        memberPO.setUserpswd(encode);
        memberPO.setUsername("杰克");
        memberPO.setEmail("jack@qq.com");
        memberPO.setAuthstatus(1);
        memberPO.setUsertype(1);
        memberPO.setRealname("杰克");
        memberPO.setCardnum("123");
        memberPO.setAccttype(2);
        memberPOMapper.insert(memberPO);
        logger.debug("插入会员记录成功");
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.debug("获取到连接： " + connection.toString());
    }
}
