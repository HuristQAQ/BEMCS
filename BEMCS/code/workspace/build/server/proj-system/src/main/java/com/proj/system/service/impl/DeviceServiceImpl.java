package com.proj.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proj.system.domain.Device;
import com.proj.system.mapper.DeviceMapper;
import com.proj.system.service.IDeviceService;

/**
 * 设备管理 服务层实现
 * 
 * @author proj
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 查询设备信息
     * 
     * @param deviceId 设备ID
     * @return 设备信息
     */
    @Override
    public Device selectDeviceById(Long deviceId) {
        return deviceMapper.selectDeviceById(deviceId);
    }

    /**
     * 查询设备列表
     * 
     * @param device 设备信息
     * @return 设备集合
     */
    @Override
    public List<Device> selectDeviceList(Device device) {
        return deviceMapper.selectDeviceList(device);
    }

    /**
     * 新增设备
     * 
     * @param device 设备信息
     * @return 结果
     */
    @Override
    public int insertDevice(Device device) {
        return deviceMapper.insertDevice(device);
    }

    /**
     * 修改设备
     * 
     * @param device 设备信息
     * @return 结果
     */
    @Override
    public int updateDevice(Device device) {
        return deviceMapper.updateDevice(device);
    }

    /**
     * 批量删除设备
     * 
     * @param deviceIds 需要删除的设备ID
     * @return 结果
     */
    @Override
    public int deleteDeviceByIds(Long[] deviceIds) {
        return deviceMapper.deleteDeviceByIds(deviceIds);
    }

    /**
     * 校验设备名称是否唯一
     * 
     * @param device 设备信息
     * @return 结果
     */
    @Override
    public boolean checkDeviceNameUnique(Device device) {
        Long deviceId = device.getDeviceId() == null ? -1L : device.getDeviceId();
        Device info = deviceMapper.checkDeviceNameUnique(device.getDeviceName());
        if (info != null && info.getDeviceId().longValue() != deviceId.longValue()) {
            return false;
        }
        return true;
    }
} 