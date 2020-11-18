package BrowserFactory;

import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeDriverManager extends DriverManager{

	@Override
	public void createBrowser() {
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
	}

}
