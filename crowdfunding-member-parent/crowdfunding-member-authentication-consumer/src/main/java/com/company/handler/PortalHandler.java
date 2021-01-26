package com.company.handler;

import com.company.crowd.api.MySQLRemoteService;
import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.util.ResultEntity;
import com.company.entity.vo.PortalTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2021-01-05 19:43
 * @description: 首页
 **/
@Controller
public class PortalHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    /**
     * 展示首页
     * @return
     */
    @RequestMapping("/")
    public String toShowPortal(ModelMap modelMap) {
        // 查询首页分类的项目信息
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            List<PortalTypeVO> portalTypeVOList = resultEntity.getData();
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA, portalTypeVOList);
        }
        return "portal";
    }
}
