package com;

import com.properties.FileStorageProperties;
import com.security.TokenProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class SpringbootAuthUpdatedApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringbootAuthUpdatedApplication.class, args);


	}
	
	

	 
}
