package org.tech.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**定义常量**/
	public static final String year2second = "yyyy-MM-dd HH:mm:ss";
	
	public static final String year2day = "yyyy-MM-dd";
	
    public static final String DATE_y2d_STR="yyyyMMdd";
    
    public static final String DATE_y2s_STR = "yyMMddHHmmss";
	
	
	public static String date2String(String format,Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String createDate = sdf.format(date);
		return createDate;
	}
	
	public static Date string2Date(String type,String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		return sdf.parse(date);
	}
	
	/**
	 * 比较是否在两个日期之内
	 * @param preDate  被比较的日期1
	 * @param compareDate 要比较的日期
	 * @param afterDate  被比较的日期2
	 * @return
	 */
	public static boolean isBetweenDates(Date preDate,Date compareDate,Date afterDate){
		if(preDate.getTime() <= compareDate.getTime() && compareDate.getTime() <= afterDate.getTime()){
			return true;
		}
		return false;
	}
	
	/**
	 * 比较是否在两个日期之内
	 * @param preDate  被比较的日期1
	 * @param compareDate 要比较的日期
	 * @param afterDate  被比较的日期2
	 * @return
	 * @throws ParseException 
	 */
	public static boolean isBetweenDates(String preDateStr,String compareDateStr,String afterDateStr) throws ParseException{
		Date preDate = string2Date(year2second, preDateStr);
		Date compareDate = string2Date(year2second, compareDateStr);
		Date afterDate = string2Date(year2second, afterDateStr);
		
		if(preDate.getTime() <= compareDate.getTime() && compareDate.getTime() <= afterDate.getTime()){
			return true;
		}
		
		return false;
	}
	
	/**
     * 获取系统当前时间
     * @return
     */
    public static String getNowTime(String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        return df.format(new Date());
    }
}
