/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.qait.automation;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

  private static String browser;
  private static final DesiredCapabilities capabilities = new DesiredCapabilities();

  public WebDriver getDriver(Map<String, String> seleniumconfig) {
    browser = seleniumconfig.get("browser");

    if (seleniumconfig.get("seleniumserver").equalsIgnoreCase("local")) {
      if (browser.equalsIgnoreCase("firefox")) {
        return getFirefoxDriver(seleniumconfig.get("driverpathfirefox")); 
      } else if (browser.equalsIgnoreCase("chrome")) {
        return getChromeDriver(seleniumconfig.get("driverpathChrome"));
      } else if (browser.equalsIgnoreCase("chromeProxy")) {
        return getChromeDriverWithProxy(seleniumconfig.get("driverpathChrome"));
      } else if (browser.equalsIgnoreCase("Safari")) {
        return getSafariDriver();
      } else if ((browser.equalsIgnoreCase("ie")) || (browser.equalsIgnoreCase("internetexplorer"))
          || (browser.equalsIgnoreCase("internet explorer"))) {
        return getInternetExplorerDriver(seleniumconfig.get("driverpathIE"));
      }
    }
    if (seleniumconfig.get("seleniumserver").equalsIgnoreCase("remote")) {
      return setRemoteDriver(seleniumconfig);
    }
    return new FirefoxDriver();
  }

  private WebDriver getChromeDriverWithProxy(String driverpath) {
    java.net.InetAddress localMachine;
    String curdir, downloadFilePath, currentPath = null, machineName = null, userName = null;
    try {
      localMachine = java.net.InetAddress.getLocalHost();
      machineName = localMachine.getHostName();
      userName = System.getProperty("user.name");
      currentPath = System.getProperty("user.dir");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    curdir = "\\\\" + machineName + "\\" + userName + currentPath.split(userName)[1];
    downloadFilePath = curdir + "\\src\\test\\resources\\testdata";
    // System.out.println("download path is:" + downloadFilePath);
    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();

    System.setProperty("webdriver.chrome.driver", driverpath);

    ChromeOptions options = new ChromeOptions();
    DesiredCapabilities cap = DesiredCapabilities.chrome();
    options.addArguments("--always-authorize-plugins=true");
    Proxy proxy = new Proxy();
    proxy.setHttpProxy("wit320.acs.org:38888");
    cap.setCapability("proxy", proxy);
    cap.setCapability(ChromeOptions.CAPABILITY, options);

    chromePrefs.put("profile.default_content_settings.popups", 0);
    chromePrefs.put("download.default_directory", downloadFilePath);
    options.setExperimentalOption("prefs", chromePrefs);

    return new ChromeDriver(cap);
  }

  private WebDriver setRemoteDriver(Map<String, String> selConfig) {
    DesiredCapabilities cap = null;
    browser = selConfig.get("browser");
    if (browser.equalsIgnoreCase("firefox")) {
      cap = DesiredCapabilities.firefox();
    } else if (browser.equalsIgnoreCase("chrome")) {
      cap = DesiredCapabilities.chrome();
    } else if (browser.equalsIgnoreCase("chromeProxy")) {
      cap = DesiredCapabilities.chrome();
      Proxy proxy = new Proxy();
      proxy.setHttpProxy("wit320.acs.org:38888");
      cap.setCapability("proxy", proxy);
    } else if (browser.equalsIgnoreCase("Safari")) {
      cap = DesiredCapabilities.safari();
    } else if ((browser.equalsIgnoreCase("ie")) || (browser.equalsIgnoreCase("internetexplorer")) || (browser.equalsIgnoreCase("internet explorer"))) {
      cap = DesiredCapabilities.internetExplorer();
    }
    String seleniuhubaddress = selConfig.get("seleniumserverhost");
    URL selserverhost = null;
    try {
      selserverhost = new URL(seleniuhubaddress);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    cap.setJavascriptEnabled(true);
    return new RemoteWebDriver(selserverhost, cap);
  }

  private static WebDriver getChromeDriver(String driverpath) {
    String curdir, downloadFilePath;
    curdir = System.getProperty("user.dir");
    downloadFilePath = curdir + "/src/test/resources/testdata";    
    System.out.println("download path is:" + downloadFilePath);
    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
    System.out.println(driverpath+"-----------------------");
    System.setProperty("webdriver.chrome.driver", driverpath);
    ChromeOptions options = new ChromeOptions();
    DesiredCapabilities cap = DesiredCapabilities.chrome();
    options.addArguments("--always-authorize-plugins=true");
    cap.setCapability(ChromeOptions.CAPABILITY, options);
    chromePrefs.put("profile.default_content_settings.popups", 0);
    chromePrefs.put("download.default_directory", downloadFilePath);
    options.setExperimentalOption("prefs", chromePrefs);
    return new ChromeDriver(cap);

  }

  private static WebDriver getInternetExplorerDriver(String driverpath) {
    System.setProperty("webdriver.ie.driver", driverpath);
    capabilities.setCapability("ignoreZoomSetting", true);
    capabilities.setCapability("ignoreZoomLevel", true);
    capabilities.setJavascriptEnabled(true);
    return new InternetExplorerDriver(capabilities);
  }

  private static WebDriver getSafariDriver() {
    return new SafariDriver();
  }

  private static WebDriver getFirefoxDriver(String driverpath) {
	    FirefoxOptions profile =new FirefoxOptions();
//	    //FirefoxProfile profile = new FirefoxProfile();
//	    profile.setPreference("browser.cache.disk.enable", false);
//	    profile.setPreference("javascript.enabled", true);
	      System.setProperty("webdriver.gecko.driver", driverpath);
	      profile.setCapability("marionette", false);
//	      profile.addPreference("browser.cache.disk.enable", false);
//	      profile.addPreference("javascript.enabled", true);
	      //System.out.println("just befor profile");
	    return new FirefoxDriver(profile);
  }
}
