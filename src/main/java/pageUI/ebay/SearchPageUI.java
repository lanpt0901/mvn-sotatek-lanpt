package pageUI.ebay;

public class SearchPageUI {
	public static final String SEARCH_TEXTBOX = "//input[@name='_nkw']";
	public static final String MENU_ICON = "//span[@class='expand-btn__cell']/div[@class='btn__icon']";
	public static final String CUSTOMIZE_ICON = "//span[text()='Customize ']";
	public static final String CURRENCY_CHECKBOX= "//input[@name='_fcpd']";
	public static final String APPLY_BUTTON = "//input[@value='Apply changes']";
	public static final String SEARCH_SUBMIT_BUTTON = "//input[@id='gh-btn']";
	public static final String NAME_ITEM = "//h3[contains(@class,'s-item__title')]";

	//h3[text()='Apple iPhone 11 - 128GB - Black (Unlocked)']/parent::a/following-sibling::div[contains(@class,'s-item__details')]//span[@class='s-item__price']
	public static final String LINK_ITEM_SPECIAL = "//h3[contains(text(),'%s') and contains(text(),'%s')]/parent::a";
	public static final String LINK_ITEM = "//h3[text()='%s']/parent::a";
	public static final String PRICE_WHOLE_ITEM = "//h3[text()='%s']/parent::a/following-sibling::div[contains(@class,'s-item__details')]//span[@class='s-item__price']";
	public static final String PRICE_WHOLE_ITEM_SPECIAL = "//h3[contains(text(),'%s') and contains(text(),'%s')]/parent::a/following-sibling::div[contains(@class,'s-item__details')]//span[@class='s-item__price']";

}
