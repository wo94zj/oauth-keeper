package com.oauth.vip.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class VipBind implements Serializable {
    
    private static final long serialVersionUID = -6714493783060675694L;

    private Integer id;
    private String account;
    private String clientId;
    //权限
    private String authority;
    
    private Long authStartTime;
    private Long authStopTime;
    
    private Long updateTime;
    private Long createTime;
}
