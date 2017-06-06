package org.tech.commons.util;
/**
 * mac地址和16进制转换
 * @author fangyunhe
 *
 */
public class MacUtils {
	
	public static byte[] getMacBytes(String mac) {
		byte[] macBytes = new byte[6];
		String[] strArr = mac.split(":");

		for (int i = 0; i < strArr.length; i++) {
			int value = Integer.parseInt(strArr[i], 16);
			macBytes[i] = (byte) value;
		}
		return macBytes;
	}

	public static String getBytesMac(byte[] macBytes) {
		String value = "";
		for (int i = 0; i < macBytes.length; i++) {
			String sTemp = Integer.toHexString(0xFF & macBytes[i]);
			if(sTemp.length()==1){
				sTemp = "0"+sTemp;
			}
			value = value + sTemp + ":";
		}
		value = value.substring(0, value.lastIndexOf(":"));
		return value.toUpperCase();
	}
	
}
