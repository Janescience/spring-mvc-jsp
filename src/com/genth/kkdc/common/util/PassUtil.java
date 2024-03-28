package com.genth.kkdc.common.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class PassUtil {

	public static Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
	
	public static String enCodeWithMD5(String txt){
		
		return passwordEncoder.encodePassword(txt, null);   
	     
	}

	public static void main(String[] args) { 
		System.out.println(enCodeWithMD5("newpasstext"));
		System.out.println(enCodeWithMD5("newp@ss123"));
	}
}
