package com.oauth.util;

import java.util.UUID;

public class SecretUtil {

	/**
	 * 随机生成盐
	 */
    public static String salt() {
        return MD5Util.md5("salt-" + System.currentTimeMillis() + "-" + UUID.randomUUID());
    }
    
    /**
     * 随机生成密钥
     */
    public static String secret() {
        return SHAUtil.SHA256("secret-" + System.currentTimeMillis() + "-" + UUID.randomUUID());
    }
    
    /**
     * 随机numb位数的数字
     */
    public static String numeric(int numb) {
		return String.valueOf((int) (Math.random() * Integer.valueOf("1" + StringUtil.repeat("0", numb))));
	}
    
    public static void main(String[] args) {
        String salt = SecretUtil.salt();
        System.out.println(salt);
        System.out.println(SHAUtil.SHA256("123456" + salt));
        
        System.out.println(SecretUtil.secret());
    }
}
