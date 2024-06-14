package com.huzaifa.cafe.sollydz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SollydzApplication {

	public static void main(String[] args) {
		SpringApplication.run(SollydzApplication.class, args);
		log.info("Application is running successfully..........!");
	}

}
