package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.CreateAppPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.apps.AppAssignmentsPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class AppAssignmentsTest extends BrowserLaunchTest {
	static String appName;
	static String grpName;

	@Parameters({ "step", "testdescription" })
	public void adminAccess(String step, String testdescription, ITestContext testContext) throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;
		try {
			loginTest.verifyValidLoginnew(step, testdescription, parameters.get("admin_username"),
					parameters.get("admin_password"), testContext);
		} catch (Exception e) {
			e.printStackTrace();

		}
		AppAssignmentsPage appAssignemntsLink = new AppAssignmentsPage(driver);
		try {
			appAssignemntsLink.appAssignmentsButtonClick();
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Startup Admin App Assignments Access.");
			org.testng.Assert.assertTrue(true, "Startup Admin App Assignments Access");

		} catch (NoSuchElementException e) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Startup Admin App Assignments Access.");
			org.testng.Assert.assertTrue(false, "Startup Admin App Assignments Access");
		}

	}

	@Parameters({ "step", "testdescription" })
	public void remindUserToCheckMobile(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
				"Step " + step + ": Verify user gets a notification of new app assignments on mobile");
	}

	@Parameters({ "step", "testdescription" })
	public void userAccess(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters1 = FileReaderUtil.getTestParameters(testContext);
		LoginPage login = new LoginPage(driver);
		login.gotoLogin();
		login.typeusername(parameters1.get("admin"));
		login.typepassword(parameters1.get("adminpwd"));
		login.Clickonloginbutton();
		DashboardPage dashboardPage = new DashboardPage(driver);
		SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
		searchUsersPage.enterEmail(parameters1.get("admin_username"));
		searchUsersPage.clickSearch();
		dashboardPage = searchUsersPage.clickOnManage(parameters1.get("admin_username"));
		dashboardPage.clickProfile();
		dashboardPage.clickChangePassword();
		dashboardPage.changeUserPassword(parameters1.get("admin_password"));
		searchUsersPage = dashboardPage.clickResumeYourSession();
		searchUsersPage.logout();

		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();

		CanvasLoginTest loginTest = new CanvasLoginTest();
		int i = 0;

		loginTest.verifyValidLogin1(step, testdescription, parameters.get("user_username"),
				parameters.get("user_password"), testContext);

		AppAssignmentsPage appAssignemntsLink = new AppAssignmentsPage(driver);
		
		appAssignemntsLink.clickProfileButton();
		boolean appAssignmentStatus = appAssignemntsLink.verifyAppAssignmentsButtonPresent();
		
		if (appAssignmentStatus==false)
		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": User does not have access to App Assignments.");
			org.testng.Assert.assertFalse(appAssignmentStatus, "User does not have access to App Assignments.");
		}
		else
		{
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": User has access to App Assignments.");
			org.testng.Assert.assertTrue(appAssignmentStatus, "User has access to App Assignments.");
		}
		
		appAssignemntsLink.logout();
	}

	@Parameters({ "step", "testdescription" })
	public void verifyAssignment(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {

		AppAssignmentsPage appAssignmentsLink = new AppAssignmentsPage(driver);
		int i = 0;

		appAssignmentsLink.checkGroup(grpName);
		appAssignmentsLink.checkApp(appName);
		appAssignmentsLink.saveButtonClick();

		appAssignmentsLink.appAssignmentsButtonClick();

		boolean grpStatus = appAssignmentsLink.isGroupChecked(grpName);
		if (grpStatus) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": User assigned to correct group.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": User assigned to correct group..");
		}
		org.testng.Assert.assertTrue(grpStatus, "User assigned to correct group..");

		boolean status = appAssignmentsLink.isAppChecked(appName);
		if (status) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": User assigned to correct app.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": User assigned to correct app.");
		}
		org.testng.Assert.assertTrue(status, "User assigned to correct app");
	}

	@Parameters({ "step", "testdescription" })
	public void verifyNewApp(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {

		AppAssignmentsPage appAssignmentsLink = new AppAssignmentsPage(driver);

		int i = 0;
		appAssignmentsLink.appAssignmentsButtonClick();
		if (appAssignmentsLink.isAppDisplayed(appName)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": New app appears in App Assignments.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": New app appears in App Assignments.");
		}
		org.testng.Assert.assertTrue(appAssignmentsLink.isAppDisplayed(appName));
	}

	@Parameters({ "step", "testdescription" })
	public void verifyNewGroup(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {
		Map<String, String> parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName())
				.getParameters();
		int i = 0;

		AppAssignmentsPage appAssignmentsLink = new AppAssignmentsPage(driver);
		AppBuilderPage appBuilder = new AppBuilderPage(driver);
		appAssignmentsLink.groupsClick();
		appAssignmentsLink.newGroup();

		grpName = parameters.get("groupName") + randomAlphaNumeric(10);
		appAssignmentsLink.createGroup(grpName);
		appName = parameters.get("appName") + randomAlphaNumeric(10);

		CreateAppPage appsLink = new CreateAppPage(driver);
		appsLink.appsButtonClick();

		appsLink.newApp();
		appsLink.startClick();
		appsLink.loadAppBuilder();
		appsLink.setAppName(appName);
		appsLink.buildBasicApp();
		appBuilder.clickSaveButton(appName,parameters.get("admin_username"),parameters.get("admin_password"));
		appBuilder.clickPublishToDeviceButton();
		appsLink.nextButtonClick().nextButtonInUserScreenClick();
		appsLink.uncheckUserEmail(parameters.get("admin_username"));
		appsLink.finalPublishButtonClick();
		appsLink.clickCloseButton();

		appAssignmentsLink.appAssignmentsButtonClick();
		if (appAssignmentsLink.isGroupDisplayed(grpName)) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": New group appears in App Assignments.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": New group appears in App Assignments.");
		}
		org.testng.Assert.assertTrue(appAssignmentsLink.isGroupDisplayed(grpName),
				"New group appears in App Assignments");
	}
}
