package com.school.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableJpaRepositories("com.school.core.repository")
public class SchoolWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolWebAppApplication.class, args);
	}
}
