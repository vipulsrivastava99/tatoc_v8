package com.qait.automation;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.setYamlFilePath;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import com.qait.acs.keywords.DragAndDropV8;
import com.qait.acs.keywords.GridGateV8;
import com.qait.acs.keywords.SSO_LoginPageAction;
import com.qait.acs.keywords.TatocWelcomePage;
import com.qait.acs.keywords.TestPageActions;
import com.qait.acs.keywords.frameDungeonV8;
import com.qait.automation.utils.TakeScreenshot;

public class TestSessionInitiator {

  public WebDriver driver;
  private final WebDriverFactory wdfactory;
  String browser;
  String seleniumserver;
  String seleniumserverhost;
  String appbaseurl;
  String applicationpath;
  String chromedriverpath;
  String firefoxdriverpath;
  String datafileloc = "";
  static int timeout;
  Map<String, Object> chromeOptions = null;
  DesiredCapabilities capabilities;

  /**
   * Initiating the page objects _initPage
   */
  public TakeScreenshot takescreenshot;
  //public TestPageActions testPage;
public TatocWelcomePage WelcomePageObject; 
public GridGateV8 gridGateObject; 
public frameDungeonV8 frameDungeonObject;
public DragAndDropV8 dragAndDropObject;
public SSO_LoginPageAction sso_LoginPageObject;
  public WebDriver getDriver() {
    return this.driver;
  }

  private void _initPage() {
	  WelcomePageObject = new TatocWelcomePage(driver);
	  gridGateObject=new GridGateV8(driver);
   frameDungeonObject =new frameDungeonV8(driver);
   dragAndDropObject=new DragAndDropV8(driver);
   sso_LoginPageObject=new SSO_LoginPageAction(driver);
  } 
  /**
   * Page object Initiation done
   * 
   */
  public TestSessionInitiator(String testname) {
    wdfactory = new WebDriverFactory();
    testInitiator(testname, false);
  }

  public TestSessionInitiator(String testname, boolean proxyFlag) {
    wdfactory = new WebDriverFactory();
    testInitiator(testname, true);
  }

  private void testInitiator(String testname, boolean proxyFlag) {
    setYamlFilePath();
    _configureBrowser(proxyFlag);
    _initPage();
    takescreenshot = new TakeScreenshot(testname, this.driver);
  }

  private void _configureBrowser(boolean proxyFlag) {
    Map<String, String> sessionConfig = new HashMap<String, String>();
    sessionConfig = _getSessionConfig();
    if (proxyFlag)
      sessionConfig.put("browser", "chromeProxy");
    driver = wdfactory.getDriver(sessionConfig);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Integer.parseInt(getProperty("timeout")), TimeUnit.SECONDS);
    driver.manage().timeouts().setScriptTimeout(Integer.parseInt(getProperty("timeout")), TimeUnit.SECONDS);

  }

  public Map<String, String> _getSessionConfig() {
    String[] configKeys = {"tier", "browser", "seleniumserver", "seleniumserverhost", "timeout", "driverpathIE", "driverpathChrome","driverpathfirefox"};
    Map<String, String> config = new HashMap<String, String>();
    for (String string : configKeys) {
      try {
        if (System.getProperty(string).isEmpty())
          config.put(string, getProperty("./Config.properties", string));
        else
          config.put(string, System.getProperty(string));
      } catch (NullPointerException e) {
        config.put(string, getProperty("./Config.properties", string));

      }
    }
    // for (Map.Entry<String, String> entry : config.entrySet()) {
    // System.out.println("Key = " + entry.getKey() + ", Value = " +
    // entry.getValue());
    // }
    return config;

  }

  public void launchApplication() {
    launchApplication(getYamlValue("baseUrl"));
  }

  public void launchApplication(String baseurl) {
    Reporter.log("The application url is :- " + baseurl, true);
    Reporter.log("The test browser is :- " + _getSessionConfig().get("browser") + "\n", true);
    driver.manage().deleteAllCookies();
    driver.get(baseurl);
   try {
	Thread.sleep(3000);
} catch (InterruptedException e) {
	e.printStackTrace();
}
  }

  public void stepStartMessage(String testStepName) {
    Reporter.log(" ", true);
    Reporter.log("***** STARTING TEST STEP:- " + testStepName.toUpperCase() + " *****", true);
    Reporter.log(" ", true);
  }

  public void openUrl(String url) {
    driver.get(url);
    Reporter.log("Launched url is :- " + url, true);
  }

  public void navigateBack() {
    driver.navigate().back();
  }

  public void closeBrowserSession() {
    Reporter.log("Browser selected from 'Config.properties' file: " + _getSessionConfig().get("browser"));

    /*
     * if(_getSessionConfig().get("browser").equalsIgnoreCase("firefox")){
     * Runtime.getRuntime().exec("taskkill /F /IM WerFault.exe"); driver.close(); }
     */
    driver.close();
    driver.quit();
  }

  public void pageRefresh() {
    driver.navigate().refresh();
  }

}
