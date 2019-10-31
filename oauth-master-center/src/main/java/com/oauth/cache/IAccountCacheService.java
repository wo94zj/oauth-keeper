package com.oauth.cache;

public interface IAccountCacheService {

	/********************************************token cache********************************************/
	
	/**
	 * 缓存token，如果已经存在则返回已存在token，并刷新过期时间
	 */
	String cacheToken(String clientCode, String phone, String token);
	
	/**
	 * 清理token，会先检查token是否合法
	 */
	boolean delToken(String clientCode, String phone, String token);
	
	/**
	 * 重置token
	 */
	boolean resetToken(String clientCode, String phone, String token);
	
	/**
	 * 检查token，并刷新过期时间
	 */
	boolean checkToken(String clientCode, String phone, String token);
	
	
	/********************************************vcode cache********************************************/
	
	/**
	 * 缓存验证码，会覆盖旧验证码
	 */
	String cacheVcode(String clientCode, String phone, String vcode);
	
	/**
	 * 验证验证码
	 */
	boolean checkVcode(String clientCode, String phone, String vcode);
}
