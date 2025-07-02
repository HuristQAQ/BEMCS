package com.proj.system.service;

import com.proj.system.domain.Threshold;
import java.util.List;

/**
 * 环境参数阈值Service接口
 */
public interface IThresholdService {
    /** 查询阈值列表（不分页） */
    List<Threshold> selectThresholdList(Threshold threshold);

    /** 查询单个阈值 */
    Threshold selectThresholdById(Long thresholdId);

    /** 新增阈值 */
    int insertThreshold(Threshold threshold);

    /** 修改阈值 */
    int updateThreshold(Threshold threshold);

    /** 删除阈值 */
    int deleteThresholdById(Long thresholdId);

    /** 校验数据类型唯一 */
    boolean checkDataTypeUnique(Integer dataType, Long thresholdId);
} 