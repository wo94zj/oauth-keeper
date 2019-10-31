package com.oauth.redis;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.oauth.cache.IAccountCacheService;
import com.oauth.config.CommonConfig;

@Service
public class AccountRedisService implements IAccountCacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String key(String clientCode, String account) {
        return "oauth-" + ":" + account;
    }
    
	@Override
	public String cacheToken(String clientCode, String account, String token) {
		String key = key(clientCode, account);
		
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		if(ops.setIfAbsent(key, token, Duration.ofSeconds(CommonConfig.TOKEN_TIMEOUT))) {
			return token;
		}
		
		//如果已经存在token，刷新过期时间
		if(!stringRedisTemplate.expire(key, CommonConfig.TOKEN_TIMEOUT, TimeUnit.SECONDS)) {
			return cacheToken(clientCode, account, token);
		}
		return ops.get(key);
	}

	@Override
	public boolean delToken(String clientId, String account, String token) {
		String key = key(clientId, account);
		
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String presentToken = ops.get(key);
        if(token.equals(presentToken)) {
        	return stringRedisTemplate.delete(key);
        }
        
		return false;
	}

	@Override
	public boolean resetToken(String clientId, String account, String token) {
		String key = key(clientId, account);
		
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		ops.set(key, token, Duration.ofSeconds(CommonConfig.TOKEN_TIMEOUT));
        
		return true;
	}

	@Override
	public boolean checkToken(String clientId, String account, String token) {
		String key = key(clientId, account);
		
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String presentToken = ops.get(key);
        if(token.equals(presentToken)) {
        	stringRedisTemplate.expire(key, CommonConfig.TOKEN_TIMEOUT, TimeUnit.SECONDS);
        	return true;
        }
        
		return false;
	}
    
}
