package com.proj.web.controller.business;

import com.proj.common.core.controller.BaseController;
import com.proj.common.core.domain.AjaxResult;
import com.proj.system.domain.Threshold;
import com.proj.system.service.IThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/threshold")
public class ThresholdController extends BaseController {
    @Autowired
    private IThresholdService thresholdService;

    /** 查询阈值列表 */
    @GetMapping("/list")
    public AjaxResult list(Threshold threshold) {
        List<Threshold> list = thresholdService.selectThresholdList(threshold);
        return AjaxResult.success(list);
    }

    /** 查询单个阈值 */
    @GetMapping("/{thresholdId}")
    public AjaxResult getInfo(@PathVariable Long thresholdId) {
        Threshold threshold = thresholdService.selectThresholdById(thresholdId);
        return AjaxResult.success(threshold);
    }

    /** 新增阈值 */
    @PostMapping
    public AjaxResult add(@RequestBody Threshold threshold) {
        int rows = thresholdService.insertThreshold(threshold);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /** 修改阈值 */
    @PutMapping
    public AjaxResult edit(@RequestBody Threshold threshold) {
        int rows = thresholdService.updateThreshold(threshold);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /** 删除阈值 */
    @DeleteMapping("/{thresholdId}")
    public AjaxResult remove(@PathVariable Long thresholdId) {
        int rows = thresholdService.deleteThresholdById(thresholdId);
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }
} 