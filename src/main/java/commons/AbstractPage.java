package commons;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import commons.GlobalConstants;
import data.Phone;

public abstract class AbstractPage {

	//Web browser
	public void openUrl(WebDriver driver, String url) {
		driver.get(url);
	}
	
	public String getTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}
	
	public void back(WebDriver driver) {
		driver.navigate().back();
	}

	public void forward(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refresh(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void sendKeyToAlert(WebDriver driver, String text) {
		driver.switchTo().alert().sendKeys(text);
	}

	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}
	
	public void cancelAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

	public String getTextAlert(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}

	public void waitAlertPresence(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.alertIsPresent());
	}
	
	//Window
	public void switchWindownByID(WebDriver driver, String parentID) {
		Set<String> allWindow = driver.getWindowHandles();
		for (String runWindow : allWindow) {
			if(!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}
	
	public void switchWindownByTitle(WebDriver driver, String title) {
		Set<String> allWindows = driver.getWindowHandles(); //get all id of window
		for (String runWindow : allWindows) {
			if(driver.getTitle().equals(title)) {
				driver.switchTo().window(runWindow);
			}
		}
	}

	public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if(!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}


	public Boolean areAllWindowsWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if(!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
		if(driver.getWindowHandles().size() == 1) {
			return true;
		}
		return false;
	}
	
	public By byXpath(String locator) {
		return By.xpath(locator);
	}
	//Web element
	public WebElement findElementByXpath(WebDriver driver, String locator) {
		return driver.findElement(byXpath(locator));
	}
	
	public WebElement findElementByXpath(WebDriver driver, String locator, String... values) {
		return findElementByXpath(driver, castToObject(locator, values));
	}
	
	public List<WebElement> findElementsByXpath(WebDriver driver, String locator) {
		return driver.findElements(byXpath(locator));
	}
	public List<WebElement> findElementsByXpath(WebDriver driver, String locator, String... values) {
		String locatorStr = castToObject(locator, values);
		System.out.println("locator===" + locatorStr);
		return driver.findElements(byXpath(locatorStr));
	}
	
	public void clickToElement(WebDriver driver, String locator) {
		findElementByXpath(driver, locator).click();
	}
	
	public String castToObject(String locator, String... values) {
		return locator = String.format(locator, (Object[]) values);
	}
	
	public void clickToElement(WebDriver driver, String locator, String... values) {
		findElementByXpath(driver, castToObject(locator, values)).click();
	}

	public void sendKeyToElement(WebDriver driver, String locator, String text) {
		element = findElementByXpath(driver, locator);
		element.clear();
		element.sendKeys(text);;
	}
	
	public String getTextOfElement(WebDriver driver, String locator) {
		return findElementByXpath(driver, locator).getText();
	}
	
	public void selectItemInDropdown(WebDriver driver, String locator, String selectedItem) {
		select = new Select(findElementByXpath(driver, locator));
		select.selectByVisibleText(selectedItem);
	}
	
	public String getSelectedItemInDropdown(WebDriver driver, String locator) {
		select = new Select(findElementByXpath(driver, locator));
		return select.getFirstSelectedOption().getText();
	}
	
	public Boolean isDropdownMutiple(WebDriver driver, String locator) {
		select = new Select(findElementByXpath(driver, locator));
		return select.isMultiple();
	}
	
	/**
	 * 
	 * 1. Click to parent to expand all item</br>
	 * 2. Wait to all child items is displayed</br>
	 * 3. Add all child items in List</br>
	 * 4. Find the item = expected item to click (select expected item)</br>
	 * 5. Scroll to expected item to view (by javascript) because displayed item can be click</br>
	 * 6. Click to expected item</br> 
	 */
	public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator, String expectedItem) {
		// 1 - Click vào thẻ (cha) để xổ ra tất cả các item con
		findElementByXpath(driver, parentLocator).click();
		sleepInSecond(1);
		// 2 - Chờ cho tất cả các item con được load ra
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byXpath(childItemLocator)));
		// Đưa tất cả các item trong dropdown vào 1 list để kiểm tra
		List<WebElement> allItems = findElementsByXpath(driver, childItemLocator);
		// 3 - Chạy qua tất cả các giá trị đang có trong list
		for (WebElement item : allItems) {
			// 4 - Kiểm tra xem text của các giá trị có item nào bằng vs text mong muốn ko
			if (item.getText().equals(expectedItem)) {
				// 5 - Scroll xuống đến đúng item này
				jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);
				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}
  
	public String getAttributeValue(WebDriver driver, String locator, String attributeName) {
		return findElementByXpath(driver, locator).getAttribute(attributeName);
	}
	
	public int countElementNumber(WebDriver driver, String locator) {
		return findElementsByXpath(driver, locator).size();
	}
	
	public void checkTheCheckBox(WebDriver driver, String locator) {
		element = findElementByXpath(driver, locator);
		if(!element.isSelected()) {
			element.click();
		}
	}
	
	public void uncheckTheCheckBox(WebDriver driver, String locator) {
		element = findElementByXpath(driver, locator);
		if(element.isSelected()) {
			element.click();
		}
	}
	
	public Boolean isElementDisplayed(WebDriver driver, String locator) {
		return findElementByXpath(driver, locator).isDisplayed();
	}
	
	public Boolean isElementDisplayed(WebDriver driver, String locator, String... values) {
		return findElementByXpath(driver, castToObject(locator, values)).isDisplayed();
	}
	
	public Boolean isElementSelected(WebDriver driver, String locator) {
		return findElementByXpath(driver, locator).isSelected();
	}
	
	public Boolean isElementEnabled(WebDriver driver, String locator) {
		return findElementByXpath(driver, locator).isEnabled();
	}
	
	/**
	 * Switch from default content to frame by WebElement (element is frame)
	 * for example: (https://kyna.vn/ has iframe FB fanpage: //div[@class='fanpage']//iframe )
	 * @param driver
	 * @param locator
	 */
	public void switchToFrame(WebDriver driver, String locator) {
		driver.switchTo().frame(findElementByXpath(driver, locator));
	}
	
	/**
	 * Switch from iFrame/frame to default Content page (parent page)
	 * @param driver
	 * @param locator
	 */
	public void switchToDefaultContent(WebDriver driver, String locator) {
		driver.switchTo().defaultContent(); 
	}
	
	//user interaction
	public void doubleClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.doubleClick(findElementByXpath(driver, locator)).perform();
	}

	public void moveToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.moveToElement(findElementByXpath(driver, locator)).perform();
	}

	public void rightClickToElement(WebDriver driver, String locator) {
		action = new Actions(driver);
		action.contextClick(findElementByXpath(driver, locator)).perform();
	}

	public void sendKeyboardToElement(WebDriver driver, String locator, Keys key) {
		action = new Actions(driver);
		action.sendKeys(findElementByXpath(driver, locator), key).perform();
	}
	
	//javascript executor
	public void dragAndDrop(WebDriver driver, String targetLocator, String dragLocator) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true)",findElementByXpath(driver, targetLocator));
		sleepInSecond(2);
		action.dragAndDrop(findElementByXpath(driver, dragLocator), driver.findElement(By.id("droptarget"))).perform();
		sleepInSecond(1);
	}
	
	public Object executeJavascriptToBrowser(WebDriver driver, String javascript) {
		jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javascript);
	}
	
	public boolean verifyTextInInnerText(WebDriver driver, String textExpected) {
		jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		System.out.println("Text actual = " + textActual);
		return textActual.equals(textExpected);
	}
	
	public void scrollToBottomPage(WebDriver driver) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}
	
	public void navigateToUrlByJS(WebDriver driver, String url) {
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}
	
	public void highlightElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		element = findElementByXpath(driver, locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 5px solid red; border-style: dashed;");
		sleepInSecond(2);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		element = findElementByXpath(driver, locator);
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public void scrollToElement(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		element = driver.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
		jsExecutor = (JavascriptExecutor) driver;
		element = findElementByXpath(driver, locator);
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
		jsExecutor = (JavascriptExecutor) driver;
		element = findElementByXpath(driver, locator);
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
	}
	
	public boolean isImageLoaded(WebDriver driver, String locator) {
		jsExecutor = (JavascriptExecutor) driver;
		boolean status = (Boolean) jsExecutor.executeScript("return arguments[0].complete "
								+ "&& typeof arguments[0].naturalWidth != 'undefined' "
								+ "&& arguments[0].naturalWidth > 0", findElementByXpath(driver, locator));
		return status;
	}
	
	//Wait
	public void waitForElementPresence(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(byXpath(locator)));
	}

	public void waitForElementVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byXpath(locator)));
	}

	public void waitForElementsVisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byXpath(locator)));
	}
	
	public void waitForElementVisible(WebDriver driver, String locator, String...values) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byXpath(castToObject(locator, values))));
	}
	
	public void waitForElementClickable(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(byXpath(locator)));
	}

	public void waitForElementInvisible(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(byXpath(locator)));
	}

	public void waitForAlertPresence(WebDriver driver, String locator) {
		explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void sleepInSecond(long timeout) {
		try {
			Thread.sleep(timeout * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void overrideGlobalTimeout(WebDriver driver, long second) {
		driver.manage().timeouts().implicitlyWait(second, TimeUnit.SECONDS);
	}
	
	//TODO: check ham nay xem sao lai chay fail
	public boolean isElementUnDisplayed(WebDriver driver, String locator, String... values) {
		boolean result = false;
		overrideGlobalTimeout(driver, GlobalConstants.SHORT_TIMEOUT);
		try {
			List<WebElement> elements = findElementsByXpath(driver,castToObject(locator, values));
			if(elements.size() == 0) {
				//element is not in dom
				overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
				return true;
			} else if(elements.size()>0 && !elements.get(0).isDisplayed()) {
				//element is in dom and invisible
				overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
				return true;
			} else {
				//element is visible
				overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
				return false;
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			result = true;
		}
		overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
		return result;
	}
	
	public boolean isElementUnDisplayed(WebDriver driver, String locator) {
		boolean result = false;
		overrideGlobalTimeout(driver, GlobalConstants.SHORT_TIMEOUT);
		try {
			List<WebElement> elements = findElementsByXpath(driver, locator);
			if(elements.size() == 0) {
				//element is not in dom
				overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
				System.out.println("Element is not in dom===" + getDateNow());
				return true;
			} else if(elements.size()>0 && !elements.get(0).isDisplayed()) {
				//element is in dom and invisible
				System.out.println("Element is in dom and invisible===" + getDateNow());
				overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
				return true;
			} else {
				//element is visible
				overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
				System.out.println("Element is visible===" + getDateNow());
				return false;
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			result = true;
		}
		overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
		return result;
	}
	
	public String getDateNow() {
		return new Date().toString();
	}

	public boolean isTextSortedAscending(WebDriver driver, String locator) {
		ArrayList<String> dataArrayList = new ArrayList<String>();
		//Lay tat ca cac element can kiem tra sort (name/price/created date...)
		List<WebElement> elementList = findElementsByXpath(driver, locator);

		System.out.println("==================DỮ LIỆU TRÊN GIAO DIỆN ==========");
		//Lay text cua tung element add vao ArrayList
		for (WebElement webElement : elementList) {
			dataArrayList.add(webElement.getText());
			System.out.println(webElement.getText());
		}

		//clone arrayList to sort
		ArrayList<String> sortedList = (ArrayList<String>) dataArrayList.clone();
		Collections.sort(sortedList);
		//printf list
		System.out.println("==================DỮ LIỆU ĐÃ SORTED ASC ==========");
		for (String value : sortedList) {
			System.out.println(value);
		}
		return dataArrayList.equals(sortedList);
	}
	
	public boolean isTextSortedDescending(WebDriver driver, String locator) {
		ArrayList<String> dataArrayList = new ArrayList<String>();
		//Lay tat ca cac element can kiem tra sort (name/price/created date...)
		List<WebElement> elementList = findElementsByXpath(driver, locator);

		System.out.println("==================DỮ LIỆU TRÊN GIAO DIỆN ==========");
		//Lay text cua tung element add vao ArrayList
		for (WebElement webElement : elementList) {
			dataArrayList.add(webElement.getText());
			System.out.println(webElement.getText());
		}

		//clone arrayList to sort
		ArrayList<String> sortedList = (ArrayList<String>) dataArrayList.clone();
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		
		//printf list
		System.out.println("==================DỮ LIỆU ĐÃ SORTED DESC==========");
		for (String value : sortedList) {
			System.out.println(value);
		}
		return dataArrayList.equals(sortedList);
	}
	
	public boolean isPriceSortedAscending(WebDriver driver, String locator) {
		ArrayList<Float> dataArrayList = new ArrayList<Float>();
		//Lay tat ca cac element can kiem tra sort (name/price/created date...)
		List<WebElement> elementList = findElementsByXpath(driver, locator);

		System.out.println("==================PRICE DỮ LIỆU TRÊN GIAO DIỆN ==========");
		//Lay text cua tung element add vao ArrayList
		for (WebElement webElement : elementList) {
			System.out.println(webElement.getText());
		}
		System.out.println("==================PRICE DA XU LY ==========");
		for (WebElement webElement : elementList) {
			Float item = Float.valueOf(webElement.getText().replace("$", "").replace(",","").trim());
			System.out.println(item);
			dataArrayList.add(item);
		}
		

		//clone arrayList to sort
		ArrayList<Float> sortedList = new ArrayList<Float>();
		for (Float item : dataArrayList) {
			sortedList.add(item);
		}
		Collections.sort(sortedList);
		//printf list
		System.out.println("==================PRICE DỮ LIỆU ĐÃ SORTED ASC ==========");
		for (Float value : sortedList) {
			System.out.println(value);
		}
		return dataArrayList.equals(sortedList);
	}
	
	public boolean isPriceSortedDescending(WebDriver driver, String locator) {
		ArrayList<Float> dataArrayList = new ArrayList<Float>();
		//Lay tat ca cac element can kiem tra sort (name/price/created date...)
		List<WebElement> elementList = findElementsByXpath(driver, locator);

		System.out.println("==================PRICE DỮ LIỆU TRÊN GIAO DIỆN=========");
		//Lay text cua tung element add vao ArrayList
		for (WebElement webElement : elementList) {
			dataArrayList.add(Float.parseFloat(webElement.getText().replace("$", "").replace(",","").trim()));
			System.out.println(webElement.getText().replace("$", "").replace("$", "").trim());
		}

		//clone arrayList to sort
		ArrayList<Float> sortedList = new ArrayList<Float>();
		for (Float item : dataArrayList) {
			sortedList.add(item);
		}
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		//printf list
		System.out.println("==================PRICE DỮ LIỆU ĐÃ SORTED ASC==========");
		for (Float value : sortedList) {
			System.out.println(value);
		}
		return dataArrayList.equals(sortedList);
	}
	
	public boolean isCreatedDateSortedAscending(WebDriver driver, String locator) {
		ArrayList<Date> dataArrayList = new ArrayList<Date>();
		//Lay tat ca cac element can kiem tra sort (name/price/created date...)
		List<WebElement> elementList = findElementsByXpath(driver, locator);

		System.out.println("==================DỮ LIỆU TRÊN GIAO DIỆN ==========");
		//Lay text cua tung element add vao ArrayList
		for (WebElement webElement : elementList) {
			dataArrayList.add(covertStringToDate(webElement.getText()));
			System.out.println(webElement.getText());
		}

		//clone arrayList to sort
		ArrayList<Date> sortedList = (ArrayList<Date>) dataArrayList.clone();
		Collections.sort(sortedList);
		//printf list
		System.out.println("==================DỮ LIỆU ĐÃ SORTED ASC ==========");
		for (Date value : sortedList) {
			System.out.println(value);
		}
		return dataArrayList.equals(sortedList);
	}
	
	public boolean isCreatedDateSortedDescending(WebDriver driver, String locator) {
		ArrayList<Date> dataArrayList = new ArrayList<Date>();
		//Lay tat ca cac element can kiem tra sort (name/price/created date...)
		List<WebElement> elementList = findElementsByXpath(driver, locator);

		System.out.println("==================DỮ LIỆU TRÊN GIAO DIỆN==========");
		//Lay text cua tung element add vao ArrayList
		for (WebElement webElement : elementList) {
			dataArrayList.add(covertStringToDate(webElement.getText()));
			System.out.println(webElement.getText());
		}

		//clone arrayList to sort
		ArrayList<Date> sortedList = (ArrayList<Date>) dataArrayList.clone();
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		//printf list
		System.out.println("==================DỮ LIỆU ĐÃ SORTED ASC ==========");
		for (Date value : sortedList) {
			System.out.println(value);
		}
		return dataArrayList.equals(sortedList);
	}
	
	public List<Float> getPriceSortedAscending(WebDriver driver, String locator) {
		ArrayList<Float> dataArrayList = new ArrayList<Float>();
		//Lay tat ca cac element can kiem tra sort (name/price/created date...)
		List<WebElement> elementList = findElementsByXpath(driver, locator);

		System.out.println("==================PRICE DỮ LIỆU TRÊN GIAO DIỆN ==========");
		//Lay text cua tung element add vao ArrayList
		for (WebElement webElement : elementList) {
			System.out.println(webElement.getText());
		}
		System.out.println("==================PRICE DA XU LY ==========");
		for (WebElement webElement : elementList) {
			Float item = Float.valueOf(webElement.getText().replace("$", "").replace(",","").trim());
			System.out.println(item);
			dataArrayList.add(item);
		}
		
		//clone arrayList to sort
		ArrayList<Float> sortedList = new ArrayList<Float>();
		for (Float item : dataArrayList) {
			sortedList.add(item);
		}
		Collections.sort(sortedList);
		//printf list
		System.out.println("==================PRICE DỮ LIỆU ĐÃ SORTED ASC ==========");
		for (Float value : sortedList) {
			System.out.println(value);
		}
		return sortedList;
	}
	
	public boolean isNameIncludeKeySearch(String keySearch) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Date covertStringToDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void convertPriceSortedAscending(List<Phone> phoneList){
		Collections.sort(phoneList, new SortPhone());
		for(Phone emp : phoneList){
			System.out.println(emp);
		}
    }
	
	int longTimeout = 30;
	WebDriverWait explicitWait;
	WebElement element;
	Select select;
	JavascriptExecutor jsExecutor;
	Actions action;
}
