package com.shoppingmall.util;

import java.security.MessageDigest;

/**
 * @author Bert Q
 * @description //MD5 加盐值工具类
 * @date 9:21 2019/10/2
 */
public class MD5Utils {
    private static String SALT ="5dq9D8f53Dd5afgFdfXdqDF27dO0oPAz79";

    public void setSALT(String salt) {
        SALT = salt;
    }

    public static String getEncodingMD5(String src) {
//        return enCrypt(src + SALT);
        return src;
    }


    private static String enCrypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] s = md.digest(str.getBytes());
            String ss;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < s.length; i++) {
                ss = Integer.toHexString(s[i] & 0xff);
                if (ss.length() == 1) {
                    result.append("0").append(ss);
                } else {
                    result.append(ss);
                }
            }
            return result.toString();
        } catch (Exception em) {
            em.printStackTrace();
        }
        return null;
    }
}
