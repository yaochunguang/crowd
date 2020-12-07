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

    /**
     * 新增菜单
     * @param menu
     */
    void saveMenu(Menu menu);

    /**
     * 更新菜单
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * 通过id删除菜单
     * @param id
     */
    void deleteMenuById(Integer id);
}
