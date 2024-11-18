package com.quangduy.track_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TrackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackServiceApplication.class, args);
	}

}
