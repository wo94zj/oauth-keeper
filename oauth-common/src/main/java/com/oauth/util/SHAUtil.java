package com.oauth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {

    public static String SHA256(final String data) {
        return SHA(data, "SHA-256");
    }

    public static String SHA512(final String data) {
        return SHA(data, "SHA-512");
    }

    private static String SHA(final String data, final String shaType) {
        // 返回值
        String sha = null;

        // 是否是有效字符串
        if (data != null && data.length() > 0) {
            try {
                // SHA 加密开始
                // 通过加密类型 创建加密对象
                MessageDigest messageDigest = MessageDigest.getInstance(shaType);
                // 传入要加密的字符串
                messageDigest.update(data.getBytes());
                // 得到byte类型结果
                byte byteBuffer[] = messageDigest.digest();

                // 将byte转换为string
                StringBuffer strHexString = new StringBuffer();
                // 遍历byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回结果
                sha = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
            }
        }

        return sha;
    }
    
    public static void main(String[] args) {
        System.out.println(SHAUtil.SHA256("111"));
        System.out.println(SHAUtil.SHA512("111"));
    }
}
