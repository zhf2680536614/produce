package com.atey;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
//@EnableCaching//开发缓存注解功能
//@EnableScheduling //开启任务调度
public class ProduceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProduceApplication.class, args);
        log.info("服务启动");
    }
}
