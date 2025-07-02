package com.proj.web.controller.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proj.common.annotation.Log;
import com.proj.common.core.controller.BaseController;
import com.proj.common.core.domain.AjaxResult;
import com.proj.common.core.page.TableDataInfo;
import com.proj.common.enums.BusinessType;
import com.proj.system.domain.Device;
import com.proj.system.service.IDeviceService;
import com.proj.system.service.IUserDeviceService;
import com.proj.system.domain.UserDevice;
import com.proj.common.utils.SecurityUtils;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 设备管理
 * 
 * @author proj
 */
@RestController
@RequestMapping("/business/device")
public class DeviceController extends BaseController {
    
    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IUserDeviceService userDeviceService;

    /**
     * 查询设备列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Device device) {
        // 权限控制：管理员查全部，普通用户查自己设备
        boolean isAdmin = SecurityUtils.isAdmin(SecurityUtils.getUserId());
        if (!isAdmin) {
            UserDevice userDevice = new UserDevice();
            userDevice.setUserId(SecurityUtils.getUserId());
            java.util.List<UserDevice> userDeviceList = userDeviceService.selectUserDeviceList(userDevice);
            Set<Long> deviceIds = userDeviceList.stream().map(UserDevice::getDeviceId).collect(Collectors.toSet());
            if (deviceIds.isEmpty()) {
                return getDataTable(java.util.Collections.emptyList());
            }
            device.setDeviceIds(deviceIds);
        }
        startPage();
        java.util.List<Device> list = deviceService.selectDeviceList(device);
        return getDataTable(list);
    }

    /**
     * 获取设备详细信息
     */
    @GetMapping(value = "/{deviceId}")
    public AjaxResult getInfo(@PathVariable("deviceId") Long deviceId) {
        return success(deviceService.selectDeviceById(deviceId));
    }

    /**
     * 新增设备
     */
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Device device) {
        return toAjax(deviceService.insertDevice(device));
    }

    /**
     * 修改设备
     */
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Device device) {
        return toAjax(deviceService.updateDevice(device));
    }

    /**
     * 删除设备
     */
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deviceIds}")
    public AjaxResult remove(@PathVariable Long[] deviceIds) {
        return toAjax(deviceService.deleteDeviceByIds(deviceIds));
    }
} 