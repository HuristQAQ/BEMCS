package com.proj.system.mapper;

import java.util.List;
import com.proj.system.domain.Environment;

/**
 * 环境数据Mapper接口
 */
public interface EnvironmentMapper {
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
     * 删除环境数据
     */
    public int deleteEnvironmentByDataId(Long dataId);

    /**
     * 批量删除环境数据
     */
    public int deleteEnvironmentByDataIds(Long[] dataIds);

    /**
     * 查询实时环境数据
     */
    public List<Environment> selectCurrentEnvironment();

    /**
     * 查询历史环境数据
     */
    public List<Environment> selectHistoryEnvironment(Environment environment);

    /**
     * 更新所有实时数据为非实时
     */
    public int updateAllCurrentToHistory();

    /**
     * 查询图表数据
     */
    public List<Environment> selectChartData(Environment environment);
} 