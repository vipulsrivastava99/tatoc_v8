package com.qait.acs.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class SSO_LoginPageAction extends GetPage {
	public SSO_LoginPageAction(WebDriver driver) {
		super(driver, "SSO_LoginPage");

	}

	public void verifySSOPageIsDisplayed(String expectedURL) {
		
		wait.hardWait(5);
		Assert.assertEquals(getCurrentURL(),expectedURL,"Assertion Failed : Unable to launch SSO Page");
		logMessage("Assertion Passed : User successfully launched the application");
	}

	public void verifyComponents() {
		isElementDisplayed("field_username");
		logMessage("Info : Username Field is Displayed");
		isElementDisplayed("field_password");
		logMessage("Info : Password Field is Displayed");
		isElementDisplayed("btn_login");
		logMessage("Info : Login Button is Displayed");
	
	}

	public void enterUsername(String usrname) {
		element("field_username").sendKeys(usrname);
		logMessage("User entered value in username field");
	}

	public void enterPassword(String password) {
		element("field_password").sendKeys(password);
		logMessage("User entered value in password field");
	}

	public void clickOnLogin() {
		isElementDisplayed("btn_login");
		element("btn_login").click();
		logMessage("User clicked on login button");
	}

	public void check_incorrectCredentialsStatement() {
		isElementDisplayed("ele_incorrect_cred");
		logMessage("User coul'nt log in");
	}


	public void verifyPageAfterClickingOnLoginButtonWithCorrectCredentials(String pageurl) {
       
		System.out.println(getCurrentURL());
		Assert.assertTrue(getCurrentURL().contains(pageurl));
		isElementDisplayed("ele_SSCenter");
		logMessage("User successfully logged in");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
	
			e.printStackTrace();
		}
	}

	public void samePageAfterClickingLoginWithoutEnteringCredentials(String pageurl) {
		Assert.assertEquals(pageurl, getCurrentURL());
		
		isElementDisplayed("field_username");
		isElementDisplayed("field_password");
		logMessage("User is still on same login page");

	}
}
