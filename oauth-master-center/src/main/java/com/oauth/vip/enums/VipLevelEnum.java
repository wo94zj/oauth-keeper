package com.oauth.vip.enums;

public enum VipLevelEnum {

    KEEPER(0), //管理者
    INNER(1), //内部用户
    EXTER(2); //外部用户
    
    private Integer type;
    
    private VipLevelEnum(int type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
}
