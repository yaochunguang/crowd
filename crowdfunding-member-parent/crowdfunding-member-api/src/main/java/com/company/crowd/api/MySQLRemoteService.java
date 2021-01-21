package com.company.crowd.api;

import com.company.crowd.util.ResultEntity;
import com.company.entity.po.MemberPO;
import com.company.entity.vo.ProjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: yaochunguang
 * @date: 2021-01-04 16:55
 * @description: MySql远程服务
 **/
@FeignClient("crowd-mysql")
public interface MySQLRemoteService {

    /**
     * 通过登录账号获取用会员信息
     * @param loginacct
     * @return
     */
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    /**
     * 保存会员
     * @param memberPO
     */
    @RequestMapping("/save/member/remote")
    ResultEntity<String> saveMember(@RequestBody MemberPO memberPO);

    /**
     * 保存项目信息
     * @param projectVO
     * @param memberId
     * @return
     */
    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);
}
