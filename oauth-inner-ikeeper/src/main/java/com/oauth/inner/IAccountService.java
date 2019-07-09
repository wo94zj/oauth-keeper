package com.oauth.inner;

import java.io.Serializable;

import com.oauth.resp.BaseDto;

public interface IAccountService {

    BaseDto<Serializable> login(String account, String clientId, String sign);
    
    BaseDto<Serializable> logout(String account, String clientId, String token, String sign);
    
    BaseDto<Serializable> checkToken(String account, String clientId, String token, String sign);
}
