package com_ActiTime_Test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ActiTimeImageTest
{

	@Test
	public void image() throws IOException, InterruptedException
	{
		WebDriverManager.chromedriver().setup();
		ChromeOptions cp = new ChromeOptions();
		cp.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(cp);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://demo.actitime.com/");
		
		// get the screenshot of the page
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		Files.copy(src, new File("./src/main/resources/ScreenShots/demo.png"));
		
		//locating  the actiTime logo 
		WebElement img = driver.findElement(By.xpath("//div[@class = 'atLogoImg']"));
		
		//getting the location n size of the logo
		int x = img.getLocation().getX();
		int y = img.getLocation().getY();
		int h = img.getSize().getHeight();
		int w = img.getSize().getWidth();
		
		//crop the image
		BufferedImage orgimg = ImageIO.read(new File("./src/main/resources/ScreenShots/demo.png"));
		BufferedImage subimg = orgimg.getSubimage(x, y, w, h);
		
		ImageIO.write(subimg, "png", new File("./src/main/resources/ScreenShots/d.png"));
		
		//comparing cropped image with stored image
		BufferedImage aimg = ImageIO.read(new File("./src/main/resources/ScreenShots/demo.png"));
		BufferedImage eimg = ImageIO.read(new File("./src/main/resources/ScreenShots/d.png"));
		
		//getting image pixel
		DataBuffer aimgpix = aimg.getData().getDataBuffer();
		DataBuffer eimgpix = eimg.getData().getDataBuffer();
		
		int apixcount = aimgpix.getSize();
		int epixcount = eimgpix.getSize();
		
		Reporter.log("Actual image count is =" +apixcount , true);
		Reporter.log("expected image  count is = "+epixcount, true);
		
		int matchcount = 0, count = 0 ;
		
		if(apixcount > epixcount)
		{
			count = epixcount;
		}
		else
		{
			count = apixcount;
		}
		
		for (int i=0; i<count; i++)
		{
			if(aimgpix.getElem(i) == eimgpix.getElem(i))
			{ 
				matchcount++;
			}
		}
		Reporter.log("matching count  = " +matchcount, true);
		
		int percentage = (matchcount*100)/epixcount;
		if(percentage >= 72)
		{
			Reporter.log("image is not broken ="+percentage , true);
		}
		else
			Reporter.log("image is broken ="+percentage, true);
			
		Thread.sleep(1000);
		driver.close();
		
	}
}
