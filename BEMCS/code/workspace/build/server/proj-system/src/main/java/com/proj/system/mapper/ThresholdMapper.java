package com.proj.system.mapper;

import com.proj.system.domain.Threshold;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 环境参数阈值Mapper接口
 */
public interface ThresholdMapper {
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
    int checkDataTypeUnique(@Param("dataType") Integer dataType, @Param("thresholdId") Long thresholdId);
} 