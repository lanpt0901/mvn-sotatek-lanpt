package pageUI.ebay;

public class SearchPageUI {
	public static final String SEARCH_TEXTBOX = "//input[@name='_nkw']";
	public static final String SEARCH_SUBMIT_BUTTON = "//input[@id='gh-btn']";
	public static final String NAME_ITEM = "//h3[contains(@class,'s-item__title')]";

	//h3[text()='Apple iPhone 11 - 128GB - Black (Unlocked)']/parent::a/following-sibling::div[contains(@class,'s-item__details')]//span[@class='s-item__price']
	public static final String LINK_ITEM = "//h3[text()='%s']/parent::a";
	public static final String PRICE_WHOLE_ITEM = "//h3[text()='%s']/parent::a/following-sibling::div[contains(@class,'s-item__details')]//span[@class='s-item__price']";

}
