package com.oauth.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    /**
     * 算法
     */
    private static final String CIPHER_ALGORITHM = "AES";

    /**
     * 加密数据
     *
     * @param data 待加密内容
     * @param key  加密的密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) {
        try {
            // 获得密钥
            Key deskey = keyGenerator(key);
            // 实例化一个密码对象
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 密码初始化
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            // 执行加密
            byte[] bytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            // 返回Base64编码后的字符串
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            return null; 
        }
    }

    /**
     * 解密数据
     *
     * @param data 待解密的内容
     * @param key  解密的密钥
     * @return 解密后的文字
     */
    public static String decrypt(String data, String key) {
        try {
            // 生成密钥
            Key kGen = keyGenerator(key);
            // 实例化密码对象
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化密码对象
            cipher.init(Cipher.DECRYPT_MODE, kGen);
            // 执行解密
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(data));
            // 返回解密后的字符串
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            return null; // 获取加密名称异常
        }
    }

    /**
     * 获取密钥
     *
     * @param key 密钥字符串
     * @return 返回一个密钥
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static Key keyGenerator(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes(StandardCharsets.UTF_8));
        
        KeyGenerator kGen = KeyGenerator.getInstance(CIPHER_ALGORITHM);
        kGen.init(256, random);
        
        SecretKey secretKey = kGen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return new SecretKeySpec(encoded, CIPHER_ALGORITHM);
    }

    public static void main(String[] args) {
        String encrypt = AESUtil.encrypt("111", "3381229f1beb087e92d924e65f63e649503fdb74304c632b604af9d2275ce09e");
        System.out.println(encrypt);
        System.out.println(AESUtil.decrypt("VAHFmM0TOStNFIaehXQ6oTarHqnUsu+oKhq6ig3rpuzXXtgt5zn4Tqa2dEXCzhhZ", "3381229f1beb087e92d924e65f63e649503fdb74304c632b604af9d2275ce09e"));
        

    }
}
