package com.proj.system.service.impl;

import com.proj.system.domain.Alarm;
import com.proj.system.mapper.AlarmMapper;
import com.proj.system.service.IAlarmService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlarmServiceImpl implements IAlarmService {
    @Autowired
    private AlarmMapper alarmMapper;

    @Override
    public List<Alarm> selectAlarmList( Alarm alarm) {
        return alarmMapper.selectAlarmList(alarm);
    }
} 