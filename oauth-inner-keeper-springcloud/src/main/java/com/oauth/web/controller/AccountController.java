package com.oauth.web.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.inner.IAccountService;
import com.oauth.resp.BaseDto;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

	@Autowired
	private IAccountService accountService;
	
	@RequestMapping(value = "/login/sign", method = RequestMethod.POST)
	public BaseDto<Serializable> signLogin(String phone, String clientCode, String sign) {
		return accountService.signLogin(phone, clientCode, sign);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public BaseDto<Serializable> pwdLogin(String phone, String password, String clientCode) {
		return accountService.pwdLogin(phone, password, clientCode);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.DELETE)
	public BaseDto<Serializable> logout(String phone, String clientCode, String token) {
		return accountService.logout(phone, clientCode, token);
	}
	
	@RequestMapping(value = "/token", method = RequestMethod.PUT)
	public BaseDto<Serializable> checkToken(String phone, String clientCode, String token) {
		return accountService.checkToken(phone, clientCode, token);
	}
	
}
