package com.proj.system.service;

import com.proj.system.domain.Alarm;
import java.util.List;

/**
 * 报警管理Service接口
 */
public interface IAlarmService {
    /**
     * 分页条件查询报警记录
     */
    List<Alarm> selectAlarmList(Alarm alarm);
} 