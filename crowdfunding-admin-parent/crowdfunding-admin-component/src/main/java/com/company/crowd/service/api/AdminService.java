package com.company.crowd.service.api;

import com.company.entity.Admin;
import com.company.entity.AdminExample;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @description:
 * @author: chunguang.yao
 * @date: 2020-11-10 23:44
 */
public interface AdminService {

    /**
     * 删除管理员信息
     * @param adminId
     */
    void removeAdmin(Integer adminId);

    /**
     * 更新管理员信息
     * @param admin
     */
    void updateAdmin(Admin admin);

    /**
     * 通过id获取管理员
     * @param adminId
     * @return
     */
    Admin getAdminById(Integer adminId);

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
     * 管理员登录
     * @param loginAcct 登录账号
     * @param userPswd 登录密码
     * @return
     */
    Admin adminLogin(String loginAcct, String userPswd);

    /**
     * 分页查询
     * @param keyword
     * @return
     */
    PageInfo<Admin> selectAdminListByKeyword(String keyword, Integer pageNum, Integer pageSize);
}
