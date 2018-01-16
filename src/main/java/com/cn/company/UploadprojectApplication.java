package com.cn.company;

import com.cn.company.util.upload.CustomMultipartResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;


@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@Configuration
@ComponentScan(basePackages = {"com.cn.company"})
@ServletComponentScan(basePackages = {"com.cn.company"})
/**
 *	@author wubingyu
 */
public class UploadprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadprojectApplication.class, args);
	}

	@Bean(name = "multipartResolver")
	public MultipartResolver multipartResolver(){
		CustomMultipartResolver customMultipartResolver=new CustomMultipartResolver();
		customMultipartResolver.setMaxInMemorySize(20*1024*1024);
		return customMultipartResolver;
	}
}
