package com.spring4all.controller;

import com.spring4all.service.MailService;
import com.spring4all.service.WeeklyPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by maskwang on 2017/9/14 0014.
 */
@RestController
public class TranslateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslateController.class);

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Autowired
    private WeeklyPostService weeklyPostService;

    @Autowired
    private MailService mailService;


    @GetMapping(value = "/translate")
    public String translate(){
        long time = System.currentTimeMillis();
        LOGGER.info("开始获取周报内容,开始时间{}", time);
        String weeklyPost = weeklyPostService.crawlWeeklyPost();
        LOGGER.info("结束,花费时间：{}", System.currentTimeMillis() - time);
        return weeklyPost;
    }
    @GetMapping(value = "/mail")
    public String translateAndSendMail() {

        long time = System.currentTimeMillis();
        LOGGER.info("开始获取周报内容,开始时间{}", time);
        String weeklyPost = weeklyPostService.crawlWeeklyPost();
        LOGGER.info("结束,花费时间：{}", System.currentTimeMillis() - time);

        LocalDate localDate = LocalDate.now();
        LOGGER.info("开始发送邮件:{}", System.currentTimeMillis());
        executorService.submit(
                () -> mailService.sendTextAndFile(localDate.toString(), weeklyPost, localDate + ".md", weeklyPost)
        );
        LOGGER.info("结束,花费时间：{}", System.currentTimeMillis() - time);
        return weeklyPost;
    }

}

