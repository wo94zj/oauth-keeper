package com.oauth.vip.enums;

public enum VipLevelEnum {

    KEEPER(0), //管理者，只能访问每个内部系统
    INNER(1), //内部用户，每个内部系统都需要授权
    EXTER(2); //外部用户，普通系统都可访问
    
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
