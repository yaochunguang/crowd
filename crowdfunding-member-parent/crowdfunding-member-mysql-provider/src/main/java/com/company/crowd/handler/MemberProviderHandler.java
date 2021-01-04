package com.company.crowd.handler;

import com.company.crowd.api.MemberService;
import com.company.crowd.util.ResultEntity;
import com.company.entity.po.MemberPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaochunguang
 * @date: 2021-01-04 17:12
 * @description: 会员handler
 **/
@RestController
public class MemberProviderHandler {

    private Logger logger = LoggerFactory.getLogger(MemberProviderHandler.class);

    @Autowired
    private MemberService memberService;


    /**
     * 提供远程调用给consumer
     * 这个方法是api项目中的MySQLRemoteService的getMemberPOByLoginAcctRemote具体实现
     * @param loginacct
     * @return
     */
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {
        try {
            // 调用本地service完成查询
            MemberPO memberPO = memberService.getMemberPOByLoginAcctRemote(loginacct);
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
