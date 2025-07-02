package com.proj.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.proj.common.core.domain.AjaxResult;
import com.proj.common.core.page.TableDataInfo;
import com.proj.common.utils.poi.ExcelUtil;
import com.proj.system.domain.Environment;
import com.proj.system.service.IEnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proj.common.core.controller.BaseController;

/**
 * 环境数据Controller
 */
@RestController
@RequestMapping("/business/environment")
public class EnvironmentController extends BaseController {
    @Autowired
    private IEnvironmentService environmentService;

    /**
     * 查询实时环境数据
     */
    @GetMapping("/current")
    public AjaxResult getCurrent() {
        List<Environment> list = environmentService.selectCurrentEnvironment();
        return success(list);
    }

    /**
     * 查询历史环境数据列表
     */
    @GetMapping("/history")
    public TableDataInfo history(Environment environment) {
        startPage();
        List<Environment> list = environmentService.selectHistoryEnvironment(environment);
        return getDataTable(list);
    }

    /**
     * 获取图表数据
     */
    @GetMapping("/chart")
    public AjaxResult getChartData(Environment environment) {
        List<Environment> list = environmentService.selectChartData(environment);
        return success(list);
    }

    /**
     * 查询环境数据列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Environment environment) {
        startPage();
        List<Environment> list = environmentService.selectEnvironmentList(environment);
        return getDataTable(list);
    }

    /**
     * 导出环境数据列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Environment environment) {
        List<Environment> list = environmentService.selectEnvironmentList(environment);
        ExcelUtil<Environment> util = new ExcelUtil<Environment>(Environment.class);
        util.exportExcel(response, list, "环境数据数据");
    }

    /**
     * 获取环境数据详细信息
     */
    @GetMapping(value = "/{dataId}")
    public AjaxResult getInfo(@PathVariable("dataId") Long dataId) {
        return success(environmentService.selectEnvironmentByDataId(dataId));
    }

    /**
     * 新增环境数据
     */
    @PostMapping
    public AjaxResult add(@RequestBody Environment environment) {
        return toAjax(environmentService.insertEnvironment(environment));
    }

    /**
     * 修改环境数据
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Environment environment) {
        return toAjax(environmentService.updateEnvironment(environment));
    }

    /**
     * 删除环境数据
     */
    @DeleteMapping("/{dataIds}")
    public AjaxResult remove(@PathVariable Long[] dataIds) {
        return toAjax(environmentService.deleteEnvironmentByDataIds(dataIds));
    }
} 