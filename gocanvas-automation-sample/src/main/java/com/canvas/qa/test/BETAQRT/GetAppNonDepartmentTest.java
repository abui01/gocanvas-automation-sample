package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.ApplicationStorePage;
import com.canvas.qa.pages.apps.AppsPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author anna.marek
 *
 *
 */
//
@Test(description = "Test CSV format")
public class GetAppNonDepartmentTest extends BrowserLaunchTest {
	@Parameters({ "step", "testdescription" })//1
	public void adminAddApplication(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		try {
			loginTest.verifyValidLogin1(step, testdescription, parameters.get("admin_username"),
					parameters.get("admin_password"), testContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		i += 3;

		ApplicationStorePage appStoreLink = new ApplicationStorePage(driver);
		appStoreLink.allAppsButtonClick();

		appStoreLink.clickGetApp(parameters.get("admin_appName"));

		AppsPage appsPageLink = new AppsPage(driver);
		appsPageLink.searchApp(parameters.get("admin_appName"));
		if (appsPageLink.appExists(parameters.get("admin_appName"))) {

			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": App Correctly Added by Admin.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": App Not Correctly Added by Admin.");
		}
		org.testng.Assert.assertTrue(appsPageLink.appExists(parameters.get("admin_appName")));
		/*
		 * ReportManager.log(testContext.getName(), LogStatus.FAIL, "Step " +
		 * step + "." + (++i) + ": Verify new app appears in mobile app.");
		 */
	}

	@Parameters({ "step", "testdescription" })//3
	public void designerAddApplication(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		try {
			loginTest.verifyValidLogin1(step, testdescription, parameters.get("designer_username"),
					parameters.get("designer_password"), testContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		i += 3;

		ApplicationStorePage appStoreLink = new ApplicationStorePage(driver);
		appStoreLink.allAppsButtonClick();

		appStoreLink.clickGetApp(parameters.get("designer_appName"));

		AppsPage appsPageLink = new AppsPage(driver);
		appsPageLink.searchApp(parameters.get("designer_appName"));
		if (appsPageLink.appExists(parameters.get("designer_appName"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": App Correctly Added by Admin.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": App Correctly Added by Admin.");
		}
		org.testng.Assert.assertTrue(appsPageLink.appExists(parameters.get("designer_appName")));
		/*
		 * ReportManager.log(testContext.getName(), LogStatus.FAIL, "Step " +
		 * step + "." + (++i) + ": Verify new app appears in mobile app.");
		 */
		appsPageLink.refresh();
		appsPageLink.logout();

	}

	@Parameters({ "step", "testdescription" })//4
	public void postConditions(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		
		
		try {
			loginTest.verifyValidLogin1(step, testdescription, parameters.get("admin_username"),
					parameters.get("admin_password"), testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		
		
		AppsPage appsPageLink = new AppsPage(driver);
		appsPageLink.appsButtonClick();
		
		appsPageLink.refresh();
		appsPageLink.qlRetireApp(parameters.get("admin_appName"));
		appsPageLink.qlRetireApp(parameters.get("designer_appName"));
		
	}

	@Parameters({ "step", "testdescription" })
	public void verifyEmailOptions(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;
		AppsPage appsPageLink = new AppsPage(driver);
		
		appsPageLink.refresh();
		appsPageLink.appClick(parameters.get("admin_appName"));

		//Thread.sleep(2000);
		appsPageLink.emailOptionsClick();
		//Thread.sleep(10000);
		customWait(5);
		String email = appsPageLink.getEmailOpt();

		if (email.equalsIgnoreCase(email)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Correct email appears in Email Options.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Correct email not appears in Email Options.");
		}
		org.testng.Assert.assertTrue(email.equalsIgnoreCase(email));
		appsPageLink.logout();

	}
  }
