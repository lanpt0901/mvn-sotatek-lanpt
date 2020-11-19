package com.amazon.ebay.search;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import BrowserFactory.DriverManager;
import BrowserFactory.DriverManagerFactory;
import commons.AbstractTest;
import data.Phone;
import pageObject.amazon.SearchPageObject;

public class Search_01_Sort extends AbstractTest{

	private WebDriver driver;
	private DriverManager driverManager;
	private SearchPageObject amazonSearchPage;
	private pageObject.ebay.SearchPageObject ebaySearchPage;
	private String keySearch;
	private String amazonUrl;
	private List<Phone> ebayPhones;
	private List<Phone> amazonPhones;
	private List<Phone> bothPhones;
	

	@BeforeClass
	@Parameters({"browser","ebayUrl","amazonUrl","keySearch"})
	public void beforeClass(String browserName, String ebayUrl, String amazonUrl, String keySearch) {
		driverManager = DriverManagerFactory.getBrowser(browserName);
		driver = driverManager.getDriver(ebayUrl);
		ebaySearchPage = new pageObject.ebay.SearchPageObject(driver);
		this.keySearch = keySearch;
		this.amazonUrl = amazonUrl;
	}

	@Test
	public void TC_01_eBay_Search() {
		ebaySearchPage.fillKeySearch(keySearch);
		ebaySearchPage.submitSearch();
		ebaySearchPage.changeDollarCurrency();
		ebayPhones = ebaySearchPage.getAllItemInPage(log);
		verifyTrue(ebaySearchPage.isNameIncludeKeySearch(keySearch, ebayPhones));
	}
	
	@Test
	public void TC_02_Amazon_Search() {
		ebaySearchPage.openOtherPageWithUrl(amazonUrl);
		amazonSearchPage = new SearchPageObject(driver);
		amazonSearchPage.fillKeySearch(keySearch);
		amazonSearchPage.submitSearch();
		amazonPhones = amazonSearchPage.getAllItemInPage(log);
		verifyTrue(amazonSearchPage.isNameIncludeKeySearch(keySearch, amazonPhones));
		List<Phone> noPricePhone1 = new ArrayList<Phone>();
		List<Phone> noPricePhone2 = new ArrayList<Phone>();
		
		for (Phone phone : amazonPhones) {
			if(phone.getWholePrice() == null || phone.getOriginPrice() == null) {
				noPricePhone1.add(phone);
			}
		}

		amazonPhones.removeAll(noPricePhone1);
		for (Phone phone : ebayPhones) {
			if(phone.getWholePrice() == null || phone.getOriginPrice() == null) {
				noPricePhone2.add(phone);
			}
		}

		ebayPhones.removeAll(noPricePhone2);
		noPricePhone1.addAll(noPricePhone2);
		
		bothPhones = amazonPhones;
		bothPhones.addAll(ebayPhones);
		bothPhones = amazonSearchPage.convertPriceSortedAscending(bothPhones);
		log.info("=================================List phone is sorted==========================================");
		for (Phone phone : bothPhones) {
			System.out.println(phone.toString());
			log.info(phone.toString());
		}
		
		log.info("=================================List phone is no price ==========================================");
		for (Phone phone : noPricePhone1) {
			System.out.println(phone.toString());
			log.info(phone.toString());
		}
	} 

	@AfterClass(alwaysRun=true)
	public void afterClass() {
		closeBrowserAndDriver(driver);
	}
}
