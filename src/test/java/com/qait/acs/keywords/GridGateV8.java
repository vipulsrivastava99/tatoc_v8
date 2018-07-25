package com.qait.acs.keywords;
import org.openqa.selenium.WebDriver;
import com.qait.automation.getpageobjects.GetPage;

public class GridGateV8 extends GetPage
{
	WebDriver driver;
	

	public GridGateV8(WebDriver driver) {
		super(driver, "tatocPage");
		this.driver = driver;
	}

	
	
	public void clickOnGreenBox() 
	{
		element("Btn_GreenBox").click();
		logMessage("User clicked on Green Box.");
	}
	

}


