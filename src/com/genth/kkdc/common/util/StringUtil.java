package com.genth.kkdc.common.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
 
public class StringUtil{
	
	public static String formatDouble(double c){
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(c);
	}
	
	public static String formatDouble(double c,String format){
		DecimalFormat df = new DecimalFormat(format);
		return df.format(c);
	}
	
	public static String getTimestampStr(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss",  Locale.US);
		return formatter.format(new Date(System.currentTimeMillis()));
	}
	
	public static String getTimestampStr(String format){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format,  Locale.US);
			return formatter.format(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}
	
	public static String toDateString(double d){
		// Change double to string => 20141010 => 10/10/2014
		String date  = formatDouble(d, "##0");
		String day   = date.substring(6, 8);
		String month = date.substring(4, 6);
		String year  = date.substring(0, 4);
		return day + "/" + month + "/" + year;
	}
	
	public static String getFirstDayOfMonth(){
		Calendar cal = Calendar.getInstance(new Locale("EN"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",  Locale.US);
		
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    
	    String firstDayOfMonth = "";
	    
	    firstDayOfMonth = sdf.format(cal.getTime());
	    
	    return firstDayOfMonth;
	}
	
	public static String getLastDayOfMonth(){
		Calendar cal = Calendar.getInstance(new Locale("EN"));
		
	    cal.setTime(new Date());
	    
	    String firstDayOfMonth = getFirstDayOfMonth();
	    String lastDayOfMonth  = firstDayOfMonth.substring(0, 6) + String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH) );
	    
	    
	    return lastDayOfMonth;
	}
	
	public static String formatTimestamp(Timestamp c, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format,  Locale.US);
		
		return sdf.format(c);
	}
	public static Timestamp formatTimestamp(String c, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format,  Locale.US);
		
		try { 
			return new Timestamp(sdf.parse(c).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}
	public static void main(String[] args) { 
		System.out.println(formatTimestamp("19821130","yyyyMMdd"));
	}
}