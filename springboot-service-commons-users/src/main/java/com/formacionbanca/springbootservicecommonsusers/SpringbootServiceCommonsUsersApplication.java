package com.formacionbanca.springbootservicecommonsusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootServiceCommonsUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServiceCommonsUsersApplication.class, args);
	}

}
