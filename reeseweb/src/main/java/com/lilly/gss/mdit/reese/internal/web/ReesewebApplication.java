package com.lilly.gss.mdit.reese.internal.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages={"com.lilly"})
@EntityScan(basePackages={"com.lilly"})
@ComponentScan(basePackages = {"com.lilly"})
public class ReesewebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReesewebApplication.class, args);
	}
}
