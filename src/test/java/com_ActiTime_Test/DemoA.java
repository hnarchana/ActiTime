package com_ActiTime_Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.Reporter;
import org.testng.annotations.Test;

public class DemoA {
	
	@Test
	public void testA () throws IOException {
		URL url = new URL("https://www.qspiders.com/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		int status = con.getResponseCode();
		Reporter.log("Url Respose code is  ="+status, true );
		
		String message = con.getResponseMessage();
		System.out.println("response message is = "+message);
	}

}
