package com.proj.system.service;

import java.util.List;
import com.proj.system.domain.UserDevice;

/**
 * 用户设备关联 服务层
 * 
 * @author proj
 */
public interface IUserDeviceService {
    /**
     * 查询用户设备关联信息
     * 
     * @param id 用户设备关联ID
     * @return 用户设备关联信息
     */
    public UserDevice selectUserDeviceById(Long id);

    /**
     * 查询用户设备关联列表
     * 
     * @param userDevice 用户设备关联信息
     * @return 用户设备关联集合
     */
    public List<UserDevice> selectUserDeviceList(UserDevice userDevice);

    /**
     * 新增用户设备关联
     * 
     * @param userDevice 用户设备关联信息
     * @return 结果
     */
    public int insertUserDevice(UserDevice userDevice);

    /**
     * 删除用户设备关联信息
     * 
     * @param id 用户设备关联ID
     * @return 结果
     */
    public int deleteUserDeviceById(Long id);

    /**
     * 校验用户设备关联是否唯一
     * 
     * @param userDevice 用户设备关联信息
     * @return 结果
     */
    public boolean checkUserDeviceUnique(UserDevice userDevice);
} 