package com.oauth.vip.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * 账号和机构绑定信息
 *
 */
@Data
public class VipBind implements Serializable {
    
    private static final long serialVersionUID = -6714493783060675694L;

    private Integer id;
    private String account;
    private String clientId;
    
    //权限，多个用英文逗号隔开
    private String authority;
    
    //写0代表永久授权
    private Long authStartTime;
    private Long authStopTime;
    
    private Long updateTime;
    private Long createTime;
}
