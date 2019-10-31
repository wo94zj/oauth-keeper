package com.oauth.inner.service;

import java.io.Serializable;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oauth.inner.IAccountService;
import com.oauth.resp.BaseDto;
import com.oauth.service.AccountService;

@Service(interfaceClass = IAccountService.class, version = "v1.0")
@Component(value = "dubboAccountService")
public class DubboAccountService implements IAccountService {

	@Autowired
	private AccountService accountService;
	
	@Override
	public BaseDto<Serializable> logout(String account, String clientId, String token) {
		return accountService.logout(account, clientId, token);
	}

	@Override
	public BaseDto<Serializable> checkToken(String account, String clientId, String token) {
		return accountService.checkToken(account, clientId, token);
	}

	@Override
	public BaseDto<Serializable> signLogin(String account, String clientCode, String sign) {
		return accountService.signLogin(account, clientCode, sign);
	}

	@Override
	public BaseDto<Serializable> pwdLogin(String phone, String password, String clientCode) {
		return accountService.pwdLogin(phone, password, clientCode);
	}

}
