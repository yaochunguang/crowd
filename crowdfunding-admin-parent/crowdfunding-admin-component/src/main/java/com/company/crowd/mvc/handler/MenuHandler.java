package com.company.crowd.mvc.handler;

import com.company.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @description: 菜单handler
 * @author: chunguang.yao
 * @date: 2020-12-03 0:04
 */
@Controller
public class MenuHandler {

    @Autowired
    private MenuService menuService;
}
