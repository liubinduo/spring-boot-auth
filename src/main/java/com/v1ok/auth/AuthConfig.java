package com.v1ok.auth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.v1ok.auth", "com.v1ok.commons"})
@EnableConfigurationProperties({AuthProperties.class})
public class AuthConfig {

}
