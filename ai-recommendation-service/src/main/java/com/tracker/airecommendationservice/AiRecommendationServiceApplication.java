package com.tracker.airecommendationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AiRecommendationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AiRecommendationServiceApplication.class, args);

		System.out.println();
	}

}
