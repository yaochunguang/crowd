package com.company.crowd.test;

import com.company.crowd.api.ProjectService;
import com.company.entity.vo.DetailProjectVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

/**
 * @author: yaochunguang
 * @date: 2021-01-29 10:28
 * @description: 项目service测试
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void testGetProjectVO() {
        DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(38);
        System.out.println(detailProjectVO);
    }
}
