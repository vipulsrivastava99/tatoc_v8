package com.qait.acs.tests;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import org.testng.annotations.Test;

public class TestStarsCustomerPortal extends BaseTest
{
	@Test(priority=0)
	public void Step01_Stars_01_Verify_Successful_Launch_Of_The_Application()
	{
		 test.launchApplication(getYamlValue("baseUrl"));
		 test.openStarPage.verifySSOPageIsDisplayed();
	}
	@Test(priority=1)
	public void Step02_Stars_02_Verify_Components_Present_On_The_Login_Page()
	{
		test.openStarPage.verifyComponents();
	}
	@Test(priority=2)
	public void Step03_Stars_03_Verify_Allow_Login_With_Valid_Credentials()
	{
	test.openStarPage.writeCredentialsAndClickOnLogin();
	try {
		Thread.sleep(4000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}}
