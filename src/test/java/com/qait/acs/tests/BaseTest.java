package com.qait.acs.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.GetPage;

public class BaseTest {
	protected GetPage logBase;
	protected TestSessionInitiator test;
	protected String urlForTemporaryStorage = null;
	protected int counterForTests;
	
	@BeforeClass
	public void Start_Test_Session() {
		counterForTests = 0;
		test = new TestSessionInitiator(this.getClass().getSimpleName());
	  //  test.launchApplication(getYamlValue("baseUrl"));
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.stepStartMessage(method.getName());
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result, Method method) {
		counterForTests = test.takescreenshot.incrementcounterForTests(counterForTests, result);
		test.takescreenshot.takeScreenShotOnException(result, counterForTests, method.getName());
	}

	@AfterClass
	public void close_Test_Session() throws IOException {
		test.closeBrowserSession();
	}

}
