package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by simon on 5/24/2019.
 */
@Configuration
@ImportResource({"classpath*:emailConfig.xml"})
public class Mail {
}
