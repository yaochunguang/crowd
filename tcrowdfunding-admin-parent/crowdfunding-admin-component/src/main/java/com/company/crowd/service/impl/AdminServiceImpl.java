package com.company.crowd.service.impl;

import com.company.crowd.mapper.AdminMapper;
import com.company.crowd.service.api.AdminService;
import com.company.entity.Admin;
import com.company.entity.AdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: chunguang.yao
 * @date: 2020-11-10 23:45
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> getAdminList() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }
}
