package org.tech.commons.util;

/**
 * 各个数据类型的二进制转换
 * 规则：高位在先、低位在后
 * 代码来自网络
 * @author fangyunhe
 *
 */
public class DataTypeTranslateUtils {

	/**
	 *  1 short与byte数组的互转
	 */

	/**
	 * 转换short为byte
	 *
	 * @param b
	 * @param s
	 *            需要转换的short
	 * @param index
	 */
	public static void putShort(byte b[], short s, int index) {
		b[index + 0] = (byte) (s >> 8);
		b[index + 1] = (byte) (s >> 0);
	}

	/**
	 * 通过byte数组取到short
	 *
	 * @param b
	 * @param index
	 *            第几位开始取
	 * @return
	 */
	public static short getShort(byte[] b, int index) {
		return (short) (((b[index + 0] << 8) | b[index + 1] & 0xff));
	}

	/**
	 *  2. int与byte数组的互转
	 */

	/**
	 * 将32位的int值放到4字节的byte数组
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] intToByteArray(int num) {
		byte[] result = new byte[4];
		result[0] = (byte) (num >>> 24);// 取最高8位放到0下标
		result[1] = (byte) (num >>> 16);// 取次高8为放到1下标
		result[2] = (byte) (num >>> 8); // 取次低8位放到2下标
		result[3] = (byte) (num); // 取最低8位放到3下标
		return result;
	}

	/**
	 * 将4字节的byte数组转成一个int值
	 * 
	 * @param b
	 * @return
	 */
	public static int byteArrayToInt(byte[] b) {
		byte[] a = new byte[4];
		int i = a.length - 1, j = b.length - 1;
		for (; i >= 0; i--, j--) {// 从b的尾部(即int值的低位)开始copy数据
			if (j >= 0)
				a[i] = b[j];
			else
				a[i] = 0;// 如果b.length不足4,则将高位补0
		}
		int v0 = (a[0] & 0xff) << 24;// &0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
		int v1 = (a[1] & 0xff) << 16;
		int v2 = (a[2] & 0xff) << 8;
		int v3 = (a[3] & 0xff);
		return v0 + v1 + v2 + v3;
	}

	/**
	 *  3. long与byte数组的互转
	 */

	/**
	 * 将64位的long值放到8字节的byte数组
	 * 
	 * @param num
	 * @return 返回转换后的byte数组
	 */
	public static byte[] longToByteArray(long num) {
		byte[] result = new byte[8];
		result[0] = (byte) (num >>> 56);// 取最高8位放到0下标
		result[1] = (byte) (num >>> 48);// 取最高8位放到0下标
		result[2] = (byte) (num >>> 40);// 取最高8位放到0下标
		result[3] = (byte) (num >>> 32);// 取最高8位放到0下标
		result[4] = (byte) (num >>> 24);// 取最高8位放到0下标
		result[5] = (byte) (num >>> 16);// 取次高8为放到1下标
		result[6] = (byte) (num >>> 8); // 取次低8位放到2下标
		result[7] = (byte) (num); // 取最低8位放到3下标
		return result;
	}

	/**
	 * 将8字节的byte数组转成一个long值
	 * 
	 * @param byteArray
	 * @return 转换后的long型数值
	 */
	public static long byteArrayToLong(byte[] byteArray) {
		byte[] a = new byte[8];
		int i = a.length - 1, j = byteArray.length - 1;
		for (; i >= 0; i--, j--) {// 从b的尾部(即int值的低位)开始copy数据
			if (j >= 0)
				a[i] = byteArray[j];
			else
				a[i] = 0;// 如果b.length不足4,则将高位补0
		}
		// 注意此处和byte数组转换成int的区别在于，下面的转换中要将先将数组中的元素转换成long型再做移位操作，
		// 若直接做位移操作将得不到正确结果，因为Java默认操作数字时，若不加声明会将数字作为int型来对待，此处必须注意。
		long v0 = (long) (a[0] & 0xff) << 56;// &0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
		long v1 = (long) (a[1] & 0xff) << 48;
		long v2 = (long) (a[2] & 0xff) << 40;
		long v3 = (long) (a[3] & 0xff) << 32;
		long v4 = (long) (a[4] & 0xff) << 24;
		long v5 = (long) (a[5] & 0xff) << 16;
		long v6 = (long) (a[6] & 0xff) << 8;
		long v7 = (long) (a[7] & 0xff);
		return v0 + v1 + v2 + v3 + v4 + v5 + v6 + v7;
	}

	/**
	 *  4. float与byte数组的互转
	 */

	/**
	 * float转换byte
	 *
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putFloat(byte[] bb, float x, int index) {
		// byte[] b = new byte[4];
		int l = Float.floatToIntBits(x);
		for (int i = 0; i < 4; i++) {
			bb[index + i] = new Integer(l).byteValue();
			l = l >> 8;
		}
	}

	/**
	 * 通过byte数组取得float
	 *
	 * @param bb
	 * @param index
	 * @return
	 */
	public static float getFloat(byte[] b, int index) {
		int l;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		return Float.intBitsToFloat(l);
	}

	/**
	 *  5. double与byte数组的互转
	 */

	/**
	 * double转换byte
	 *
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putDouble(byte[] bb, double x, int index) {
		// byte[] b = new byte[8];
		long l = Double.doubleToLongBits(x);
		for (int i = 0; i < 4; i++) {
			bb[index + i] = new Long(l).byteValue();
			l = l >> 8;
		}
	}

	/**
	 * 通过byte数组取得float
	 *
	 * @param bb
	 * @param index
	 * @return
	 */
	public static double getDouble(byte[] b, int index) {
		long l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffl;
		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffl;
		l |= ((long) b[6] << 48);
		l &= 0xffffffffffffffl;
		l |= ((long) b[7] << 56);
		return Double.longBitsToDouble(l);
	}
}
