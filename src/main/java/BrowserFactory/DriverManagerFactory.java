package BrowserFactory;

/**
 * Abstract class DriverManager for Browser Factory Patter
 * @author LAN
 *
 */
public abstract class DriverManagerFactory {

	public static DriverManager getBrowser(String browserName) {

		DriverManager driverManager = null;
		switch (browserName) {
		case "chrome":
			driverManager = new ChromeDriverManager();
			break;

		case "firefox":
			driverManager = new FireFoxDriverManager();
			break;

		case "chromeHeadless":
			driverManager = new ChromeHeadlessDriverManager();
			break;

		case "firefoxHeadless":
			driverManager = new FireFoxHeadlessDriverManager();
			break;

		case "edge":
			driverManager = new EdgeDriverManager();
			break;

		case "coccoc":
			driverManager = new CocCocDriverManager();
			break;
		case "ie":
			driverManager = new InternetExplorerDriverManager();
			break;
			
		default: System.out.println("Please choose a browser to run");
			break;
		}
		return driverManager;
	}
	
}
