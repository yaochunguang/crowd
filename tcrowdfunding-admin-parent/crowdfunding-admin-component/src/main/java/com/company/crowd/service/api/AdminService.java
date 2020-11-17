package com.company.crowd.service.api;

import com.company.entity.Admin;
import com.company.entity.AdminExample;

import java.util.List;

/**
 * @description:
 * @author: chunguang.yao
 * @date: 2020-11-10 23:44
 */
public interface AdminService {

    /**
     * 保存用户
     * @param admin
     */
    void saveAdmin(Admin admin);

    /**
     * 获取所有用户
     * @return
     */
    List<Admin> getAdminList();

    /**
     * 通过id获取用户信息
     * @return
     */
    Admin getAdminById();
}
