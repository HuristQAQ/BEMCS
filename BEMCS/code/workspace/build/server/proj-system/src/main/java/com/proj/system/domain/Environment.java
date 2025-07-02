package com.proj.system.domain;

import com.proj.common.annotation.Excel;
import com.proj.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 环境数据对象 biz_environment_data
 */
public class Environment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 数据ID */
    private Long dataId;

    /** 数据类型 */
    @Excel(name = "数据类型")
    private Integer dataType;

    /** 数据值 */
    @Excel(name = "数据值")
    private BigDecimal dataValue;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 采集时间 */
    @Excel(name = "采集时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date collectTime;

    /** 是否当前数据(0否 1是) */
    @Excel(name = "是否当前数据", readConverterExp = "0=否,1=是")
    private String isCurrent;

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }

    public BigDecimal getDataValue() {
        return dataValue;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("dataId", getDataId())
            .append("dataType", getDataType())
            .append("dataValue", getDataValue())
            .append("unit", getUnit())
            .append("collectTime", getCollectTime())
            .append("isCurrent", getIsCurrent())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
} 