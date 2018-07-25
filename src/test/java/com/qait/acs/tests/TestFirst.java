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
	}	
	
	@Test(priority=3)
	public void Step03_GridGate(){

		test.gridGateObject.clickOnGreenBox();
		}	
	@Test(priority=4)
	public void Step04_FrameDungeon(){
	test.frameDungeonObject.switchframesprocess();
		}	
	@Test(priority=5)
	public void Step05_DragAndDrop(){
	test.dragAndDropObject.dragboxmouse();
		}	
	
}


