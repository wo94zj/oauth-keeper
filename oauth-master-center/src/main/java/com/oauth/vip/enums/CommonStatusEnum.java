package com.oauth.vip.enums;

public enum CommonStatusEnum {

    USABLE(0), //可用
    DISABLED(1); //不可用
    
    private Integer status;
    
    private CommonStatusEnum(int status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
}
