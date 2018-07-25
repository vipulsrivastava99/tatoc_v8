package com.qait.acs.keywords;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.qait.automation.getpageobjects.GetPage;

public class DragAndDropV8 extends GetPage{
	WebDriver driver;

	public DragAndDropV8(WebDriver driver) {
		super(driver, "tatocPage");
		this.driver = driver;
	}
public void dragboxmouse()
{
	element("dragbox");
	element("dropbox");
	Actions builder = new Actions(driver);
	builder.dragAndDrop(element("dragbox"),element("dropbox")).perform();
	logMessage("User dragged drag box to dropbox");

	element("proceeddrag").click();
	
	
}


}

