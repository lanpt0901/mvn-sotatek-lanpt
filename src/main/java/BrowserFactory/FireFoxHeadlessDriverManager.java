package BrowserFactory;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FireFoxHeadlessDriverManager extends DriverManager{

	@Override
	public void createBrowser() {
		WebDriverManager.firefoxdriver().setup();
		DesiredCapabilities capabilities = null;
		FirefoxOptions options = new FirefoxOptions(capabilities);
		options.setHeadless(true);
		options.setCapability("marionette", false);
	    capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
		driver = new FirefoxDriver(capabilities);
		
	}

}
