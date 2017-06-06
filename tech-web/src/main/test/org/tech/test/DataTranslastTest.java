package org.tech.test;

import java.math.BigInteger;

import org.junit.Test;

/**
 * 各种二、八、十、十六进制的转换测试
 * @author fangyunhe
 *
 */
public class DataTranslastTest {
	
	
	@Test
	public void test(){
//		int a = 20;
//		System.out.println(a);
//		System.out.println(Integer.toHexString(a));
//		System.out.println(Integer.toBinaryString(a));
		
//		String h = "20";
//      BigInteger srch = new BigInteger(h, 16);
//      System.out.println(srch);
        
//		System.out.println(Integer.toBinaryString(Integer.parseInt("0xAA",16)));

        

        int a = 10 * 0Xff;
        System.out.println(a);

//		System.out.println(Integer.parseInt("0xAA",16));
	}
	
	public static byte[] HexStrToBytes(String str){
        //如果字符串长度不为偶数，则追加0
        if(str.length() % 2 !=0){
            str = "0"+str;
        }
        
        byte[] b = new byte[str.length() / 2];
        byte c1, c2;
        for (int y = 0, x = 0; x < str.length(); ++y, ++x)
        {
            c1 = (byte)str.charAt(x);
            if (c1 > 0x60) c1 -= 0x57;
            else if (c1 > 0x40) c1 -= 0x37;
            else c1 -= 0x30;
            c2 = (byte)str.charAt(++x);
            if (c2 > 0x60) c2 -= 0x57;
            else if (c2 > 0x40) c2 -= 0x37;
            else c2 -= 0x30;
            b[y] = (byte)((c1 << 4) + c2);
        }
        return b;
    }
    
	
	@Test
	public void test1(){
		System.out.println(HexStrToBytes("20").toString());
	}
	
}
