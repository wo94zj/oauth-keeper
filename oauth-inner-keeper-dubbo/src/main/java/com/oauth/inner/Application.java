package com.oauth.inner;

import java.util.concurrent.CountDownLatch;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.oauth"})
@EnableDubbo //dubbo配置
@MapperScan("com.oauth.vip.mapper") //不加时无法注入mapper
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		CountDownLatch cdl = new CountDownLatch(1);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				cdl.countDown();
			}
		});
		
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
