package com.proj.system.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.proj.common.utils.DateUtils;
import com.proj.common.utils.SecurityUtils;
import com.proj.system.domain.Environment;
import com.proj.system.domain.Alarm;
import com.proj.system.domain.Threshold;
import com.proj.system.domain.Device;
import com.proj.system.mapper.EnvironmentMapper;
import com.proj.system.mapper.AlarmMapper;
import com.proj.system.mapper.ThresholdMapper;
import com.proj.system.mapper.DeviceMapper;
import com.proj.system.service.IEnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 环境数据Service业务层处理
 */
@Service
public class EnvironmentServiceImpl implements IEnvironmentService {
    @Autowired
    private EnvironmentMapper environmentMapper;
    @Autowired
    private AlarmMapper alarmMapper;
    @Autowired
    private ThresholdMapper thresholdMapper;
    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 查询环境数据
     */
    @Override
    public Environment selectEnvironmentByDataId(Long dataId) {
        return environmentMapper.selectEnvironmentByDataId(dataId);
    }

    /**
     * 查询环境数据列表
     */
    @Override
    public List<Environment> selectEnvironmentList(Environment environment) {
        return environmentMapper.selectEnvironmentList(environment);
    }

    /**
     * 新增环境数据
     */
    @Override
    public int insertEnvironment(Environment environment) {
        environment.setCreateTime(DateUtils.getNowDate());
        environment.setCreateBy(SecurityUtils.getUsername());
        return environmentMapper.insertEnvironment(environment);
    }

    /**
     * 修改环境数据
     */
    @Override
    public int updateEnvironment(Environment environment) {
        environment.setUpdateTime(DateUtils.getNowDate());
        environment.setUpdateBy(SecurityUtils.getUsername());
        return environmentMapper.updateEnvironment(environment);
    }

    /**
     * 批量删除环境数据
     */
    @Override
    public int deleteEnvironmentByDataIds(Long[] dataIds) {
        return environmentMapper.deleteEnvironmentByDataIds(dataIds);
    }

    /**
     * 删除环境数据信息
     */
    @Override
    public int deleteEnvironmentByDataId(Long dataId) {
        return environmentMapper.deleteEnvironmentByDataId(dataId);
    }

    /**
     * 查询实时环境数据
     */
    @Override
    public List<Environment> selectCurrentEnvironment() {
        return environmentMapper.selectCurrentEnvironment();
    }

    /**
     * 查询历史环境数据
     */
    @Override
    public List<Environment> selectHistoryEnvironment(Environment environment) {
        return environmentMapper.selectHistoryEnvironment(environment);
    }

    /**
     * 查询图表数据
     */
    @Override
    public List<Environment> selectChartData(Environment environment) {
        return environmentMapper.selectChartData(environment);
    }

    /**
     * 生成新的环境数据
     */
    @Override
    public void generateNewEnvironmentData() {
        // 1. 将当前实时数据转为历史数据
        environmentMapper.updateAllCurrentToHistory();

        // 2. 查询所有阈值
        List<Threshold> thresholdList = thresholdMapper.selectThresholdList(null);
        // 3. 查询所有设备
        List<Device> deviceList = deviceMapper.selectAllDevice();

        Random random = new Random();
        Date now = new Date();
       // String username = SecurityUtils.getUsername();

        // 生成7种环境数据
        for (int i = 1; i <= 7; i++) {
            final int dataTypeForAlarm = i;
            Environment environment = new Environment();
            environment.setDataType(i);
            environment.setIsCurrent("1");
            environment.setCollectTime(now);
            environment.setCreateTime(now);
          //  environment.setCreateBy(username);
            environment.setUpdateTime(now);
          //  environment.setUpdateBy(username);

            // 根据数据类型设置不同的值和单位
            switch (i) {
                case 1: // PM2.5
                    environment.setDataValue(new BigDecimal(random.nextInt(100) + 50));
                    environment.setUnit("μg/m³");
                    break;
                case 2: // CO2
                    environment.setDataValue(new BigDecimal(random.nextInt(35) + 5));
                    environment.setUnit("ppm");
                    break;
                case 3: // 温度
                    environment.setDataValue(new BigDecimal(random.nextInt(20) + 15));
                    environment.setUnit("℃");
                    break;
                case 4: // 湿度
                    environment.setDataValue(new BigDecimal(random.nextInt(30) + 40));
                    environment.setUnit("%");
                    break;
                case 5: // 光照
                    environment.setDataValue(new BigDecimal(random.nextInt(400) + 250));
                    environment.setUnit("lux");
                    break;
                case 6: // 烟雾
                    environment.setDataValue(new BigDecimal(random.nextInt(10)));
                    environment.setUnit("mg/m³");
                    break;
                case 7: // 含氧量
                    environment.setDataValue(new BigDecimal(random.nextInt(20) + 60));
                    environment.setUnit("%");
                    break;
            }

            environmentMapper.insertEnvironment(environment);

            // 告警处理逻辑
            Threshold threshold = thresholdList.stream().filter(t -> t.getDataType() != null && t.getDataType().equals(dataTypeForAlarm)).findFirst().orElse(null);
            boolean isAlarm = false;
            if (threshold != null && environment.getDataValue() != null) {
                if ((threshold.getMinValue() != null && environment.getDataValue().compareTo(threshold.getMinValue()) < 0)
                    || (threshold.getMaxValue() != null && environment.getDataValue().compareTo(threshold.getMaxValue()) > 0)) {
                    isAlarm = true;
                }
            }
            if (isAlarm && !deviceList.isEmpty()) {
                List<Alarm> alarmList = new java.util.ArrayList<>();
                for (Device device : deviceList) {
                    Alarm alarm = new Alarm();
                    alarm.setDataType(dataTypeForAlarm);
                    alarm.setDataValue(environment.getDataValue());
                    alarm.setAlarmTime(now);
                    alarm.setProjectId(device.getProjectId());
                    alarm.setDeviceId(device.getDeviceId());
                    alarm.setRemark("环境数据超出阈值");
                   // alarm.setCreateBy(username);
                    alarm.setCreateTime(now);
                    //alarm.setUpdateBy(username);
                    alarm.setUpdateTime(now);
                    alarmList.add(alarm);
                }
                alarmMapper.batchInsertAlarm(alarmList);
            }
        }
    }
} 