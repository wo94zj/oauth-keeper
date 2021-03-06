package com.oauth.inner.service;

import java.io.Serializable;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oauth.inner.IAccountService;
import com.oauth.resp.BaseDto;

@Service(interfaceClass = IAccountService.class, version = "v1.0")
@Component(value = "dubboAccountService")
public class DubboAccountService implements IAccountService {

	@Autowired
	private IAccountService accountService;
	
	@Override
	public BaseDto<Serializable> login(String account, String clientId, String sign) {
		return accountService.login(account, clientId, sign);
	}

	@Override
	public BaseDto<Serializable> logout(String account, String clientId, String token, String sign) {
		return accountService.logout(account, clientId, token, sign);
	}

	@Override
	public BaseDto<Serializable> checkToken(String account, String clientId, String token, String sign) {
		return accountService.checkToken(account, clientId, token, sign);
	}

}
