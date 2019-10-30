package com.oauth.inner;

import java.io.Serializable;

import com.oauth.resp.BaseDto;

public interface IAccountService {

    BaseDto<Serializable> signLogin(String account, String clientCode, String sign);
    BaseDto<Serializable> pwdLogin(String phone, String password, String clientCode);
    
    BaseDto<Serializable> logout(String account, String clientCode, String token);
    
    BaseDto<Serializable> checkToken(String account, String clientCode, String token);
}
