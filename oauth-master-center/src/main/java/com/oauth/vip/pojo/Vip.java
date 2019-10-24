package com.oauth.vip.pojo;

import java.io.Serializable;

import com.oauth.dto.Account;

import lombok.Data;

/**
 * 客户账号
 *
 */
@Data
public class Vip implements Serializable {

    private static final long serialVersionUID = -1584452090599765954L;
    
    private Long id;
    private String nickname;	//昵称
    private String account;	//用户标记
    private String img;	//头像
    
    private String password;	//密码
    private String salt;	//加盐
    
    private Integer level;	//用户标记，见VipLevelEnum
    
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
