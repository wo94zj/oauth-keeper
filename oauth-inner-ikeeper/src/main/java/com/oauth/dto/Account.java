package com.oauth.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 账户登录返回基础信息
 *
 */
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -1395485239634438912L;

    private String nickname;
    private String account;
    private String img;
    
}
