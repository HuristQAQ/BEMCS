package com.proj.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.proj.common.annotation.Excel;
import com.proj.common.core.domain.BaseEntity;
import java.util.Set;

/**
 * 设备对象 biz_device
 * 
 * @author proj
 */
public class Device extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long deviceId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备类型 (1办公区 2生活区 3特殊区域) */
    @Excel(name = "设备类型")
    private String deviceType;

    /** 所属项目ID */
    @Excel(name = "所属项目ID")
    private Long projectId;

    /** 所属项目名称 */
    @Excel(name = "所属项目名称")
    private String projectName;

    /** 设备状态（0正常 1异常） */
    @Excel(name = "设备状态")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 查询条件-设备ID集合 */
    private Set<Long> deviceIds;

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public Set<Long> getDeviceIds() { return deviceIds; }
    public void setDeviceIds(Set<Long> deviceIds) { this.deviceIds = deviceIds; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("deviceId", getDeviceId())
            .append("deviceName", getDeviceName())
            .append("deviceType", getDeviceType())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
} 