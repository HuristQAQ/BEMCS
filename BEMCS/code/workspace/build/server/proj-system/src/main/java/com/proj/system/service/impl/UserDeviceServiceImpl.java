package com.proj.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proj.system.mapper.UserDeviceMapper;
import com.proj.system.domain.UserDevice;
import com.proj.system.service.IUserDeviceService;

/**
 * 用户设备关联 服务层实现
 * 
 * @author proj
 */
@Service
public class UserDeviceServiceImpl implements IUserDeviceService {
    @Autowired
    private UserDeviceMapper userDeviceMapper;

    /**
     * 查询用户设备关联信息
     * 
     * @param id 用户设备关联ID
     * @return 用户设备关联信息
     */
    @Override
    public UserDevice selectUserDeviceById(Long id) {
        return userDeviceMapper.selectUserDeviceById(id);
    }

    /**
     * 查询用户设备关联列表
     * 
     * @param userDevice 用户设备关联信息
     * @return 用户设备关联集合
     */
    @Override
    public List<UserDevice> selectUserDeviceList(UserDevice userDevice) {
        return userDeviceMapper.selectUserDeviceList(userDevice);
    }

    /**
     * 新增用户设备关联
     * 
     * @param userDevice 用户设备关联信息
     * @return 结果
     */
    @Override
    public int insertUserDevice(UserDevice userDevice) {
        return userDeviceMapper.insertUserDevice(userDevice);
    }

    /**
     * 删除用户设备关联信息
     * 
     * @param id 用户设备关联ID
     * @return 结果
     */
    @Override
    public int deleteUserDeviceById(Long id) {
        return userDeviceMapper.deleteUserDeviceById(id);
    }

    /**
     * 校验用户设备关联是否唯一
     * 
     * @param userDevice 用户设备关联信息
     * @return 结果
     */
    @Override
    public boolean checkUserDeviceUnique(UserDevice userDevice) {
        Long id = userDevice.getId() == null ? -1L : userDevice.getId();
        UserDevice info = userDeviceMapper.checkUserDeviceUnique(userDevice);
        if (info != null && info.getId().longValue() != id.longValue()) {
            return false;
        }
        return true;
    }
} 