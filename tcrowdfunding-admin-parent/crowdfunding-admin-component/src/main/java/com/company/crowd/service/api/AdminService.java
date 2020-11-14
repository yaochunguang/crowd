package com.company.crowd.service.api;

import com.company.entity.Admin;

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
}
