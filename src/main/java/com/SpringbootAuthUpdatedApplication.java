package com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.service.MyService;
@SpringBootApplication
public class SpringbootAuthUpdatedApplication {



	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootAuthUpdatedApplication.class, args);


	}
	
	
	@Bean
	public CommandLineRunner init(final MyService myService) {

	    return new CommandLineRunner() {
	    	public void run(String... strings) throws Exception {
	        	myService.createDemoUsers();
	        }
	    };
	}
	 
}
