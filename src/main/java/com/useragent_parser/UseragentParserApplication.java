package com.useragent_parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UseragentParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UseragentParserApplication.class, args);
	}

}
