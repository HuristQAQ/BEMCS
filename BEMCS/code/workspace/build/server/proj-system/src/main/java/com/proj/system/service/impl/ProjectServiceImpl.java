package com.proj.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proj.system.domain.Project;
import com.proj.system.mapper.ProjectMapper;
import com.proj.system.service.IProjectService;

/**
 * 项目管理 服务层实现
 * 
 * @author proj
 */
@Service
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 查询项目信息
     * 
     * @param projectId 项目ID
     * @return 项目信息
     */
    @Override
    public Project selectProjectById(Long projectId) {
        return projectMapper.selectProjectById(projectId);
    }

    /**
     * 查询项目列表
     * 
     * @param project 项目信息
     * @return 项目集合
     */
    @Override
    public List<Project> selectProjectList(Project project) {
        return projectMapper.selectProjectList(project);
    }

    /**
     * 新增项目
     * 
     * @param project 项目信息
     * @return 结果
     */
    @Override
    public int insertProject(Project project) {
        return projectMapper.insertProject(project);
    }

    /**
     * 修改项目
     * 
     * @param project 项目信息
     * @return 结果
     */
    @Override
    public int updateProject(Project project) {
        return projectMapper.updateProject(project);
    }

    /**
     * 批量删除项目
     * 
     * @param projectIds 需要删除的项目ID
     * @return 结果
     */
    @Override
    public int deleteProjectByIds(Long[] projectIds) {
        return projectMapper.deleteProjectByIds(projectIds);
    }

    /**
     * 校验项目名称是否唯一
     * 
     * @param project 项目信息
     * @return 结果
     */
    @Override
    public boolean checkProjectNameUnique(Project project) {
        Long projectId = project.getProjectId() == null ? -1L : project.getProjectId();
        Project info = projectMapper.checkProjectNameUnique(project.getProjectName());
        if (info != null && info.getProjectId().longValue() != projectId.longValue()) {
            return false;
        }
        return true;
    }
} 