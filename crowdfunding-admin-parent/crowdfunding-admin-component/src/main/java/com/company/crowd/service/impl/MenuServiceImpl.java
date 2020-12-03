package com.company.crowd.service.impl;

import com.company.crowd.mapper.MenuMapper;
import com.company.crowd.service.api.MenuService;
import com.company.entity.Menu;
import com.company.entity.MenuExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: chunguang.yao
 * @date: 2020-12-03 0:03
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAllMenu() {
        MenuExample menuExample = new MenuExample();
        return menuMapper.selectByExample(menuExample);
    }
}
