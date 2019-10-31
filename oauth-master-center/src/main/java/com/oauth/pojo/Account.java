package com.oauth.pojo;

import java.io.Serializable;

import com.oauth.enums.AccountLevelEnum;
import com.oauth.enums.CommonStatusEnum;

import lombok.Data;

/**
 * 客户账号
 *
 */
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -1584452090599765954L;
    
    private Long id;
    
    private String nickname;	//昵称
    
    private String img;	//头像
    
    private String password;	//密码
    private String salt;	//加盐
    
    
    private String phone;	//手机号
    
    private Integer level;	//用户标记，见AccountLevelEnum
    
    private Integer status;
    
    private Long updateTime;
    private Long createTime;
    
    public interface Create{}
    
    public void buildAccount() {
    	this.level = AccountLevelEnum.EXTER.getType();
    	this.status = CommonStatusEnum.USABLE.getStatus();
	}
    
}
