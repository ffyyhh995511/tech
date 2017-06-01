package org.tech.commons.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;



/**
 * java aes 128 ecb 模式
 * @author fangyunhe
 *
 */
public class AESUtils {
	private static final Logger logger = Logger.getLogger(FileUpLoadUtil.class);  
	/**
	 * 原生byte数组加密 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] Encrypt(byte sSrc[], byte sKey[]) throws Exception {
        if (sKey == null) {
        	logger.error("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length != 16) {
        	logger.error("Key长度不是16位");
            return null;
        }
        SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc);
        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return encrypted;
    }
	
	
	/**
	 * 原生byte数组解密
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] Decrypt(byte sSrc[], byte sKey[]) throws Exception {
		byte[] original = null;
        try {
            // 判断Key是否正确
            if (sKey == null) {
            	logger.error("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length != 16) {
            	logger.error("Key长度不是16位");
                return null;
            }
            SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            try {
                original = cipher.doFinal(sSrc);
            } catch (Exception e) {
            	e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return original;
    }
	
	
	/**
	 * 字符串加密
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
        	logger.error("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
        	logger.error("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return new Base64().encodeToString(encrypted);
    }

    /**
     * 字符串解码
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
            	logger.error("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
            	logger.error("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
            	logger.error(e.toString());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    
}
