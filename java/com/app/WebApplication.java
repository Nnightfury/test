package com.app;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@ServletComponentScan("com.app.servlet")
@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer  {	
	
	public static void main(String[] args) {
		 // 整个程序入口，启动springboot项目，创建内置tomcat服务器，使用tomct加载springmvc注解启动类
       SpringApplication.run(WebApplication.class, args);
	}
	
	
	
	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(WebApplication.class);
	}
	
	
}
