package com.company.crowd.api;

import com.company.entity.po.MemberPO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: yaochunguang
 * @date: 2021-01-04 17:05
 * @description: 会员service
 **/
public interface MemberService {

    /**
     * 通过登录账号获取会员信息
     * @param loginacct
     * @return
     */
    MemberPO getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    /**
     * 保存会员信息
     * @param memberPO
     */
    void saveMember(MemberPO memberPO);
}
