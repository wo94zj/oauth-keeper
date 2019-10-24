package com.oauth.vip.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oauth.dto.Account;
import com.oauth.vip.mapper.VipMapper;
import com.oauth.vip.pojo.Vip;

@Component
public class VipService {

    @Autowired
    private VipMapper vipMapper;
    
    public Account selectAccountByClientIdAndAccount(String clientId, String account) {
        return vipMapper.selectAccountByClientIdAndAccount(clientId, account);
    }
    
    public Vip selectVipByAccount(String account) {
        return vipMapper.selectVipByAccount(account);
    }
    
    public int insertVip(Vip vip) {
    	return vipMapper.insertVip(vip);
	}
    
}
