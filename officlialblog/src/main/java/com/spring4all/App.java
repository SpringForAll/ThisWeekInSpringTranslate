package com.spring4all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;

/**
 * 启动类
 *
 * @author maskwang
 * 2017年6月23日
 */
@SpringBootApplication
@EnableScheduling
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
