package BrowserFactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CocCocDriverManager extends DriverManager{

	@Override
	public void createBrowser() {
		WebDriverManager.chromedriver().driverVersion("84.0.4147.30").setup();
		ChromeOptions options = new ChromeOptions();
		options.setBinary("C:\\Users\\HAU\\AppData\\Local\\CocCoc\\Browser\\Application\\browser.exe");
		driver = new ChromeDriver(options);
	}

}
