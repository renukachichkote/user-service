package com.example.userservice;

import com.example.userservice.util.DefaultDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.event.EventListener;

import java.io.IOException;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

	@Autowired
	DefaultDataLoader defaultDataLoader;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		log.info("Verifying and inserting default user");
		defaultDataLoader.loadDefaultAdminUser();
	}
}
