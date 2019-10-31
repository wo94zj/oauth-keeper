package com.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:common.properties")
@ConfigurationProperties(prefix = "common")
public class CommonConfig {

	public static long TOKEN_TIMEOUT;	//登陆token过期时间，秒
	public static long VCODE_TIMEOUT;	//验证码过期时间，秒
	
	public static String img;
	
	public void setTokenTimeout(long tokenTimeout) {
		TOKEN_TIMEOUT = tokenTimeout;
	}
	
	public void setVcodeTimeout(long vcodeTimeout) {
		VCODE_TIMEOUT = vcodeTimeout;
	}
}
