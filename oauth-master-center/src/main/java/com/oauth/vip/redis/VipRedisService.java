package com.oauth.vip.redis;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.oauth.vip.cache.IVipCacheService;
import com.oauth.vip.config.VipCommonConfig;

@Service
public class VipRedisService implements IVipCacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String key(String clientId, String account) {
        return "oauth-" + clientId + ":" + account;
    }
    
	@Override
	public String cacheToken(String clientId, String account, String token) {
		String key = key(clientId, account);
		
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		if(ops.setIfAbsent(key, token, Duration.ofSeconds(VipCommonConfig.TOKEN_TIMEOUT))) {
			return token;
		}
		
		if(!stringRedisTemplate.expire(key, VipCommonConfig.TOKEN_TIMEOUT, TimeUnit.SECONDS)) {
			return cacheToken(clientId, account, token);
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
		ops.set(key, token, Duration.ofSeconds(VipCommonConfig.TOKEN_TIMEOUT));
        
		return true;
	}

	@Override
	public boolean checkToken(String clientId, String account, String token) {
		String key = key(clientId, account);
		
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String presentToken = ops.get(key);
        if(token.equals(presentToken)) {
        	stringRedisTemplate.expire(key, VipCommonConfig.TOKEN_TIMEOUT, TimeUnit.SECONDS);
        	return true;
        }
        
		return false;
	}
    
}
