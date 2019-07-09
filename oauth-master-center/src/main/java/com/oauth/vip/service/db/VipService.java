package com.oauth.vip.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oauth.dto.Account;
import com.oauth.vip.mapper.VipMapper;
import com.oauth.vip.pojo.Vip;
import com.oauth.vip.pojo.VipBind;

@Component
public class VipService {

    @Autowired
    private VipMapper accountMapper;
    
    public Account selectAccountByClientIdAndAccount(String clientId, String account) {
        return accountMapper.selectAccountByClientIdAndAccount(clientId, account);
    }
    
    public Vip selectVipByAccount(String account) {
        return accountMapper.selectVipByAccount(account);
    }
    
    public VipBind selectVipBindByClientIdAndAccount(String clientId, String account) {
        return accountMapper.selectVipBindByClientIdAndAccount(clientId, account);
    }
    
}
