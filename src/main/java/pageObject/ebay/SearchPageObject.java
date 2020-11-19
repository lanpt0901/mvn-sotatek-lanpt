package pageObject.ebay;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import commons.AbstractPage;
import commons.GlobalConstants;
import data.Phone;
import pageUI.ebay.SearchPageUI;

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
		System.out.println("==================DỮ LIỆU TRÊN GIAO DIỆN EBAY ==========");
		
		for (WebElement webElement : elementList) {

			String price = null;
			WebElement link = null;
			WebElement wholePrice=null;
			
			String name = webElement.getText();
			Phone aPhone = new Phone();
			aPhone.setName(name);
			System.out.println("==========name: =========" + name);

			overrideGlobalTimeout(driver, GlobalConstants.SHORT_TIMEOUT);
			
			try {
				link = findElementByXpath(driver, SearchPageUI.LINK_ITEM, name);
				wholePrice = findElementByXpath(driver, SearchPageUI.PRICE_WHOLE_ITEM, name);
				
				price = wholePrice.getText();
				System.out.println("==========wholePrice =========" + price);
				
			} catch (NoSuchElementException e) {
				System.out.println("=====================EBay: Name has special character");
				String innerHTML = webElement.getAttribute("innerHTML");
				System.out.println("==========innerHTML =========" + innerHTML);
				if(innerHTML.contains("&nbsp;")) {
					
					if(innerHTML.contains("</span>")) {
						innerHTML = innerHTML.split("</span>")[1];
					}
					
					String[] innerHTMLArray = innerHTML.split("&nbsp;");
					
					link = findElementByXpath(driver, SearchPageUI.LINK_ITEM_SPECIAL, innerHTMLArray[0], innerHTMLArray[1]);
					wholePrice = findElementByXpath(driver, SearchPageUI.PRICE_WHOLE_ITEM_SPECIAL, innerHTMLArray[0], innerHTMLArray[1]);
					System.out.println(castToObject(SearchPageUI.LINK_ITEM_SPECIAL, innerHTMLArray[0], innerHTMLArray[1]));
				} else {
					System.out.println("=====================EBay: Name has special character but not &nbsp; ");
				}
			}

			overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
			
			if(link != null) {
				aPhone.setLink(link.getAttribute("href"));
			}
			
			try {
				if(price != null) {
					aPhone.setOriginPrice(price);
					price = price.trim().replace(",", "");
					String [] priceStr = price.split("\\$");
					String []priceI = priceStr[1].split("\\.");
					//xu ly price
					aPhone.setWholePrice(Integer.decode(priceI[0]));
					if(priceI[1].contains("to")) {
						System.out.println("------to price-----" + priceI[1].split(" to")[0]);
						aPhone.setFractionPrice(Integer.decode(priceI[1].split(" to")[0]));
					} else {
						aPhone.setFractionPrice(Integer.decode(priceI[1]));
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				log.error("EBay: Parse price error with Phone name: " + name);
			}
			aPhone.setWebsite("Ebay");
			
			dataArrayList.add(aPhone);
		}

		System.out.println("==========eBay data list size====" + dataArrayList.size());
		return dataArrayList;
	}

	public void changeDollarCurrency() {
		waitForElementsVisible(driver, SearchPageUI.MENU_ICON);
		clickToElement(driver, SearchPageUI.MENU_ICON);
		
		waitForElementsVisible(driver, SearchPageUI.CUSTOMIZE_ICON);
		clickToElement(driver, SearchPageUI.CUSTOMIZE_ICON);
		
		waitForElementsVisible(driver, SearchPageUI.CURRENCY_CHECKBOX);
		uncheckTheCheckBoxByJS(driver, SearchPageUI.CURRENCY_CHECKBOX);

		waitForElementsVisible(driver, SearchPageUI.APPLY_BUTTON);
		clickToElementByJS(driver, SearchPageUI.APPLY_BUTTON);

		sleepInSecond(5);
	}

	
}
