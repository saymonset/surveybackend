package com.config;

import com.security_delete.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by simon on 5/16/2019.
 */
@Configuration
public class SecurityConfiguration {

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
}
