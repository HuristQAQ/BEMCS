package com.proj.web.controller.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.proj.system.domain.UserDevice;
import com.proj.system.service.IUserDeviceService;

/**
 * 用户设备关联
 * 
 * @author proj
 */
@RestController
@RequestMapping("/business/user-device")
public class UserDeviceController extends BaseController {
    
    @Autowired
    private IUserDeviceService userDeviceService;

    /**
     * 查询用户设备关联列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserDevice userDevice) {
        startPage();
        List<UserDevice> list = userDeviceService.selectUserDeviceList(userDevice);
        return getDataTable(list);
    }

    /**
     * 获取用户设备关联详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(userDeviceService.selectUserDeviceById(id));
    }

    /**
     * 新增用户设备关联
     */
    @Log(title = "用户设备关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserDevice userDevice) {
        if (!userDeviceService.checkUserDeviceUnique(userDevice)) {
            return error("该用户已分配过此设备");
        }
        return toAjax(userDeviceService.insertUserDevice(userDevice));
    }

    /**
     * 删除用户设备关联
     */
    @Log(title = "用户设备关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(userDeviceService.deleteUserDeviceById(id));
    }
} 