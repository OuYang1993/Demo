package com.masget.network_library.util;



import com.masget.network_library.bean.Constants;

import org.apache.commons.codec.binary.Base64;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by OuYang on 2015/12/25.
 * 加密工具类
 */
public class EncryptUtil {

    /**
     * AES加密算法
     *
     * @param data 加密数据
     * @param key  加密参数
     * @param iv   加密参数
     * @return result
     */
    public static String aesEncrypt(String data, String key, String iv) {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes("UTF-8");
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength
                        + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            Base64 base64 = new Base64();
            String base64str = new String(base64.encode(encrypted), "utf-8");
            base64str = base64str.replace("\n", "").replace("\r", "").replace('+', '-').replace('/', '_');
            return base64str;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * AES解密算法
     *
     * @param data 解密数据
     * @param key  解密参数
     * @param iv   解密参数
     * @return 解密后的字符串
     */
    public static String aesDecrypt(String data, String key, String iv) {
        try {

            Base64 base64 = new Base64();
            byte[] encrypted1 = base64.decode(data.replace('-', '+').replace('_', '/').getBytes("utf-8"));
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8").trim();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }


    /**
     * 对结果进行 md5 校验
     *
     * @param data data
     * @param sign sign
     * @return true-校验通过   false-校验失败
     */
    public static boolean verifyResult(String data, String sign) {
        String md5 = EncryptUtil.string2MD5(data + Constants.AES_KEY);
        if (md5.equals(sign)) {//校验通过
            return true;
        } else {
            return false;
        }
    }

}
