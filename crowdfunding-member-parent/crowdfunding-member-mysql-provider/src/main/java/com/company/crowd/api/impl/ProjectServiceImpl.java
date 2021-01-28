package com.company.crowd.api.impl;

import com.company.crowd.api.ProjectService;
import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.mapper.MemberConfirmInfoPOMapper;
import com.company.crowd.mapper.MemberLaunchInfoPOMapper;
import com.company.crowd.mapper.ProjectItemPicPOMapper;
import com.company.crowd.mapper.ProjectPOMapper;
import com.company.crowd.mapper.ReturnPOMapper;
import com.company.crowd.util.DateUtils;
import com.company.entity.po.MemberConfirmInfoPO;
import com.company.entity.po.MemberLaunchInfoPO;
import com.company.entity.po.ProjectPO;
import com.company.entity.po.ReturnPO;
import com.company.entity.vo.DetailProjectVO;
import com.company.entity.vo.MemberConfirmInfoVO;
import com.company.entity.vo.MemberLauchInfoVO;
import com.company.entity.vo.PortalTypeVO;
import com.company.entity.vo.ProjectVO;
import com.company.entity.vo.ReturnVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: yaochunguang
 * @date: 2021-01-19 16:17
 * @description: 项目service实现
 **/
@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;
    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;
    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
    @Autowired
    private ReturnPOMapper returnPOMapper;
    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        // 保存项目基本信息  -- begin
        // 创建空的ProjectPO对象
        ProjectPO projectPO = new ProjectPO();
        BeanUtils.copyProperties(projectVO, projectPO);
        projectPO.setMemberid(memberId);
        String dateStr = DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT);
        // 创建项目的日期
        projectPO.setCreatedate(dateStr);
        // 项目发起时间
        projectPO.setDeploydate(dateStr);
        // status: 0--即将开始
        projectPO.setStatus(0);
        // 为了能够获取到projectPO保存后的自增主键，需要到ProjectPOMapper.xml文件中进行相关设置
        projectPOMapper.insertSelective(projectPO);
        // 保存项目基本信息  -- end
        // 获取保存后自增的id
        Integer projectPOId = projectPO.getId();
        // 保存项目与分类的关联关系
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList, projectPOId);

        // 保存项目和标签的关联关系
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList, projectPOId);

        // 保存项目中的详情图片路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(projectPOId, detailPicturePathList);

        // 保存项目发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insertSelective(memberLaunchInfoPO);

        // 保存项目回报信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<>();
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            returnPOList.add(returnPO);
        }
        returnPOMapper.insertReturnPOBatch(returnPOList, projectPOId);

        // 保存项目确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insertSelective(memberConfirmInfoPO);
    }

    @Override
    public List<PortalTypeVO> selectPortalTypeVOList() {
        return projectPOMapper.selectPortalTypeVOList();
    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectId) {
        DetailProjectVO detailProjectVO = projectPOMapper.getDetailProjectVO(projectId);
        // 将status转换成对应的statusText
        int status = detailProjectVO.getStatus();
        switch (status) {
            case 0:
                detailProjectVO.setStatusText(CrowdConstant.STATUS_VERIFYING);
                break;
            case 1:
                detailProjectVO.setStatusText(CrowdConstant.STATUS_CROWDING);
                break;
            case 2:
                detailProjectVO.setStatusText(CrowdConstant.STATUS_CROWD_SUCCESS);
                break;
            case 4:
                detailProjectVO.setStatusText(CrowdConstant.STATUS_CLOSED);
                break;
            default:
                break;
        }
        // 根据deployDate计算lastDate
        String deployDateStr = detailProjectVO.getDeployDate();
        Date deployDate = DateUtils.convertDateStrToDate(deployDateStr, DateUtils.DATE_FORMAT);
        // 获取当前时间戳
        long currentTime = System.currentTimeMillis();
        // 获取筹集日期的时间戳
        long deployDateTime = deployDate.getTime();
        // 两个时间戳相减计算当前已经过去的时间
        long pastDays = (currentTime - deployDateTime) / 1000 / 60 / 60 / 24;
        // 获取总的众筹天数
        Integer totalDays = detailProjectVO.getDay();
        // 使用总的众筹天数减去已经过去的天数得到剩余天数
        Integer lastDay = (int) (totalDays - pastDays);
        detailProjectVO.setLastDay(lastDay);
        return detailProjectVO;
    }
}
