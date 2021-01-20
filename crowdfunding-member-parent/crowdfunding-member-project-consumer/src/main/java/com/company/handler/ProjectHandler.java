package com.company.handler;

import com.company.entity.vo.ProjectVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2021-01-20 19:41
 * @description: 项目handler
 **/
@Controller
public class ProjectHandler {


    /**
     * 保存项目基本信息
     * @param projectVO 接收除了上传图片之外的其他普通数据
     * @param headerPicture 接收上传的头图
     * @param detailPictureList 接收上传的详情图片
     * @param session 用来将收集了一部分数据的ProjectVO对象存入Session域
     * @param modelMap 用来在当前操作失败后返回上一个表单页面时携带提示消息
     * @return
     */
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPictureList,
                                       HttpSession session, ModelMap modelMap) {
        return "redirect:http://www.crowd.com/project/return/info/page";
    }
}
