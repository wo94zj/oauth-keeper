package com.oauth.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oauth.cache.IAccountCacheService;
import com.oauth.enums.AccountLevelEnum;
import com.oauth.enums.CommonStatusEnum;
import com.oauth.mapper.AccountBindMapper;
import com.oauth.mapper.AccountMapper;
import com.oauth.mapper.ClientMapper;
import com.oauth.pojo.Account;
import com.oauth.pojo.AccountBind;
import com.oauth.pojo.Client;
import com.oauth.resp.BaseDto;
import com.oauth.resp.ResultCode;
import com.oauth.resp.ResultUtil;
import com.oauth.util.AESUtil;
import com.oauth.util.MD5Util;
import com.oauth.util.MediaTypeUtil;
import com.oauth.util.SHAUtil;
import com.oauth.util.SecretUtil;
import com.oauth.util.StringUtil;
import com.oauth.util.TimeUtil;

@Service(value = "accountService")
public class AccountService {

	@Autowired
	private ClientMapper clientService;
	@Autowired
	private AccountMapper accountService;
	@Autowired
	private AccountBindMapper accountBindService;

	@Autowired
	private IAccountCacheService accountCacheService;

	/**
	 * 内部机构接口调用登录
	 */
	public BaseDto<Serializable> signLogin(String phone, String clientCode, String sign) {
		Client client = clientService.selectClientByClientCode(clientCode);

		// 客户端是否可用
		if (Objects.isNull(client) || client.getStatus() == CommonStatusEnum.DISABLED.getStatus()) {
			return ResultUtil.result(ResultCode.UNABLE_CLIENT);
		}

		// 解密
		String decrypt = AESUtil.decrypt(sign, client.getClientSecret());
		if (Objects.isNull(decrypt)) {
			return ResultUtil.result(ResultCode.UNABLE_SECRET);
		}

		Map<String, String> decryptMap = MediaTypeUtil.mediaFormToMap(decrypt);
		String password = decryptMap.get("password");
		String time = decryptMap.get("timestamp");
		if (Objects.isNull(password) || Objects.isNull(time)) {
			return ResultUtil.result(ResultCode.UNABLE_SECRET);
		}

		return loginValid(phone, password, client);
	}
	
	public BaseDto<Serializable> pwdLogin(String phone, String password, String clientCode) {
		Client client = clientService.selectClientByClientCode(clientCode);
		// 客户端是否可用
		if (Objects.isNull(client) || client.getStatus() == CommonStatusEnum.DISABLED.getStatus()) {
			return ResultUtil.result(ResultCode.UNABLE_CLIENT);
		}
		
		return loginValid(phone, password, client);
	}
	
	private BaseDto<Serializable> loginValid(String phone, String password, Client client) {
		//帐户是否可用
		Account account = accountService.selectAccountByPhone(phone);
		if (account.getStatus() == CommonStatusEnum.DISABLED.getStatus()) {
			return ResultUtil.result(ResultCode.UNABLE_ACOUNT);
		}

		
		// 密码验证
		String sha256 = SHAUtil.SHA256(password + account.getSalt());
		if (!account.getPassword().equals(sha256)) {
			return ResultUtil.result(ResultCode.CHECK_FAILED);
		}
		
		HashMap<String, Object> dtoMap = new HashMap<>();
		dtoMap.put("account", account);
		
		// 获取token
		long timestamp = TimeUtil.currentMilli();
		String token = MD5Util.md5(client.getClientCode() + phone + timestamp);
		token = accountCacheService.cacheToken(client.getClientCode(), phone, token);
		dtoMap.put("token", token);
		
		//1. 帐户level是否和机构type匹配
		switch (client.getType()) {
		case INNER:
			//内部系统判断内部帐户是否有权限
			if(AccountLevelEnum.INNER.getType().equals(account.getLevel())) {
				AccountBind accountBind = accountBindService.selectAccountBindByClientCodeAndAccountId(client.getClientCode(), account.getId());
				if(accountBind.getAuthStopTime().equals(0L)) {//授权时间比较
					return ResultUtil.success(dtoMap);
				}
				
				return ResultUtil.result(ResultCode.AUTH_EXPIRE);
			}else if (AccountLevelEnum.KEEPER.getType().equals(account.getLevel())) {
				return ResultUtil.success(dtoMap);
			}
			
			return ResultUtil.result(ResultCode.NOT_AUTH);
		case UNION:
			//提示需要用户授权
			break;
		case EXTER:
			if(AccountLevelEnum.EXTER.getType().equals(account.getLevel())) {
				return ResultUtil.success(dtoMap);
			}
			
			return ResultUtil.result(ResultCode.NOT_AUTH);
		default:
			break;
		}
		
		return ResultUtil.result(ResultCode.CHECK_FAILED);
	}

	public BaseDto<Serializable> vcodeLogin(String phone, String vcode, String clientCode) {
		Client client = clientService.selectClientByClientCode(clientCode);
		// 客户端是否可用
		if (Objects.isNull(client) || client.getStatus() == CommonStatusEnum.DISABLED.getStatus()) {
			return ResultUtil.result(ResultCode.UNABLE_CLIENT);
		}
		
		Account account = accountService.selectAccountByPhone(phone);
		if(Objects.isNull(account)) {
			account = new Account();
			account.setPhone(phone);
			account.setLevel(AccountLevelEnum.EXTER.getType());
			account.setStatus(CommonStatusEnum.USABLE.getStatus());
			long time = TimeUtil.currentMilli();
			account.setUpdateTime(time);
			account.setCreateTime(time);
			int accountResult = accountService.insertAccount(account);
			if(accountResult <= 0) {
				return ResultUtil.failed();
			}
		}
		
		HashMap<String, Object> dtoMap = new HashMap<>();
		dtoMap.put("account", account);
		
		// 获取token
		long timestamp = TimeUtil.currentMilli();
		String token = MD5Util.md5(client.getClientCode() + phone + timestamp);
		token = accountCacheService.cacheToken(client.getClientCode(), phone, token);
		dtoMap.put("token", token);
		
		return ResultUtil.success(dtoMap);
	}
	
	public BaseDto<Serializable> logout(String account, String clientCode, String token) {
		if(accountCacheService.delToken(clientCode, account, token)) {
			return ResultUtil.success();
		}
		
		return ResultUtil.failed();
	}

	public BaseDto<Serializable> checkToken(String account, String clientCode, String token) {
		if(accountCacheService.checkToken(clientCode, account, token)) {
			return ResultUtil.success();
		}
		
		return ResultUtil.failed();
	}

	/**
	 * 外部用户需要走注册
	 */
	public BaseDto<Serializable> register(Account account, String clientCode) {
		account.setLevel(AccountLevelEnum.EXTER.getType());
		account.setStatus(CommonStatusEnum.USABLE.getStatus());
		account.setSalt(SecretUtil.salt());
		account.setPassword(SHAUtil.SHA256(account.getPassword() + account.getSalt()));
		long time = TimeUtil.currentMilli();
		account.setUpdateTime(time);
		account.setCreateTime(time);
		if(StringUtil.isBlank(account.getNickname())) {
			
		}
		if(StringUtil.isBlank(account.getImg())) {
			
		}
		
		int accountResult = accountService.insertAccount(account);
		if(accountResult > 0) {
			return ResultUtil.success();
		}
		return ResultUtil.failed();
	}
}
