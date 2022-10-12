package com.eudagama12.example.k8monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class K8MonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(K8MonitorApplication.class, args);
	}

}
