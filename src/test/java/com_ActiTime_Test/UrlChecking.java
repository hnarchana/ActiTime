/*Script to check for url break*/
package com_ActiTime_Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UrlChecking {
	
	@Test
	public void testUrl() throws IOException, InterruptedException
	{
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("file:///C:/Users/apple/Downloads/broken.html");
		WebElement link = driver.findElement(By.linkText("qspiders"));
		Reporter.log("element name is = "+link.getText(), true);
		
		String href = link.getAttribute("href");
		Reporter.log("attribute value = "+href, true);
		
		try
		{
		URL url = new URL(href);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		int status = con.getResponseCode();
		Reporter.log("url status code = "+status, true);
		
		if(status == 200)
		{
			Reporter.log("Link is not broken....", true);
		}
		else
		{
			Reporter.log("link is broken...", true);
			String message = con.getResponseMessage();
			Reporter.log("response message =" +message , true);	
		}
		}
		catch(Exception e)
		{
			Reporter.log("url is invalid ",true);
		} 
		Thread.sleep(2000);
		driver.close();
		
	}

}
