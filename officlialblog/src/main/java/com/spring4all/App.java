package com.spring4all;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;

/**
 * 启动类
 *
 * @author maskwang
 *         2017年6月23日
 */
@SpringBootApplication
public class App {


    public static void main(String[] args) {

        HashMap props = new HashMap();
        props.put("server.port",9999);
        new SpringApplicationBuilder()
                .sources(App.class)
                .properties(props)
                .run(args);
    }
}
