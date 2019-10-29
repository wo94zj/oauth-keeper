package com.oauth.vip.pojo;

import com.oauth.vip.enums.ClientTypeEnum;

import lombok.Data;

/**
 * 机构信息
 *
 */
@Data
public class Client {

    private Long id;
    private String clientCode;	//机构标识
    private String clientSecret;	//机构分配密钥
    
    private String name;	//机构描述
    private String redirectUrl;	//机构地址
    
    private ClientTypeEnum type;	//机构类型，识别账户是否授权
    
    private Integer status;	//状态，见CommonStatusEnum
    
    private Long updateTime;
    private Long createTime;
}
