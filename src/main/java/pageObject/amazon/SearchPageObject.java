package pageObject.amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.AbstractPage;
import commons.GlobalConstants;
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

	public List<Phone> getAllItemInPage(Log log) {
		ArrayList<Phone> dataArrayList = new ArrayList<Phone>();
		
		//Lay tat ca cac element name can kiem tra sort
		List<WebElement> elementList = findElementsByXpath(driver, SearchPageUI.NAME_ITEM);
		System.out.println("================== DỮ LIỆU TRÊN GIAO DIỆN AMAZON ==========");
		
		for (WebElement webElement : elementList) {
			String price = null;
			WebElement link = null;
			WebElement wholePrice=null;
			WebElement fractionPrice=null;
			
			String name = webElement.getText();
			Phone aPhone = new Phone();
			aPhone.setName(name);

			overrideGlobalTimeout(driver, GlobalConstants.SHORT_TIMEOUT);
			
			try {
				link = findElementByXpath(driver, SearchPageUI.LINK_ITEM, name);
				wholePrice = findElementByXpath(driver, SearchPageUI.PRICE_WHOLE_ITEM, name);
				fractionPrice = findElementByXpath(driver, SearchPageUI.PRICE_FRACTION_ITEM, name);
			} catch (NoSuchElementException e) {
				System.out.println("================Amazon: Name has special character");
				String innerHTML = webElement.getAttribute("innerHTML");
				
				if(innerHTML.contains("&nbsp;")) {
					String[] innerHTMLArray = innerHTML.split("&nbsp;");
					fractionPrice = findElementByXpath(driver, SearchPageUI.PRICE_FRACTION_ITEM_SPECIAL, innerHTMLArray[0], innerHTMLArray[1]);
					link = findElementByXpath(driver, SearchPageUI.LINK_ITEM_SPECIAL, innerHTMLArray[0], innerHTMLArray[1]);
					wholePrice = findElementByXpath(driver, SearchPageUI.PRICE_WHOLE_ITEM_SPECIAL, innerHTMLArray[0], innerHTMLArray[1]);
				} else {
					System.out.println("=====================Amazon: Name has special character but not &nbsp; ");
				}
			}

			overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);

			if(link != null) {
				aPhone.setLink(link.getAttribute("href"));
			}
			
			try {
				if(wholePrice != null) {
					aPhone.setWholePrice(Integer.decode(wholePrice.getText()));
					price = "$" + wholePrice.getText();
				}
				
				if(fractionPrice != null) {
					aPhone.setFractionPrice(Integer.decode(fractionPrice.getText()));
					price = price + "." + fractionPrice.getText();
				}
				aPhone.setOriginPrice(price);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				log.error("Amazon: Parse price error with Phone name: " + name);
			}
			aPhone.setWebsite("Amazon");
			
			dataArrayList.add(aPhone);
		}

		System.out.println("==========Amazon data list size====" + dataArrayList.size());
		return dataArrayList;
	}
	
}
