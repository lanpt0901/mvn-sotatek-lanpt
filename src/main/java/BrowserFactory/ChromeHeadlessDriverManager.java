package BrowserFactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeHeadlessDriverManager extends DriverManager{

	@Override
	public void createBrowser() {
		WebDriverManager.chromedriver().setup();
		DesiredCapabilities capacities = new DesiredCapabilities().chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless"); // chay voi tab ẩn danh private
		options.addArguments("window-size"); 
		capacities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver();
	}

}
