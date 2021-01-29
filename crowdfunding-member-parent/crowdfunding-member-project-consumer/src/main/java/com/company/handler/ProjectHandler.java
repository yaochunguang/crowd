package com.company.handler;

import com.company.crowd.api.MySQLRemoteService;
import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.util.CrowdUtil;
import com.company.crowd.util.ResultEntity;
import com.company.entity.vo.DetailProjectVO;
import com.company.entity.vo.MemberConfirmInfoVO;
import com.company.entity.vo.MemberLoginVO;
import com.company.entity.vo.ProjectVO;
import com.company.entity.vo.ReturnVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2021-01-20 19:41
 * @description: 项目handler
 **/
@Controller
public class ProjectHandler {

    private Logger logger = LoggerFactory.getLogger(ProjectHandler.class);
    /**
     * 图片上传地址
     */
    @Value("${file.upload.path}")
    private String filePath;

    /**
     * 文件存储地址
     */
    @Value("${file.upload.diskPath}")
    private String diskPath;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    /**
     * 查看项目详情
     * @param projectId
     * @param modelMap
     * @return
     */
    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer projectId, ModelMap modelMap) {
        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            modelMap.addAttribute("detailProjectVO", resultEntity.getData());
        }
        return "project-show-detail.html";
    }

    /**
     * 保存项目基本信息
     *
     * @param projectVO         接收除了上传图片之外的其他普通数据
     * @param headerPicture     接收上传的头图
     * @param detailPictureList 接收上传的详情图片
     * @param session           用来将收集了一部分数据的ProjectVO对象存入Session域
     * @param modelMap          用来在当前操作失败后返回上一个表单页面时携带提示消息
     * @return
     */
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPictureList,
                                       HttpSession session, ModelMap modelMap) {
        boolean headerPictureEmpty = headerPicture.isEmpty();
        if (headerPictureEmpty) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";
        }
        // 上传头图
        ResultEntity<String> uploadImageResultEntity = CrowdUtil.uploadImage(headerPicture, filePath, diskPath);
        String uploadImageResult = uploadImageResultEntity.getResult();
        // 判断头图上传是否成功
        if (ResultEntity.SUCCESS.equals(uploadImageResult)) {
            String headerPicturePath = uploadImageResultEntity.getData();
            projectVO.setHeaderPicturePath(headerPicturePath);
        } else {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        }
        // 上传详情图片
        List<String> detailPicturePathList = new ArrayList<>();
        // 检查detailPictureList是否有效
        if (detailPictureList == null || detailPictureList.size() == 0) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-launch";
        }
        // 遍历detailPictureList进行上传详情图
        for (MultipartFile detailPicture : detailPictureList) {
            if (detailPicture.isEmpty()) {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }
            // 执行上传
            ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadImage(detailPicture, filePath, diskPath);
            String detailUploadResult = detailUploadResultEntity.getResult();
            if (ResultEntity.SUCCESS.equals(detailUploadResult)) {
                detailPicturePathList.add(detailUploadResultEntity.getData());
            } else {
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
        }
        // 将详情图片路径放入projectVO对象
        projectVO.setDetailPicturePathList(detailPicturePathList);
        // 将projectVO存入session域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
        return "redirect:http://www.crowd.com/project/return/info/page";
    }

    /**
     * 检查是否已经添加回报信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/check/return.json")
    public ResultEntity<String> checkReturnInfo(HttpSession session) {
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        if (projectVO == null) {
            return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        if (returnVOList == null || returnVOList.size() == 0) {
            return ResultEntity.failed("请先添加回报信息！");
        }
        return ResultEntity.successWithoutData();
    }

    /**
     * 保存回报信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {
        try {
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
            if (projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();
            if (returnVOList == null || returnVOList.size() == 0) {
                returnVOList = new ArrayList<>();
                projectVO.setReturnVOList(returnVOList);
            }
            // 收集表单数据
            returnVOList.add(returnVO);
            // 将projectVO存入session域
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 上传项目回报图片
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile returnPicture) {
        ResultEntity<String> returnPictureResultEntity = CrowdUtil.uploadImage(returnPicture, filePath, diskPath);
        return returnPictureResultEntity;
    }

    /**
     * 提交保存项目信息到数据库
     *
     * @return
     */
    @RequestMapping("/create/confirm")
    public String saveConfirm(MemberConfirmInfoVO memberConfirmInfoVO, ModelMap modelMap, HttpSession session) {
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        if (projectVO == null) {
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        // 从session域中获取当前登录的用户
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
        // 调用远程方法保存信息到数据库
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO, memberId);
        if (ResultEntity.FAILED.equals(saveResultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());
            return "project-confirm";
        }
        // 将临时的projectVO对象从session域中移除
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        // 如果远程保存成功，则跳转到最终完成页面
        return "redirect:http://www.crowd.com/project/create/success";
    }
}
