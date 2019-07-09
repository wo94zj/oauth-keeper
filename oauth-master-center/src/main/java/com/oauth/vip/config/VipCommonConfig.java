package com.oauth.vip.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:vip.properties")
@ConfigurationProperties(prefix = "vip")
public class VipCommonConfig {

	public static long TOKEN_TIMEOUT;
	
	public void setTokenTimeout(long tokenTimeout) {
		TOKEN_TIMEOUT = tokenTimeout;
	}
}
