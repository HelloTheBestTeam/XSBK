package com.xsbk.balancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BalancerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BalancerApplication.class, args);
	}
}
