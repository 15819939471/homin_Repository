package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author homin
 * 日期2021-01-25 17:01
 */
public class MyListener implements SpringApplicationRunListener {
    @Override
    public void starting() {
        System.out.println("项目启动时执行该方法...starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("项目需要的准备环境执行该方法before...environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("项目环境可以加载了执行该方法ing...contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("项目环境加载已完成执行该方法after...contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("项目已启动完成执行该方法...started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("项目正在运行执行该方法...running");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("项目启动失败执行该方法...failed");
    }

    public MyListener(SpringApplication springApplication, String[] args) {
    }
}
