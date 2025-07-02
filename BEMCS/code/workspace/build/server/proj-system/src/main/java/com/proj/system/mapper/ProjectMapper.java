package com.proj.system.mapper;

import java.util.List;
import com.proj.system.domain.Project;

/**
 * 项目管理 数据层
 * 
 * @author proj
 */
public interface ProjectMapper {
    /**
     * 查询项目信息
     * 
     * @param projectId 项目ID
     * @return 项目信息
     */
    public Project selectProjectById(Long projectId);

    /**
     * 查询项目列表
     * 
     * @param project 项目信息
     * @return 项目集合
     */
    public List<Project> selectProjectList(Project project);

    /**
     * 新增项目
     * 
     * @param project 项目信息
     * @return 结果
     */
    public int insertProject(Project project);

    /**
     * 修改项目
     * 
     * @param project 项目信息
     * @return 结果
     */
    public int updateProject(Project project);

    /**
     * 批量删除项目
     * 
     * @param projectIds 需要删除的项目ID
     * @return 结果
     */
    public int deleteProjectByIds(Long[] projectIds);

    /**
     * 校验项目名称是否唯一
     * 
     * @param projectName 项目名称
     * @return 结果
     */
    public Project checkProjectNameUnique(String projectName);
} 