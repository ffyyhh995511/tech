package org.tech.commons.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5工具类
 * @author fangyunhe
 *
 * 2017年8月4日 下午2:27:41
 */
public class MD5Util {

	public static void main(String[] args) {
		String str = encryptionLowerCase("123456");
		System.out.println("加密结果：" + str);
		String str2 = encryptionUpperCase("123456");
		System.out.println("加密结果：" + str2);
	}

	/**
	 * 小写
	 * 
	 * @param plain
	 * @return
	 */
	public static String encryptionLowerCase(String plain) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plain.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}

	/**
	 * 大写
	 * 
	 * @param plain
	 * @return
	 */
	public static String encryptionUpperCase(String plain) {
		String encryptionLowerCase = encryptionLowerCase(plain);
		return encryptionLowerCase.toUpperCase();
	}
}