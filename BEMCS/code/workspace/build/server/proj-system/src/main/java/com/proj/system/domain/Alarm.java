package com.proj.system.domain;

import com.proj.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * 告警记录对象 biz_alarm
 */
@Data
public class Alarm extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 报警ID */
    private Long alarmId;
    /** 数据类型 */
    private Integer dataType;

    private BigDecimal dataValue;
    /** 报警时间 */
    private Date alarmTime;
    /** 所属项目ID */
    private Long projectId;
    private String projectName;
    /** 设备ID */
    private Long deviceId;
    private String deviceName;
    /** 备注 */
    private String remark;
    /** 查询条件-设备ID集合 */
    private Set<Long> deviceIds;

    public Long getAlarmId() { return alarmId; }
    public void setAlarmId(Long alarmId) { this.alarmId = alarmId; }

    public Integer getDataType() { return dataType; }
    public void setDataType(Integer dataType) { this.dataType = dataType; }

    public Date getAlarmTime() { return alarmTime; }
    public void setAlarmTime(Date alarmTime) { this.alarmTime = alarmTime; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public Set<Long> getDeviceIds() { return deviceIds; }
    public void setDeviceIds(Set<Long> deviceIds) { this.deviceIds = deviceIds; }
} 