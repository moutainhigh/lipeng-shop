package com.lipeng.core.utils;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: lipeng 910138
 * @Date: 2019/11/12 11:27
 */
public class UrlUtil {

    private static final String KEY = "myMw6qPt&3AD";

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtil.class);

    public static void main(String[] args) throws Exception {
        String source = "123456";
        System.out.println("待加密字段:" + source);
        String result = enCryptAndEncode(source);
        System.out.println("加密后:" + result);
        String source_2 = deCryptAndDecode(result);
        System.out.println("解密后:" + source_2);
        System.out.println("判断是否相等:" + source.equals(source_2));
    }


    public static String enCryptAndEncode(String content) {
        try {
            byte[] sourceBytes = enCryptAndEncode(content, KEY);
            return Base64.encodeBase64URLSafeString(sourceBytes);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return content;
        }
    }

    /**
     * 加密函数
     *
     * @param content 加密的内容
     * @param strKey 密钥
     * @return 返回二进制字符数组
     */
    public static byte[] enCryptAndEncode(String content, String strKey) throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom(strKey.getBytes()));

        SecretKey desKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        return cipher.doFinal(content.getBytes("UTF-8"));
    }

    public static String deCryptAndDecode(String content) {
        try {
            byte[] targetBytes = Base64.decodeBase64(content);
            return deCryptAndDecode(targetBytes, KEY);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }


    /**
     * 解密函数
     *
     * @param src 加密过的二进制字符数组
     * @param strKey 密钥
     */
    public static String deCryptAndDecode(byte[] src, String strKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom(strKey.getBytes()));

        SecretKey desKey = keyGenerator.generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        byte[] cByte = cipher.doFinal(src);
        return new String(cByte, "UTF-8");
    }

}