package com.genth.kkdc.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class CalculateCOI {

	private static final String USER_AGENT = "Mozilla/5.0";
	
	public static String sendGet(String _planCode,String _age,String _gender,String _premium,String _emVal,String _sumInsured) throws Exception {

		String url = "http://localhost:3000/getCoiForCallback?plan="+_planCode+"&age="+_age+"&gender="+_gender+"&prem="+_premium+"&emVal="+_emVal+"&sa="+_sumInsured;
        
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
	
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		if(true/*Integer.parseInt(_emVal) != 1*/)
		{
			System.out.println(url+" >>> ["+_emVal+"]"+response.toString());
		}
		
		//print result
//		System.out.println(response.toString());
		return response.toString();

	}
	public static String getFrontEndFee(String _planCode,String _premium) throws Exception {

		String url = "http://localhost:3000/frontEndFee?plan="+_planCode+"&prem="+_premium;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
//		System.out.println(response.toString());
		return response.toString();

	}

	public static int getMultipleSA (String sumInsured, String premium) {
		
		BigDecimal d_premium = new BigDecimal(premium.replaceAll(",", ""));
		BigDecimal d_sumInsured = new BigDecimal(sumInsured.replaceAll(",", ""));
		int multipleSA = 0;
		
		if (d_premium.compareTo(BigDecimal.ZERO) == 0  || d_sumInsured.compareTo(BigDecimal.ZERO) == 0) {
			System.out.println("ZERO :: d_premium="+d_premium + " ;; d_sumInsured="+d_sumInsured);
			return 0;
		}
		else {
			try 
			{
				multipleSA = (int) (d_sumInsured.doubleValue() /  d_premium.doubleValue());				
			}
			catch (ArithmeticException e) {
				multipleSA = 0;
			}
			
			return multipleSA;
		}
		 
	}
}
