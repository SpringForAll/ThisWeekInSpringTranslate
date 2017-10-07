package com.spring4all.job;


import com.spring4all.controller.TranslateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WeeklyPaperJobs {
    private Logger logger = LoggerFactory.getLogger(WeeklyPaperJobs.class);

    @Autowired
    private TranslateController translateController;

    @Scheduled(cron = "${springio.time}")
    public void getSpringIoWeeklyPaper() {
        System.err.println("this is test");
        logger.info("开始获取周报:");
        try {
            translateController.translateAndSendMail();
        } catch (Exception e) {
            logger.error("出现异常:{}", e);
            return;
        }

        logger.info("已发送至邮箱");
    }
}
