package com.qait.acs.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class OpenStarPageAction extends GetPage{
public OpenStarPageAction(WebDriver driver)
{
	super(driver,"OpenStarPage");
	
}
public void verifySSOPageIsDisplayed()
{
	wait.hardWait(5);
	Assert.assertEquals("https://sso.acs.org/idp/service/loginrequest",getCurrentURL(),"Assertion Failed : Unable to launch SSO Page");
	logMessage("Assertion Passed : User successfully launched the application");
}
public void verifyComponents()
{
	isElementDisplayed("field_username");
//	Assert.assertEquals("userid",getValue,"Assertion Failed :Username field is not displayed");
	logMessage("Info : Username Field is Displayed");
	isElementDisplayed("field_password");
	logMessage("Info : Password Field is Displayed");
	/*getValue=element("field_password").getText();
	Assert.assertEquals("userid",getValue,"Password field is not displayed");
	logMessage("Assertion Passed : Password Field is Displayed");
*/	isElementDisplayed("btn_login");
logMessage("Info : Login Button is Displayed");
/*getValue=element("btn_login").getText();
	Assert.assertEquals("Log In",getValue,"LogIn Button is not displayed");
	logMessage("Assertion Passed : LogIn is Displayed");*/
}
public void writeCredentialsAndClickOnLogin()
{
  element("field_username").sendKeys("ACS1\\C00767");
  logMessage("User wrote values in username field");
  element("field_password").sendKeys("AHt3m^9DW");
  logMessage("User wrote values in password field");
  element("btn_login").click();
  logMessage("User clicked on login button");
}
}
