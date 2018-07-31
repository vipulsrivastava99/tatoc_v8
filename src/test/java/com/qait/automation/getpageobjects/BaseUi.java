/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getPageTitleFromFile;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.qait.automation.utils.SeleniumWait;

/**
 *
 * @author prashantshukla
 * 
 */
public class BaseUi {

	WebDriver driver;
	protected SeleniumWait wait;
	private String pageName;

	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		this.wait = new SeleniumWait(driver, Integer.parseInt(getProperty("Config.properties", "timeout")));
	}

	protected String getPageTitle() {
		return driver.getTitle();
	}

	protected void logMessage(String message) {
		Reporter.log(message, true);
	}

	public String getCurrentURL() {
		wait.waitForPageToLoadCompletely();
		return driver.getCurrentUrl();
	}

	protected void verifyPageTitleExact() {
		String pageTitle = getPageTitleFromFile(pageName);
		System.out.println("pageTitle...." + pageTitle);
		verifyPageTitleExact(pageTitle);
	}

	protected void verifyPageTitleExact(String expectedPagetitle) {
		if (((expectedPagetitle == "") || (expectedPagetitle == null) || (expectedPagetitle.isEmpty()))
				&& (getProperty("browser").equalsIgnoreCase("chrome"))) {
			expectedPagetitle = getCurrentURL();
		}
		try {
			wait.waitForPageTitleToBeExact(expectedPagetitle);
			hardWait(5);
			logMessage("TEST PASSED: PageTitle for '" + pageName + "' is exactly: '" + expectedPagetitle + "'");
		} catch (TimeoutException ex) {
			logMessage("TEST FAILED: PageTitle for " + pageName + " is not exactly: '" + expectedPagetitle
					+ "'!!!\n instead it is :- " + driver.getTitle());
		}
	}

	/**
	 * Verification of the page title with the title text provided in the page
	 * object repository
	 * 
	 */
	protected void verifyPageTitleContains() {
		String expectedPagetitle = getPageTitleFromFile(pageName).trim();
		verifyPageTitleContains(expectedPagetitle);
	}

	/**
	 * this method will get page title of current window and match it partially
	 * with the param provided
	 *
	 * @param expectedPagetitle
	 *            - Partial page title text
	 * 
	 */
	protected void verifyPageTitleContains(String expectedPagetitle) {
		if (((expectedPagetitle == "") || (expectedPagetitle == null) || (expectedPagetitle.isEmpty()))
				&& (getProperty("browser").equalsIgnoreCase("chrome"))) {
			expectedPagetitle = getCurrentURL();
		}
		try {
			wait.waitForPageTitleToContain(expectedPagetitle);
			String actualPageTitle = getPageTitle().trim();
			logMessage("TEST PASSED: PageTitle for '" + actualPageTitle + "' contains: '" + expectedPagetitle + "'.");
		} catch (TimeoutException exp) {
			String actualPageTitle = driver.getTitle().trim();
			logMessage("TEST FAILED: As actual Page Title: '" + actualPageTitle
					+ "' does not contain expected Page Title : '" + expectedPagetitle + "'.");
		}
	}

	public void assertThatPageTitleContains(String expectedPageTitle) {
		wait.waitForPageToLoad();
		String pageTitle = getPageTitle();
		logMessage("actual Page title is:" + pageTitle);
		System.out.println("expectedPageTitle..." + expectedPageTitle);
		// try {

		Assert.assertTrue(
				pageTitle.toLowerCase().replaceAll("\\s", "").trim()
						.contains(expectedPageTitle.toLowerCase().replaceAll("\\s", "").trim()),
				"TEST FAILED: As actual Page Title: '" + pageTitle + "' does not contain expected Page Title : '"
						+ expectedPageTitle + "'.");
		logMessage("TEST PASSED: PageTitle for '" + pageTitle + "' contains: '" + expectedPageTitle + "'.");
		// } catch (AssertionError err) {
		// String actualPageTitle = driver.getTitle().trim();
		// logMessage("TEST FAILED: As actual Page Title: '" + actualPageTitle +
		// "' does not contain expected Page Title : '" + expectedPageTitle +
		// "'.");
		// }
	}

	public void assertThatPageTitleContains() {
		String expectedPageTitle = getPageTitleFromFile(pageName).trim();
		String pageTitle = getPageTitle();
		try {
			Assert.assertTrue(
					pageTitle.toLowerCase().replaceAll("\\s", "").trim()
							.contains(expectedPageTitle.toLowerCase().replaceAll("\\s", "").trim()),
					"TEST FAILED: As actual Page Title: '" + pageTitle + "' does not contain expected Page Title : '"
							+ expectedPageTitle + "'.");
			logMessage("TEST PASSED: PageTitle for '" + pageTitle + "' contains: '" + expectedPageTitle + "'.");
		} catch (AssertionError err) {
			String actualPageTitle = driver.getTitle().trim();
			logMessage("TEST FAILED: As actual Page Title: '" + actualPageTitle
					+ "' does not contain expected Page Title : '" + expectedPageTitle + "'.");
		}
	}

	protected WebElement getElementByIndex(List<WebElement> elementlist, int index) {
		return elementlist.get(index);
	}

	protected WebElement getElementByExactText(List<WebElement> elementlist, String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list No element
		// exception
		if (element == null) {
		}
		return element;
	}

	protected WebElement getElementByContainsText(List<WebElement> elementlist, String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().contains(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list
		if (element == null) {
		}
		return element;
	}

	protected void switchToFrame(WebElement element) {
		wait.waitForElementToBeVisible(element);
		driver.switchTo().frame(element);
	}

	public void switchToFrame(int i) {
		driver.switchTo().frame(i);
	}

	public void switchToFrame(String id) {
		driver.switchTo().frame(id);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	protected Object executeJavascript(String script) {
		return ((JavascriptExecutor) driver).executeScript(script);
	}

	protected Object executeJavascript(String script, Object object) {
		return (Object) ((JavascriptExecutor) driver).executeScript(script, object);
	}

	protected void handleAlert() {
		try {
			switchToAlert().accept();
			logMessage("Alert handled..");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			logMessage("No Alert window appeared...");
		}
	}

	protected void dismissAlert() {
		try {
			switchToAlert().dismiss();
			logMessage("Alert dismissed..");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			logMessage("No Alert window appeared...");
		}
	}

	protected String handleAlertAndReturnText() {
		String message = null;
		try {
			Alert alert = switchToAlert();
			message = alert.getText();
			alert.accept();
			logMessage("Alert handled..");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			logMessage("No Alert window appeared...");
		}
		return message;
	}

	public boolean isAlertPresent() {
		boolean flag = false;
		try {
			switchToAlert();
			flag = true;
		} catch (TimeoutException Ex) {
			flag = false;
		}
		return flag;
	}

	private Alert switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	protected String getAlertMessage() {
		String message = null;
		try {
			message = switchToAlert().getText();
			logMessage("Retrieved message from Alert saying:- " + message);
			driver.switchTo().defaultContent();
		} catch (Exception ex) {
			logMessage("No Alert appeared ");
		}
		return message;
	}

	protected void selectProvidedTextFromDropDown(WebElement el, String text) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		sel.selectByVisibleText(text);
	}

	protected void deSelectEverythingFromDropDown(WebElement el) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		sel.deselectAll();
	}

	protected void selectValueIndexFromDropDown(WebElement el, int index) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		sel.selectByIndex(index);
	}

	protected String getFirstSelectedOptionFromDropdown(WebElement el) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		return sel.getFirstSelectedOption().getText();
	}

	protected int getNoOfOptionsInDropdown(WebElement el) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		return sel.getOptions().size();
	}

	protected List<WebElement> getListOfOptionsInDropdown(WebElement el) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		return sel.getOptions();
	}

	protected void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	protected void scrollDown() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,10000)");
	}

	protected void scrollUp() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-10000)");
	}

	protected void scrollVertical(int scrollY) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + scrollY + ")");
	}

	protected void scrollhorizontal(int scrollX) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(" + scrollX + "," + "0)");

	}

	protected void hover(WebElement element) {
		Actions hoverOver = new Actions(driver);
		hoverOver.moveToElement(element).build().perform();
	}

	public void hoverAndClick(WebElement element) {
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element).click().build().perform();
	}

	public void hoverOnMainAndClickSubLink(WebElement mainElement, WebElement subElement) {
		Actions actions = new Actions(driver);
		Actions builder = actions.moveToElement(mainElement);
		Action b = builder.build();
		b.perform();
		actions.moveToElement(mainElement).build().perform();
		wait.hardWait(1);
		actions.moveToElement(subElement);
		actions.click().build().perform();
	}

	protected void click(WebElement element) {
		try {
			wait.waitForElementToBeVisible(element);
			scrollDown(element);
			element.click();
		} catch (StaleElementReferenceException ex1) {
			wait.waitForElementToBeVisible(element);
			scrollDown(element);
			element.click();
			logMessage("Clicked Element " + element + " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + element + " could not be clicked! " + ex2.getMessage());
		}
	}

	public void mouseHoverJScript(WebElement HoverElement) {
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} "
				+ "else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		((JavascriptExecutor) driver).executeScript(mouseOverScript, HoverElement);
	}

	public void navigateToPreviousPage() {
		driver.navigate().back();
		wait.waitForPageToLoad();
	}

	protected void changeWindow(int i) {
		hardWait(1);
		Set<String> windows = driver.getWindowHandles();
		if (i > 0) {
			for (int j = 0; j < 9; j++) {
				System.out.println("Windows: " + windows.size());
				hardWait(1);
				if (windows.size() >= 2) {
					try {
						Thread.sleep(5000);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				}
				windows = driver.getWindowHandles();
			}
		}
		String wins[] = windows.toArray(new String[windows.size()]);
		driver.switchTo().window(wins[i]);
		hardWait(1);
		System.out.println("Title: " + driver.switchTo().window(wins[i]).getTitle());
	}

	/**
	 * This method overrides the default wait timeout with the new wait time
	 * provided.
	 * 
	 * @param implicitWaitTimeout
	 *            : new implicit wait timeout
	 * @param expicitWaitTimeout
	 *            : new explicit wait time out
	 */

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	protected void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void closeCurrentWindow() {
		driver.close();
		logMessage("Current window is closed");
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	/**
	 * This method overrides the default wait timeout with the new wait time
	 * provided.
	 * 
	 * @param implicitWaitTimeout
	 *            : new implicit wait timeout
	 * @param expicitWaitTimeout
	 *            : new explicit wait time out
	 */
	public void resetWaitTimeOut(int implicitWaitTimeout, int expicitWaitTimeout) {
		this.wait.resetImplicitTimeout(implicitWaitTimeout);
		this.wait.resetExplicitTimeout(expicitWaitTimeout);
		logMessage("Wait Time reset ( implicit - " + implicitWaitTimeout + "seconds" + " Explicit - "
				+ expicitWaitTimeout + "seconds )");
	}

	public void resetWaitTimeOutToDefault() {
		int timeout = Integer.parseInt(getProperty("Config.properties", "timeout"));
		logMessage("RESETTING TO DEFAULT WAIT TIME : " + timeout);
		this.wait.resetImplicitTimeout(timeout);
		this.wait.resetExplicitTimeout(timeout);
	}

	public int getIntegerOnParsingAString(String value) {
		int parsedNumber = 0;
		try {
			parsedNumber = Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			logMessage("Number format exception caught while parsing string: -" + value);
		}
		return parsedNumber;
	}

}
