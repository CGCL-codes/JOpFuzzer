
package org.webdriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.openmbean.SimpleType;

import org.apache.commons.io.FileUtils;
import org.apache.http.entity.mime.content.FileBody;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class takingscreenshot {

	public static void main(String[] args) throws IOException  {
		
		WebDriver driver = new FirefoxDriver();
		
		driver.manage().window().maximize();
		
		driver.get("http://www.outlook.com");
		
		driver.findElement(By.id("i0116")).clear();
		
		driver.findElement(By.id("i0116")).sendKeys("testing");
		
		driver.findElement(By.id("i0118")).clear();
		
		driver.findElement(By.id("i0118")).sendKeys("testing");
	
		driver.findElement(By.id("idSIButton9")).click();
		
		DateFormat df = new  SimpleDateFormat("yyyy-mm-dd,hh-mm-ss");
		
		// Current system date
		Date d = new Date();
		
		//get system date using date format
		String time = df.format(d);
		//
		System.out.println(time);
		
	
		File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcfile, new File("\\D: ketam"+time+".png"));
			System.out.println("screen shot done succesfully");
		} catch (IOException e) {
			
		
			//e.printStackTrace();
		}
		
		

	}

	
}

