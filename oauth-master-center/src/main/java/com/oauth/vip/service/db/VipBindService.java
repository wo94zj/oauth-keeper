package com.oauth.vip.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oauth.vip.mapper.VipBindMapper;
import com.oauth.vip.pojo.VipBind;

@Service
public class VipBindService {

	@Autowired
    private VipBindMapper vipBindMapper;
	
	public VipBind selectVipBindByClientIdAndAccount(String clientId, String account) {
        return vipBindMapper.selectVipBindByClientIdAndAccount(clientId, account);
    }
	
	public int insertVipBind(VipBind vipBind) {
		return vipBindMapper.insertVipBind(vipBind);
	}
}
