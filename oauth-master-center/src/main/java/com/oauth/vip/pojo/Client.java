package com.oauth.vip.pojo;

import lombok.Data;

@Data
public class Client {

    private Integer id;
    private String clientId;
    private String clientSecret;
    
    private String name;
    private String redirectUrl;
    
    private Integer status;
    
    private Long updateTime;
    private Long createTime;
}
