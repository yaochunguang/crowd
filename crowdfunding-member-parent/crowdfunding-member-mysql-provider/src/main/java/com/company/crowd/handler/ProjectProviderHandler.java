package com.company.crowd.handler;

import com.company.crowd.api.ProjectService;
import com.company.crowd.util.ResultEntity;
import com.company.entity.vo.DetailProjectVO;
import com.company.entity.vo.PortalTypeVO;
import com.company.entity.vo.ProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2021-01-19 16:17
 * @description: 项目handler
 **/
@RestController
public class ProjectProviderHandler {

    private Logger logger = LoggerFactory.getLogger(ProjectProviderHandler.class);

    @Autowired
    private ProjectService projectService;

    /**
     * 保存项目信息
     *
     * @param projectVO
     * @param memberId
     * @return
     */
    @RequestMapping("/save/project/vo/remote")
    public ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId) {
        try {
            projectService.saveProject(projectVO, memberId);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 按照分类查询首页项目信息
     *
     * @return
     */
    @RequestMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote() {
        try {
            List<PortalTypeVO> portalTypeVOList = projectService.selectPortalTypeVOList();
            return ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 通过项目id查询项目详细信息
     * @param projectId
     * @return
     */
    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId")Integer projectId) {
        try {
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }

}
