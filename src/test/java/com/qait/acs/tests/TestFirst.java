package com.qait.acs.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.Test;

public class TestFirst extends BaseTest {

	@Test(priority=1)
	public void Step01_Launch_Application() {
		
		test.launchApplication(getYamlValue("baseUrl"));
	}
	
	

	@Test(priority=2)
	public void Step02_BasicCourse()
	{

		test.WelcomePageObject.clickOnBasicCourse();
		test.WelcomePageObject.checkGridGatePageOpened("http://10.0.1.86/tatoc/basic/grid/gate");
	}	
	
	@Test(priority=3)
	public void Step03_GridGate(){

		test.gridGateObject.clickOnGreenBox();
		test.gridGateObject.checkFrameDungeonPageOpened("http://10.0.1.86/tatoc/basic/frame/dungeon");
		}	
	@Test(priority=4)
	public void Step04_FrameDungeon(){
	test.frameDungeonObject.switchframesprocess();
	test.frameDungeonObject.checkDraganddropPageOpened("http://10.0.1.86/tatoc/basic/drag");
		}	
	@Test(priority=5)
	public void Step05_DragAndDrop(){
	test.dragAndDropObject.dragboxmouse();
	test.dragAndDropObject.checkPopupWindowPageOpened("http://10.0.1.86/tatoc/basic/windows");
		}	
	
}


