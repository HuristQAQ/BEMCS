package com.proj.system.mapper;

import java.util.List;
import com.proj.system.domain.Device;

/**
 * 设备管理 数据层
 * 
 * @author proj
 */
public interface DeviceMapper {
    /**
     * 查询设备信息
     * 
     * @param deviceId 设备ID
     * @return 设备信息
     */
    public Device selectDeviceById(Long deviceId);

    /**
     * 查询设备列表
     * 
     * @param device 设备信息
     * @return 设备集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 新增设备
     * 
     * @param device 设备信息
     * @return 结果
     */
    public int insertDevice(Device device);

    /**
     * 修改设备
     * 
     * @param device 设备信息
     * @return 结果
     */
    public int updateDevice(Device device);

    /**
     * 批量删除设备
     * 
     * @param deviceIds 需要删除的设备ID
     * @return 结果
     */
    public int deleteDeviceByIds(Long[] deviceIds);

    /**
     * 校验设备名称是否唯一
     * 
     * @param deviceName 设备名称
     * @return 结果
     */
    public Device checkDeviceNameUnique(String deviceName);

    /**
     * 查询所有设备
     */
    public List<Device> selectAllDevice();
} 