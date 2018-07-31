
package com.qait.acs.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class frameDungeonV8 extends GetPage {

	WebDriver driver;

	public frameDungeonV8(WebDriver driver) {
		super(driver, "tatocPage");
		this.driver = driver;
	}

	public void switchframesprocess() {
		switchToFrame("main");
		int flag = 0;
		while (flag == 0) {
			String s1 = element("get_color_box1").getAttribute("class");
			switchToFrame("child");
			logMessage("Switched to child frame");

			String s2 = element("get_color_box2").getAttribute("class");
			switchToDefaultContent();
			switchToFrame("main");
			logMessage("Switched to main frame");

			if (s1.equals(s2)) {
				flag = 1;
				element("proceed").click();
				logMessage("User clicked on proceed button.");

			} else {
				element("repaintbox2").click();
				logMessage("User clicked on repaintbox2 button.");

			}
		}
		
	}
		public void checkDraganddropPageOpened(String pageurl) {
			
			Assert.assertEquals(getCurrentURL(),pageurl,"Drag and drop Page is not opened");
			logMessage("Drag and drop Page is successfully opened");
			
		}

	
}