package com.company.crowd.service.api;

import com.company.entity.Menu;

import java.util.List;

/**
 * @description: 菜单Service
 * @author: chunguang.yao
 * @date: 2020-12-03 0:02
 */
public interface MenuService {

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> getAllMenu();
}
