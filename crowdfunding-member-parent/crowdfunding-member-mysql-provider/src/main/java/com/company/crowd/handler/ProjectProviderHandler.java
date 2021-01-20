package com.company.crowd.handler;

import com.company.crowd.api.ProjectService;
import com.company.crowd.util.ResultEntity;
import com.company.entity.vo.ProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
