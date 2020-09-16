package com.emilie.ClimbingWebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages="com.emilie")
public class ClimbingWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClimbingWebAppApplication.class, args);
	}

}
