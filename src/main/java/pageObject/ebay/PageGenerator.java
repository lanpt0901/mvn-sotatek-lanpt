package pageObject.ebay;

import org.openqa.selenium.WebDriver;

public class PageGenerator {

	public static HomePageObject getHomePage(WebDriver driver) {
		return new HomePageObject(driver) ;
	}

}
