package com.oauth.redis;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.oauth.cache.IAccountCacheService;
import com.oauth.config.AccountConfig;
import com.oauth.util.StringUtil;

@Service
public class AccountRedisService implements IAccountCacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String tokenKey(String clientCode, String account) {
        return "oauth-token" + ":" + account;
    }
    private String vcodeKey(String clientCode, String account) {
        return "oauth-vcode" + ":" + account;
    }
    
	@Override
	public String cacheToken(String clientCode, String phone, String token) {
		String key = tokenKey(clientCode, phone);
		
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		if(ops.setIfAbsent(key, token, Duration.ofSeconds(AccountConfig.TOKEN_TIMEOUT))) {
			return token;
		}
		
		//如果已经存在token，刷新过期时间
		if(!stringRedisTemplate.expire(key, AccountConfig.TOKEN_TIMEOUT, TimeUnit.SECONDS)) {
			return cacheToken(clientCode, phone, token);
		}
		return ops.get(key);
	}

	@Override
	public boolean delToken(String clientCode, String phone, String token) {
		String key = tokenKey(clientCode, phone);
		
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String presentToken = ops.get(key);
        if(token.equals(presentToken)) {
        	return stringRedisTemplate.delete(key);
        }
        
		return false;
	}

	@Override
	public boolean resetToken(String clientCode, String phone, String token) {
		String key = tokenKey(clientCode, phone);
		
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		ops.set(key, token, Duration.ofSeconds(AccountConfig.TOKEN_TIMEOUT));
        
		return true;
	}

	@Override
	public boolean checkToken(String clientCode, String phone, String token) {
		String key = tokenKey(clientCode, phone);
		
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String presentToken = ops.get(key);
        if(token.equals(presentToken)) {
        	stringRedisTemplate.expire(key, AccountConfig.TOKEN_TIMEOUT, TimeUnit.SECONDS);
        	return true;
        }
        
		return false;
	}

	@Override
	public String cacheVcode(String clientCode, String phone, String vcode) {
		String key = vcodeKey(clientCode, phone);
		
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		ops.set(key, vcode, AccountConfig.VCODE_TIMEOUT, TimeUnit.SECONDS);
		
		return vcode;
	}

	@Override
	public boolean checkVcode(String clientCode, String phone, String vcode) {
		String key = vcodeKey(clientCode, phone);
		
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String presentVcode = ops.get(key);
        if(StringUtil.isBlank(presentVcode) || !presentVcode.equals(vcode)) {
        	return false;
        }
        
		return true;
	}
    
}
