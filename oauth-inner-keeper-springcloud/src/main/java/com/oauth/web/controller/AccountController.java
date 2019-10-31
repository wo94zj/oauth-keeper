package com.oauth.web.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.resp.BaseDto;
import com.oauth.service.AccountService;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/login/sign", method = RequestMethod.POST)
	public BaseDto<Serializable> signLogin(@RequestParam String phone, @RequestParam String clientCode, @RequestParam String sign) {
		return accountService.signLogin(phone, clientCode, sign);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public BaseDto<Serializable> pwdLogin(@RequestParam String phone, @RequestParam String password, @RequestParam String clientCode) {
		return accountService.pwdLogin(phone, password, clientCode);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.DELETE)
	public BaseDto<Serializable> logout(@RequestParam String phone, @RequestParam String clientCode, @RequestParam String token) {
		return accountService.logout(phone, clientCode, token);
	}
	
	@RequestMapping(value = "/token", method = RequestMethod.PUT)
	public BaseDto<Serializable> checkToken(@RequestParam String phone, @RequestParam String clientCode, @RequestParam String token) {
		return accountService.checkToken(phone, clientCode, token);
	}
	
	@RequestMapping(value = "/login/vcode", method = RequestMethod.POST)
	public BaseDto<Serializable> vcodeLogin(String phone, String vcode, String clientCode){
		return accountService.vcodeLogin(phone, vcode, clientCode);
	}
	
}
