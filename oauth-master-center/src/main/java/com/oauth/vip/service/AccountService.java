package com.oauth.vip.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oauth.inner.IAccountService;
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
import com.oauth.vip.cache.IVipCacheService;
import com.oauth.vip.enums.AuthorityEnum;
import com.oauth.vip.enums.CommonStatusEnum;
import com.oauth.vip.pojo.Client;
import com.oauth.vip.pojo.Vip;
import com.oauth.vip.pojo.VipBind;
import com.oauth.vip.service.db.ClientService;
import com.oauth.vip.service.db.VipBindService;
import com.oauth.vip.service.db.VipService;

@Service(value = "accountService")
public class AccountService implements IAccountService {

	@Autowired
	private ClientService clientService;
	@Autowired
	private VipService vipService;
	@Autowired
	private VipBindService vipBindService;

	@Autowired
	private IVipCacheService vipCacheService;

	@Override
	public BaseDto<Serializable> login(String account, String clientId, String sign) {
		Client client = clientService.selectClientByClientId(clientId);

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

		VipBind bind = vipBindService.selectVipBindByClientIdAndAccount(clientId, account);
		// 账户信息判断
		if (Objects.isNull(bind)) {
			return ResultUtil.result(ResultCode.UNABLE_LOGIN);
		}
		long timestamp = TimeUtil.currentMilli();
		if(bind.getAuthStopTime() > 0 && bind.getAuthStopTime() < timestamp) {
			return ResultUtil.result(ResultCode.AUTH_EXPIRE);
		}

		Vip vip = vipService.selectVipByAccount(account);
		if (vip.getStatus() == CommonStatusEnum.DISABLED.getStatus()) {
			return ResultUtil.result(ResultCode.UNABLE_ACOUNT);
		}

		// 密码验证
		String sha256 = SHAUtil.SHA256(password + vip.getSalt());
		if (!vip.getPassword().equals(sha256)) {
			return ResultUtil.result(ResultCode.CHECK_FAILED);
		}

		// 获取token
		String token = MD5Util.md5(clientId + account + time);
		token = vipCacheService.cacheToken(clientId, account, token);

		HashMap<String, Object> dtoMap = new HashMap<>();
		dtoMap.put("account", vip.buildAccount());
		dtoMap.put("token", token);

		return ResultUtil.success(dtoMap);
	}

	@Override
	public BaseDto<Serializable> logout(String account, String clientId, String token, String sign) {
		if(vipCacheService.delToken(clientId, account, token)) {
			return ResultUtil.success();
		}
		
		return ResultUtil.failed();
	}

	@Override
	public BaseDto<Serializable> checkToken(String account, String clientId, String token, String sign) {
		if(vipCacheService.checkToken(clientId, account, token)) {
			return ResultUtil.success();
		}
		
		return ResultUtil.failed();
	}

	
	public BaseDto<Serializable> register(Vip vip, String clientId) {
		vip.setStatus(CommonStatusEnum.USABLE.getStatus());
		vip.setSalt(SecretUtil.salt());
		vip.setPassword(SHAUtil.SHA256(vip.getPassword() + vip.getSalt()));
		long time = TimeUtil.currentMilli();
		vip.setUpdateTime(time);
		vip.setCreateTime(time);
		if(StringUtil.isBlank(vip.getNickname())) {
			
		}
		if(StringUtil.isBlank(vip.getImg())) {
			
		}
		
		int vipResult = vipService.insertVip(vip);
		if(vipResult > 0) {
			VipBind vipBind = new VipBind();
			vipBind.setAccount(vip.getAccount());
			vipBind.setAuthority(AuthorityEnum.base.name());
			vipBind.setClientId(clientId);
			vipBind.setAuthStartTime(0L);
			vipBind.setAuthStopTime(0L);
			vipBind.setUpdateTime(time);
			vipBind.setCreateTime(time);
			
			int bindResult = vipBindService.insertVipBind(vipBind);
			if(bindResult > 0) {
				return ResultUtil.success();
			}
		}
		
		return ResultUtil.failed();
	}
}
