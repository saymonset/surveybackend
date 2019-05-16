package com;

import com.security.TokenProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringbootAuthUpdatedApplication {
	private static final Integer DEFAULT_VALIDITY_SECONDS = 3600;
	private static final Integer DEFAULT_NOTIFY_SECONDS = 120;


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public TokenProvider tokenProvider() {
		String secret = "mySecretXAuthSecret";
		int validityInSeconds =   DEFAULT_VALIDITY_SECONDS;
		int notifyGraceSeconds = DEFAULT_NOTIFY_SECONDS;
		return new TokenProvider(secret, validityInSeconds, notifyGraceSeconds);
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringbootAuthUpdatedApplication.class, args);


	}
	
	

	 
}
