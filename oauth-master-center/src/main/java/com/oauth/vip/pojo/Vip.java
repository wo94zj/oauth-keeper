package com.oauth.vip.pojo;

import java.io.Serializable;

import com.oauth.dto.Account;

import lombok.Data;

@Data
public class Vip implements Serializable {

    private static final long serialVersionUID = -1584452090599765954L;
    
    private Integer id;
    private String nickname;
    private String account;
    private String img;
    private String password;
    private String salt;
    private Integer level;
    private Integer status;
    private Long updateTime;
    private Long createTime;
    
    public Account buildAccount() {
    	Account account =  new Account();
    	account.setAccount(this.account);
    	account.setNickname(nickname);
    	account.setImg(img);
    	return account;
	}
}
