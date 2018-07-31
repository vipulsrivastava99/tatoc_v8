package com.qait.acs.tests;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.apache.tools.ant.util.WeakishReference.HardReference;
import org.testng.annotations.Test;

public class TestStarsCustomerPortal extends BaseTest
{
	@Test(priority=1)
	public void Step01_Stars_01_Verify_Successful_Launch_Of_The_Application() throws InterruptedException
	{
		 test.launchApplication(getYamlValue("baseUrl"));
		 test.sso_LoginPageObject.verifySSOPageIsDisplayed("https://sso.acs.org/idp/service/loginrequest");
		 Thread.sleep(8000);
	} 
	@Test(priority=2)
	public void Step02_Stars_02_Verify_Components_Present_On_The_Login_Page()
	{
		test.sso_LoginPageObject.verifyComponents();
	}

	@Test(priority=3)
	public void Step02_Stars_04_Verify_Not_Allow_Login_With_InValid_Credentials()
	{
	test.sso_LoginPageObject.enterUsername(getYamlValue("InValidUsername"));
	test.sso_LoginPageObject.enterPassword(getYamlValue("InValidPassword"));
	test.sso_LoginPageObject.clickOnLogin();
	test.sso_LoginPageObject.check_incorrectCredentialsStatement();
	}
	
	
	@Test(priority=4)
	public void Step03_Stars_06_Verify_Not_Allow_Login_With_Valid_Username_Invalid_Password()
	{
	test.sso_LoginPageObject.enterUsername(getYamlValue("ValidUsername"));
	test.sso_LoginPageObject.enterPassword(getYamlValue("InValidPassword"));
	test.sso_LoginPageObject.clickOnLogin();
	test.sso_LoginPageObject.check_incorrectCredentialsStatement();
	}
	@Test(priority=5)
	public void Step04_Stars_05_Verify_Allow_Login_With_InValid_Username_Valid_Password()
	{
	test.sso_LoginPageObject.enterUsername(getYamlValue("InValidUsername"));
	test.sso_LoginPageObject.enterPassword(getYamlValue("ValidPassword"));
	test.sso_LoginPageObject.clickOnLogin();
	test.sso_LoginPageObject.check_incorrectCredentialsStatement();

	}
	@Test(priority=6)
	public void Step05_Stars_07_Verify_Not_Allow_Login_Without_Entering_Credentials()
	{
	
	test.sso_LoginPageObject.clickOnLogin();
	test.sso_LoginPageObject.samePageAfterClickingLoginWithoutEnteringCredentials("https://sso.acs.org/idp/service/login");
	
	}
	
	@Test(priority=7)
	public void Step06_Stars_03_Verify_Allow_Login_With_Valid_Credentials()
	{
	test.sso_LoginPageObject.enterUsername(getYamlValue("ValidUsername"));
	test.sso_LoginPageObject.enterPassword(getYamlValue("ValidPassword"));
	test.sso_LoginPageObject.clickOnLogin();
	test.sso_LoginPageObject.verifyPageAfterClickingOnLoginButtonWithCorrectCredentials("https://cherwellcsstag.acs.org/CherwellPortal/STARS?_");

	}
}