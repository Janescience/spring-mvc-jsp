package com.genth.kkdc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {

	private final String USER_AGENT = "Mozilla/5.0";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test http = new Test();

		System.out.println("Testing 1 - Send Http GET request");
		try {
			http.sendGet("UL01", "39", "F", "111000");
//			http.sendGet("UL01", "44", "M", "211000");
//			http.sendGet("UL01", "55", "F", "311000");
//			http.sendGet("UL01", "66", "M", "411000");
//			http.sendGet("UL01", "77", "F", "511000");
//			http.sendGet("UL01", "33", "M", "611000");
//			http.sendGet("UL01", "37", "F", "711000");
//			http.sendGet("UL01", "54", "M", "811000");
//			http.sendGet("UL01", "67", "F", "911000");
//			http.sendGet("UL01", "43", "M", "811000");
//			http.sendGet("UL01", "73", "F", "411000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// HTTP GET request
		private void sendGet(String _planCode,String _age,String _gender,String _premium) throws Exception {

//			String _planCode = "UL01";
//			String _age = "39";
//			String _gender = "F";
//			String _premium = "123456";
			
			String url = "http://192.168.0.167:3000/getCoiForCallback?plan="+_planCode+"&age="+_age+"&gender="+_gender+"&prem="+_premium;

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());

		}
}
