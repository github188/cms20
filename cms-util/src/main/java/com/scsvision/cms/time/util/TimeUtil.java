package com.scsvision.cms.time.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * TimeUtil
 * 
 * @author zhuanqi <p/> Create at 2013 下午12:46:17
 */
public class TimeUtil {

	private static String getFormatStr(int formatType) {
		String formatStr = null;
		switch (formatType) {
		case 1:
			formatStr = "yyyy-MM-dd";
			break;
		case 2:
			formatStr = "yyyy-MM-dd HH:mm:ss";
			break;
		case 3:
			formatStr = "yyyy-MM-dd HH:mm:ss SSS";
			break;
		case 4:
			formatStr = "yyyyMMdd";
			break;
		case 5:
			formatStr = "yyyyMMddHHmmss";
			break;
		case 6:
			formatStr = "yyyyMMddHHmmssSSS";
			break;
		case 7:
			formatStr = "yyyyMM";
			break;
		case 8:
			formatStr = "HH:mm:ss";
			break;
		default:
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		return formatStr;
	}

	public static Date parse(String str, String format, Date defaultValue) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(str);
			return date;
		} catch (Exception e) {
			return defaultValue;
		}
	}


	public static String getStringTime(long timeLong, int formatType) {
		String time = "";
		if (timeLong > 0) {
			String formatStr = getFormatStr(formatType);
			DateFormat df = new SimpleDateFormat(formatStr);
			time = df.format(timeLong);
		}
		return time;
	}

	
	public static String getLongTime(String timeStr,int formatType){
		long time = 0;
		String formatStr = getFormatStr(formatType);
		if (timeStr != null && timeStr.length() > 0) {
			DateFormat df = new SimpleDateFormat(formatStr);
			try {
				time = df.parse(timeStr).getTime();
			} catch (ParseException e) {

			}
		}
		return time+"";
	}
	
	/**
	 * 
	 * 获取当前日期
	 * @return
	 * @author zhuanqi <p />
	 * Create at 2013 10:06:52 AM
	 */
	public static String getCurDay() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
		return (f.format(c.getTime()));
	}

	/**
	 * 获取当前日期是星期几
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}
	
	public static void main(String args[]){
		long l = 1368066067374L;
		System.out.println(getStringTime(l,3));
		String str = "2013-05-09 10:21:07";
		System.out.println(getLongTime(str,2));
	}
}
