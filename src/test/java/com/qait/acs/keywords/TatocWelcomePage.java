package com.qait.acs.keywords;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class TatocWelcomePage extends GetPage
{
	WebDriver driver;
	

	public TatocWelcomePage(WebDriver driver) {
		super(driver, "tatocPage");
		this.driver = driver;
	}

	
	
	public void clickOnBasicCourse() {
		
		element("Btn_BasicCourse").click();
		logMessage("User clicked on Basic Course.");
		
		
	}

	public void checkGridGatePageOpened(String pageurl) {
		
		Assert.assertEquals(getCurrentURL(),pageurl,"Assertion Failed : GridGate Page is not opened");
		logMessage("Assertion Passed : GridGate Page is successfully opened");
		
	}

}


