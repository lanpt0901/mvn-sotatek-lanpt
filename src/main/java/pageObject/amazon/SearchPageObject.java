package pageObject.amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.AbstractPage;
import data.Phone;
import pageUI.amazon.SearchPageUI;

public class SearchPageObject extends AbstractPage{

	WebDriver driver;
	public SearchPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public void fillKeySearch(String keySearch) {
		waitForElementsVisible(driver, SearchPageUI.SEARCH_TEXTBOX);
		sendKeyToElement(driver, SearchPageUI.SEARCH_TEXTBOX, keySearch);
	}
	
	public void submitSearch() {
		waitForElementsVisible(driver, SearchPageUI.SEARCH_SUBMIT_BUTTON);
		clickToElement(driver, SearchPageUI.SEARCH_SUBMIT_BUTTON);
	}
	
	public void openOtherPageWithUrl(String url) {
		openUrl(driver, url);
	}

	public List<Phone> getAllItemInPage() {
		ArrayList<Phone> dataArrayList = new ArrayList<Phone>();
		//Lay tat ca cac element name can kiem tra sort
		List<WebElement> elementList = findElementsByXpath(driver, SearchPageUI.NAME_ITEM);
		System.out.println("================== DỮ LIỆU TRÊN GIAO DIỆN AMAZON ==========");
		//Lay text cua tung element add vao ArrayList
		for (WebElement webElement : elementList) {
			String name = webElement.getText();
			System.out.println("==========name =========" + name);
			Phone aPhone = new Phone();
			aPhone.setName(name);
			
			WebElement link = findElementByXpath(driver, SearchPageUI.LINK_ITEM, name);
			WebElement wholePrice = findElementByXpath(driver, SearchPageUI.PRICE_WHOLE_ITEM, name);
			WebElement fractionPrice = findElementByXpath(driver, SearchPageUI.PRICE_FRACTION_ITEM, name);
			System.out.println("==========wholePrice =========" + wholePrice.getText());
			aPhone.setWholePrice(Integer.decode(wholePrice.getText()));
			aPhone.setFractionPrice(Integer.decode(fractionPrice.getText()));
			aPhone.setWebsite("Amazon");
			aPhone.setLink(link.getAttribute("href"));
			
			dataArrayList.add(aPhone);
		}
		
		
		return dataArrayList;
	}
	
}
