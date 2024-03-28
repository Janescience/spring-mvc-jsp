package com.genth.kkdc.service;

import java.util.ResourceBundle;

public class ResourceConfig{
	
	private static ResourceBundle commonconfig = ResourceBundle.getBundle("config");
	
	public static String getCommonProperty(String key){
		return commonconfig.getString(key);
	}
	
	public static String [] getArray(String key, String delim){
		return ((String)commonconfig.getString(key)).split(delim);
		
	}
	
}
