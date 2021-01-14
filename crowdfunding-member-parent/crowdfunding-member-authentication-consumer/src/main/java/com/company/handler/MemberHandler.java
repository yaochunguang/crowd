package com.company.handler;

import com.company.config.ShortMessageProperties;
import com.company.crowd.api.MySQLRemoteService;
import com.company.crowd.api.RedisRemoteService;
import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.util.CrowdUtil;
import com.company.crowd.util.ResultEntity;
import com.company.entity.po.MemberPO;
import com.company.entity.vo.MemberLoginVO;
import com.company.entity.vo.MemberVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: yaochunguang
 * @date: 2021-01-08 10:17
 * @description: 会员handler
 **/
@Controller
public class MemberHandler {

    @Autowired
    private RedisRemoteService redisRemoteService;
    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @Autowired
    private ShortMessageProperties shortMessageProperties;

    /**
     * 会员退出登录
     * @param session
     * @return
     */
    @RequestMapping("/auth/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 会员登录
     * @param loginacct
     * @param userpswd
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/auth/member/do/login")
    public String login(@RequestParam("loginacct") String loginacct, @RequestParam("userpswd") String userpswd,
                        ModelMap modelMap, HttpSession session) {
        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
            // 登录失败
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-login";
        }
        MemberPO dbMemberPO = resultEntity.getData();
        if (dbMemberPO == null) {
            // 用户名不存在，请先注册
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_MEMBER_NOTEXISTS);
            return "member-login";
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(userpswd, dbMemberPO.getUserpswd());
        // 不匹配
        if (!matches) {
            // 用户名或者密码错误
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        // 将用户信息存到session
        MemberLoginVO memberLoginVO = new MemberLoginVO(dbMemberPO.getId(), dbMemberPO.getUsername(), dbMemberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);
        // 登录成功，跳转到首页
        return "redirect:/auth/member/to/center/page";
    }

    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, ModelMap modelMap) {
        // 校验验证码是否正确 -- begin
        /*String phoneNum = memberVO.getPhoneNum();
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        ResultEntity<String> codeResultEntity = redisRemoteService.getRedisStringValueByKeyRemote(key);
        String result = codeResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, codeResultEntity.getMessage());
            return "member-reg";
        }
        String redisCode = codeResultEntity.getData();
        if(redisCode == null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }
        String formCode = memberVO.getCode();
        if(!Objects.equals(formCode, redisCode)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }
        redisRemoteService.removeRedisKeyRemote(key);*/
        // 校验验证码是否正确 -- end
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodeUserpswd = bCryptPasswordEncoder.encode(memberVO.getUserpswd());
        memberVO.setUserpswd(encodeUserpswd);
        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);
        ResultEntity<String> saveMemberResultEntity = mySQLRemoteService.saveMember(memberPO);
        if (ResultEntity.FAILED.equals(saveMemberResultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberResultEntity.getMessage());
            return "member-reg";
        }
        // 使用重定向避免刷新浏览器导致重新执行注册流程
        return "redirect:/auth/member/to/login/page";
    }

    /**
     * 发送短信获取验证码并存到redis
     * @param phoneNum
     * @return
     */
    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(shortMessageProperties.getHost(), shortMessageProperties.getPath(), shortMessageProperties.getMethod(), phoneNum,
                shortMessageProperties.getAppCode(), shortMessageProperties.getSign(), shortMessageProperties.getSkin());
        if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
            String code = sendMessageResultEntity.getData();
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 15, TimeUnit.MINUTES);
            if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())) {
                return ResultEntity.successWithoutData();
            } else {
                return saveCodeResultEntity;
            }
        } else {
            return sendMessageResultEntity;
        }
    }

}
