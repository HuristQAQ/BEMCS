package com.proj.system.service.impl;

import com.proj.system.domain.Threshold;
import com.proj.system.mapper.ThresholdMapper;
import com.proj.system.service.IThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ThresholdServiceImpl implements IThresholdService {
    @Autowired
    private ThresholdMapper thresholdMapper;

    @Override
    public List<Threshold> selectThresholdList(Threshold threshold) {
        return thresholdMapper.selectThresholdList(threshold);
    }

    @Override
    public Threshold selectThresholdById(Long thresholdId) {
        return thresholdMapper.selectThresholdById(thresholdId);
    }

    @Override
    @Transactional
    public int insertThreshold(Threshold threshold) {
        // 校验唯一性
        if (!checkDataTypeUnique(threshold.getDataType(), null)) {
            throw new RuntimeException("数据类型已存在");
        }
        if (threshold.getMaxValue().compareTo(threshold.getMinValue()) <= 0) {
            throw new RuntimeException("最大值必须大于最小值");
        }
        return thresholdMapper.insertThreshold(threshold);
    }

    @Override
    @Transactional
    public int updateThreshold(Threshold threshold) {
        // 校验唯一性
        if (!checkDataTypeUnique(threshold.getDataType(), threshold.getThresholdId())) {
            throw new RuntimeException("数据类型已存在");
        }
        if (threshold.getMaxValue().compareTo(threshold.getMinValue()) <= 0) {
            throw new RuntimeException("最大值必须大于最小值");
        }
        return thresholdMapper.updateThreshold(threshold);
    }

    @Override
    @Transactional
    public int deleteThresholdById(Long thresholdId) {
        return thresholdMapper.deleteThresholdById(thresholdId);
    }

    @Override
    public boolean checkDataTypeUnique(Integer dataType, Long thresholdId) {
        int count = thresholdMapper.checkDataTypeUnique(dataType, thresholdId);
        return count == 0;
    }
} 