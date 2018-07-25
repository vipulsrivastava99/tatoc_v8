package com.qait.acs.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.SampleDataProvider;

public class TestPageActions extends GetPage {

	WebDriver driver;
	SampleDataProvider fileData;

	public TestPageActions(WebDriver driver) {
		super(driver, "testPage");
		this.driver = driver;
	}

	public void printSpecificRowData(String String) {
		System.out.println("+++++++++");
		logMessage("Demo String: '"+String+"'");
		System.out.println("+++++++++");
	}

	public void clickAnyElementOnWebsite() {
		element("Btn_FeelingLucky").click();
		logMessage("User clicked on 'I am feeling lucky' button.");
	}

}
