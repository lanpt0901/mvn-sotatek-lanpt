package BrowserFactory;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InternetExplorerDriverManager extends DriverManager{

	@Override
	public void createBrowser() {
		WebDriverManager.iedriver().arch32().setup(); //win 32bit hay 64bit deu chay voi arch32, chay ban 64 rats cham
		InternetExplorerOptions options = new InternetExplorerOptions();
		driver = new InternetExplorerDriver(options);
	}

}
