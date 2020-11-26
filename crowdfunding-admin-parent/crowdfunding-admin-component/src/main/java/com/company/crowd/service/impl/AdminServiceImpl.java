package com.company.crowd.service.impl;

import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.exception.LoginAcctAlreadyInUseException;
import com.company.crowd.exception.LoginFailedException;
import com.company.crowd.mapper.AdminMapper;
import com.company.crowd.service.api.AdminService;
import com.company.crowd.util.CrowdUtil;
import com.company.crowd.util.DateUtils;
import com.company.entity.Admin;
import com.company.entity.AdminExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: chunguang.yao
 * @date: 2020-11-10 23:45
 */
@Service
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void removeAdmin(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {
        // “Selective”表示有选择的更新，对于null值的字段不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            logger.info("异常全类名=" + e.getClass().getName());
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public List<Admin> getAdminList() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin adminLogin(String loginAcct, String userPswd) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (adminList.size() > 1) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = adminList.get(0);
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 取出数据库的密码进行校验
        String dbUserPswd = admin.getUserPswd();
        String formUserPswd = CrowdUtil.md5(userPswd);
        if (!Objects.equals(dbUserPswd, formUserPswd)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }

    @Override
    public void saveAdmin(Admin admin) {
        String dateStr = DateUtils.formatDate(new Date(), DateUtils.DATETIME_FORMAT);
        admin.setCreateTime(dateStr);
        // 对密码进行md5加密
        String userPswdSrc = admin.getUserPswd();
        String userPswdMd5 = CrowdUtil.md5(userPswdSrc);
        admin.setUserPswd(userPswdMd5);
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(admin.getLoginAcct());
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList != null && adminList.size() > 0) {
            throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }
        adminMapper.insert(admin);
    }

    @Override
    public PageInfo<Admin> selectAdminListByKeyword(String keyword, Integer pageNum, Integer pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> adminList = adminMapper.selectAdminListByKeyword(keyword);
        PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
        return pageInfo;
    }
}
