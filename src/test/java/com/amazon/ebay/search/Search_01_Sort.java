package com.amazon.ebay.search;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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
//		ebaySearchPage.verifySearchItemDisplayed();
//		Assert.assertTrue(ebaySearchPage.isNameSortedAscending());
		ebayPhones = ebaySearchPage.getAllItemInPage();
//		Assert.assertTrue(ebaySearchPage.isNameIncludeKeySearch(keySearch));
		
	}
	
	@Test
	public void TC_02_Amazon_Search() {
		ebaySearchPage.openOtherPageWithUrl(amazonUrl);
		amazonSearchPage = new SearchPageObject(driver);
		amazonSearchPage.fillKeySearch(keySearch);
		amazonSearchPage.submitSearch();
		amazonPhones = amazonSearchPage.getAllItemInPage();
//		Assert.assertTrue(amazonSearchPage.isNameIncludeKeySearch(keySearch));
	} 

	@AfterClass(alwaysRun=true)
	public void afterClass() {
		closeBrowserAndDriver(driver);
	}
}
