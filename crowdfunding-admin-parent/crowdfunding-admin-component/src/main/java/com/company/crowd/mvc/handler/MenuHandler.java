package com.company.crowd.mvc.handler;

import com.company.crowd.service.api.MenuService;
import com.company.crowd.util.ResultEntity;
import com.company.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 菜单handler
 * @author: chunguang.yao
 * @date: 2020-12-03 0:04
 */
@Controller
public class MenuHandler {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getMenuTree() {
        List<Menu> allMenu = menuService.getAllMenu();
        Menu root = null;
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : allMenu) {
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }
        for (Menu menu : allMenu) {
            Integer pid = menu.getPid();
            if (pid == null) {
                root = menu;
                continue;
            }
            Menu fatherMenu = menuMap.get(pid);
            fatherMenu.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }

    /**
     * 新增菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/menu/save.json")
    public ResultEntity<Menu> saveMenu(Menu menu) {
        try {
            menuService.saveMenu(menu);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("/menu/update.json")
    public ResultEntity<Menu> updateMenu(Menu menu) {
        try {
            menuService.updateMenu(menu);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据id删除菜单
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/menu/remove.json")
    public ResultEntity<Menu> deleteMenuById(Integer id) {
        menuService.deleteMenuById(id);
        return ResultEntity.successWithoutData();
    }
}
