package com.company.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: yaochunguang
 * @date: 2021-01-05 19:43
 * @description: 首页
 **/
@Controller
public class PortalHandler {

    /**
     * 展示首页
     * @return
     */
    @RequestMapping("/")
    public String toShowPortal() {
        return "portal";
    }
}
