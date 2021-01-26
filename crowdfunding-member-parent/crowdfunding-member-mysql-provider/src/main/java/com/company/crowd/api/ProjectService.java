package com.company.crowd.api;

import com.company.entity.vo.PortalTypeVO;
import com.company.entity.vo.ProjectVO;

import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2021-01-19 16:17
 * @description: 项目service
 **/
public interface ProjectService {

    /**
     * 保存项目信息
     * @param projectVO
     * @param memberId
     */
    void saveProject(ProjectVO projectVO, Integer memberId);

    /**
     * 按照分类查询首页项目信息
     * @return
     */
    List<PortalTypeVO> selectPortalTypeVOList();
}
