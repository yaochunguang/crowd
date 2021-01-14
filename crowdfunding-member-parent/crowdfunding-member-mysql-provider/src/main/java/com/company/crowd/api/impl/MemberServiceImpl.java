package com.company.crowd.api.impl;

import com.company.crowd.api.MemberService;
import com.company.crowd.mapper.MemberPOMapper;
import com.company.crowd.util.ResultEntity;
import com.company.entity.po.MemberPO;
import com.company.entity.po.MemberPOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2021-01-04 17:08
 * @description: 会员service
 **/
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcctRemote(String loginacct) {
        MemberPOExample example = new MemberPOExample();
        MemberPOExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        List<MemberPO> memberPOList = memberPOMapper.selectByExample(example);
        if (memberPOList == null || memberPOList.size() == 0) {
            return null;
        }
        return memberPOList.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, readOnly = false)
    @Override
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
