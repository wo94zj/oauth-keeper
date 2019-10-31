package com.oauth.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * 账号和机构绑定信息
 *
 */
@Data
public class AccountBind implements Serializable {
    
    private static final long serialVersionUID = -6714493783060675694L;

    private Long id;
    private Long accountId;
    private String clientCode;
    
    //权限，多个用英文逗号隔开
    private String authority;
    
    //写0代表永久授权
    private Long authStartTime;
    private Long authStopTime;
    
    private Long updateTime;
    private Long createTime;
}
