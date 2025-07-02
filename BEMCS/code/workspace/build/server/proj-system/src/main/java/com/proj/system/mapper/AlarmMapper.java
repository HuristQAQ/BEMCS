package com.proj.system.mapper;

import com.proj.system.domain.Alarm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 告警记录Mapper接口
 */
public interface AlarmMapper {
    /**
     * 新增告警记录
     */
    public int insertAlarm(Alarm alarm);
    
    /**
     * 批量新增告警记录
     */
    public int batchInsertAlarm(List<Alarm> alarmList);

    /**
     * 分页条件查询报警记录
     */
    List<Alarm> selectAlarmList(Alarm alarm);
} 