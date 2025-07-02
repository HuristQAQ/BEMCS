package com.proj.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.proj.common.annotation.Excel;
import com.proj.common.core.domain.BaseEntity;

/**
 * 用户设备关联对象 biz_user_device
 * 
 * @author proj
 */
public class UserDevice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 关联ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long deviceId;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userName;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("projectId", getProjectId())
            .append("deviceId", getDeviceId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("userName", getUserName())
            .append("projectName", getProjectName())
            .append("deviceName", getDeviceName())
            .toString();
    }
} 