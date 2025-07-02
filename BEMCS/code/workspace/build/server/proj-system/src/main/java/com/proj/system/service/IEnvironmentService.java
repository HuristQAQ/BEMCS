package com.proj.system.service;

import java.util.List;
import com.proj.system.domain.Environment;

/**
 * 环境数据Service接口
 */
public interface IEnvironmentService {
    /**
     * 查询环境数据
     */
    public Environment selectEnvironmentByDataId(Long dataId);

    /**
     * 查询环境数据列表
     */
    public List<Environment> selectEnvironmentList(Environment environment);

    /**
     * 新增环境数据
     */
    public int insertEnvironment(Environment environment);

    /**
     * 修改环境数据
     */
    public int updateEnvironment(Environment environment);

    /**
     * 批量删除环境数据
     */
    public int deleteEnvironmentByDataIds(Long[] dataIds);

    /**
     * 删除环境数据信息
     */
    public int deleteEnvironmentByDataId(Long dataId);

    /**
     * 查询实时环境数据
     */
    public List<Environment> selectCurrentEnvironment();

    /**
     * 查询历史环境数据
     */
    public List<Environment> selectHistoryEnvironment(Environment environment);

    /**
     * 查询图表数据
     */
    public List<Environment> selectChartData(Environment environment);

    /**
     * 生成新的环境数据
     */
    public void generateNewEnvironmentData();
} 