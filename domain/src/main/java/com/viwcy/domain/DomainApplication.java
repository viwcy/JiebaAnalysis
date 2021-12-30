package com.viwcy.domain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.viwcy")
@MapperScan("com.viwcy.*.mapper")
public class DomainApplication {

	public static void main(String[] args) {
		SpringApplication.run(DomainApplication.class, args);
	}

}
