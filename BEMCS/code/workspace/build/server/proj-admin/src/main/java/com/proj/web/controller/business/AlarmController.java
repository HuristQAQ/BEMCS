package com.proj.web.controller.business;

import com.proj.common.core.controller.BaseController;
import com.proj.common.core.domain.AjaxResult;
import com.proj.common.core.page.TableDataInfo;
import com.proj.common.utils.SecurityUtils;
import com.proj.system.domain.Alarm;
import com.proj.system.domain.UserDevice;
import com.proj.system.service.IAlarmService;
import com.proj.system.service.IUserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 报警管理
 */
@RestController
@RequestMapping("/business/alarm")
public class AlarmController extends BaseController {
    @Autowired
    private IAlarmService alarmService;
    @Autowired
    private IUserDeviceService userDeviceService;

    /**
     * 报警记录分页查询
     */
    @GetMapping("/list")
    public TableDataInfo list(Alarm alarm) {
        // 判断是否管理员
        boolean isAdmin = SecurityUtils.isAdmin(SecurityUtils.getUserId());
        if (!isAdmin) {
            // 普通用户只能查自己有权限的设备
            UserDevice userDevice = new UserDevice();
            userDevice.setUserId(SecurityUtils.getUserId());
            List<UserDevice> userDeviceList = userDeviceService.selectUserDeviceList(userDevice);
            Set<Long> deviceIds = userDeviceList.stream().map(UserDevice::getDeviceId).collect(Collectors.toSet());
            if (deviceIds.isEmpty()) {
                // 没有设备权限，直接返回空
                return getDataTable(Collections.emptyList());
            }
            alarm.setDeviceIds(deviceIds);
        }
        startPage();
        List<Alarm> list = alarmService.selectAlarmList(alarm);
        return getDataTable(list);
    }
} 