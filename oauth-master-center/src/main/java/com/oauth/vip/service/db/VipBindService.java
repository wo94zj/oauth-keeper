package com.oauth.vip.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oauth.vip.mapper.VipBindMapper;
import com.oauth.vip.pojo.VipBind;

@Service
public class VipBindService {

	@Autowired
    private VipBindMapper vipBindMapper;
	
	public VipBind selectVipBindByClientCodeAndAccountId(String clientCode, long accountId) {
        return vipBindMapper.selectVipBindByClientCodeAndAccountId(clientCode, accountId);
    }
	
	public int insertVipBind(VipBind vipBind) {
		return vipBindMapper.insertVipBind(vipBind);
	}
}
