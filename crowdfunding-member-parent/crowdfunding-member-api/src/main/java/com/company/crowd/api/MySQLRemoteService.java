package com.company.crowd.api;

import com.company.crowd.util.ResultEntity;
import com.company.entity.po.MemberPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: yaochunguang
 * @date: 2021-01-04 16:55
 * @description: MySql远程服务
 **/
@FeignClient("crowd-mysql")
public interface MySQLRemoteService {

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);
}
