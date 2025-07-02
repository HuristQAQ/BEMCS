package com.proj.system.task;

import com.proj.system.service.IEnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 环境数据定时任务
 */
@Component
public class EnvironmentTask {

    //间隔时间
    static final long time = 30000;

    private static final Logger log = LoggerFactory.getLogger(EnvironmentTask.class);

    @Autowired
    private IEnvironmentService environmentService;

    /**
     * 每5秒执行一次
     */
    @Scheduled(fixedRate = time)
    public void generateEnvironmentData() {
        log.info("[定时任务] 环境数据生成任务开始");
        try {
            environmentService.generateNewEnvironmentData();
            log.info("[定时任务] 环境数据生成任务完成");
        } catch (Exception e) {
            log.error("[定时任务] 环境数据生成任务异常：", e);
        }
    }
} 