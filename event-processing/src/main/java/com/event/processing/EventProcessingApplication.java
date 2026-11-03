package com.event.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EventProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventProcessingApplication.class, args);

	}

}
