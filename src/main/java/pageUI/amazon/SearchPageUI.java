package pageUI.amazon;

public class SearchPageUI {

	public static final String SEARCH_TEXTBOX = "//input[@id='twotabsearchtextbox']";
	public static final String SEARCH_SUBMIT_BUTTON = "//input[@class='nav-input' and @type='submit']";
	public static final String NAME_ITEM = "//span[contains(@class,'a-size-medium a-color-base a-text-normal')]";

	//h2[contains(text(),'s-line-clamp-2')]/ancestor::div[@class='a-section a-spacing-medium']
	//span[contains(text(),'OnePlus 8 Pro')]/parent::a
	//span[contains(text(),'OnePlus 8 Pro')]/ancestor::div[@class='a-section a-spacing-medium']//span[@class='a-price-fraction']
	public static final String LINK_ITEM = "//span[contains(text(),'%s')]/parent::a";
	public static final String PRICE_ITEM= "//span[contains(text(),'%s')]/ancestor::div[@class='a-section a-spacing-medium']//span[@class='a-price']";
	public static final String PRICE_WHOLE_ITEM = "//span[contains(text(),'%s')]/ancestor::div[@class='a-section a-spacing-medium']//span[@class='a-price-whole']";
	public static final String PRICE_FRACTION_ITEM = "//span[contains(text(),'%s')]/ancestor::div[@class='a-section a-spacing-medium']//span[@class='a-price-fraction']";

	public static final String LINK_ITEM_SPECIAL = "//span[contains(text(),'%s') and contains(text(),'%s'))]/parent::a";
	public static final String PRICE_ITEM_SPECIAL= "//span[contains(text(),'%s') and contains(text(),'%s')]/ancestor::div[@class='a-section a-spacing-medium']//span[@class='a-price']";
	public static final String PRICE_WHOLE_ITEM_SPECIAL = "//span[contains(text(),'%s') and contains(text(),'%s')]/ancestor::div[@class='a-section a-spacing-medium']//span[@class='a-price-whole']";
	public static final String PRICE_FRACTION_ITEM_SPECIAL = "//span[contains(text(),'%s') and contains(text(),'%s')]/ancestor::div[@class='a-section a-spacing-medium']//span[@class='a-price-fraction']";
}
