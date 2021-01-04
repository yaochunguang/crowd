package com.company.crowd.api;

import com.company.crowd.util.ResultEntity;
import com.company.entity.po.MemberPO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: yaochunguang
 * @date: 2021-01-04 17:05
 * @description: 会员service
 **/
public interface MemberService {

    MemberPO getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);
}
