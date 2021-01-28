package com.company.crowd.api;

import com.company.entity.vo.DetailProjectVO;
import com.company.entity.vo.PortalTypeVO;
import com.company.entity.vo.ProjectVO;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 通过项目id查询项目详细信息
     * @param projectId
     * @return
     */
    DetailProjectVO getDetailProjectVO(@Param("projectId") Integer projectId);
}
