package BrowserFactory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import commons.GlobalConstants;

/**
 * Abstract class DriverManager for Browser Factory Patter
 * @author LAN
 *
 */
public abstract class DriverManager {

	protected WebDriver driver;
	public abstract void createBrowser();
	
	public void quitDriver() {
		if(driver != null) {
			driver.quit();
		}
	}
	
	public WebDriver getDriver(String browserUrl) {
		//singleton Pattern
		if(driver == null) {
			createBrowser();
		} 
		
		driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(browserUrl);
		return driver;
	}
}
