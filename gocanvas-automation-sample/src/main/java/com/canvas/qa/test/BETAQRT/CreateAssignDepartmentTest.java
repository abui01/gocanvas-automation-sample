package com.canvas.qa.test.BETAQRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.core.ReportManager;
import com.canvas.qa.pages.CreateAssignDepartmentPage;
import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.SearchUsersPage;
import com.canvas.qa.pages.apps.AppBuilderPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;
import com.relevantcodes.extentreports.LogStatus;

@Test
public class CreateAssignDepartmentTest extends BrowserLaunchTest

{

	public static Map<String, String> parameters;
	public static String random = randomAlphaNumeric(10);

	public static void login(String step, String testdescription, String username, String password,
			ITestContext testContext) throws IOException, InterruptedException {
		CanvasLoginTest loginTest = new CanvasLoginTest();

		loginTest.verifyValidLogin1(step, testdescription, username, password, testContext);

	}

	@Parameters({ "step", "testdescription" })
	public void createApp(String step, String testdescription, ITestContext testContext) throws InterruptedException {

		int i = 0;

		CreateAppCommonTest createApp = new CreateAppCommonTest();
		boolean creationsuccess = createApp.createApp();
		if (creationsuccess) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": New App Created.");

		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": App creation.");

		}
		org.testng.Assert.assertTrue(creationsuccess, "Some error occured while creating new App.");
		
		AppBuilderPage appBuilderPage = new AppBuilderPage(driver);
		appBuilderPage.keepEditingButton();
		
		String appName = randomAlphaNumeric(10);
		appBuilderPage.clearAppName();
		appBuilderPage.enterAppName(appName);
		appBuilderPage.clickSaveButton(appName,parameters.get("companyadmin"),parameters.get("companyadminpassword"));
		appBuilderPage.clickCloseButton();
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickDepartmentDropDownIcon();
		dashboardPage.clickDepartment("All");
		dashboardPage.clickAccount();
		CreateAssignDepartmentPage createAssignDepartment = new CreateAssignDepartmentPage(driver);
		createAssignDepartment.enableDepartment();
	}

	@Parameters({ "step", "testdescription" })
	public void createDepartment(String step, String testdescription, ITestContext testContext)
			throws InterruptedException {

		int i = 0;

		CreateAssignDepartmentPage createAssignDepartment = new CreateAssignDepartmentPage(driver);
		String departName = random;
		String memberText = createAssignDepartment.createDepartment(parameters.get("companyadminname"), departName,
				parameters.get("departmentdesc"));
		if (memberText.contains(parameters.get("companyadminname"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Company Admin appears under Department member.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Company Admin appears under Department member.");
		}
		org.testng.Assert.assertTrue(memberText.contains(parameters.get("companyadminname")),
				"Company Admin Not appears under Department member.");

		Thread.sleep(2000);
		if (memberText.contains(parameters.get("adminrole"))) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": Company Admin is Department admin.");

		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Company Admin is Department admin.");

		}

		org.testng.Assert.assertTrue(memberText.contains(parameters.get("adminrole")),
				"Company Admin Not appears under Department member.");
	}

	@Parameters({ "step", "testdescription" })
	public void enableDepartmentalFunctionality(String step, String testdescription, ITestContext testContext)
			throws InterruptedException, IOException {
		Map<String, String> parameters1 = FileReaderUtil.getTestParameters(testContext);
		LoginPage login = new LoginPage(driver);
		login.gotoLogin();
		login.typeusername(parameters1.get("admin"));
		login.typepassword(parameters1.get("adminpwd"));
		login.Clickonloginbutton();
		DashboardPage dashboardPage = new DashboardPage(driver);
		SearchUsersPage searchUsersPage = dashboardPage.clickSearchUsers();
		searchUsersPage.enterEmail(parameters1.get("companyadmin"));
		searchUsersPage.clickSearch();
		searchUsersPage.clickOnMore(parameters1.get("companyadmin").toLowerCase());
		searchUsersPage.clickFeatures();
		if (searchUsersPage.isDisableDepartmentDisplayed()) {
			searchUsersPage.clickDisableDepartment();
		}
		searchUsersPage.logout();

		int i = 3;
		parameters = FileReaderUtil.testMap.get(testContext.getCurrentXmlTest().getName()).getParameters();
		login(step, testdescription, parameters.get("companyadmin"), parameters.get("companyadminpassword"),
				testContext);

		CreateAssignDepartmentPage createAssignDepartment = new CreateAssignDepartmentPage(driver);

		String pageSource = createAssignDepartment.enableDepartment();

		/*
		 * if
		 * (pageSource.contains("Department Functionality has been enabled.")) {
		 * ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.PASS, "Step " + step + "." + (++i) +
		 * ": Department Functionality has been enabled.");
		 * 
		 * } else { ReportManager.lognew(testContext.getName(), testdescription,
		 * LogStatus.FAIL, "Step " + step + "." + (++i) +
		 * ": Department Functionality has been enabled."); }
		 * 
		 * org.testng.Assert.assertTrue((pageSource.
		 * contains("Department Functionality has been enabled."))
		 * ,"User Not able to See Apps Page:");
		 */

		String departDisplayed = createAssignDepartment.departmentDisplayed();
		if (departDisplayed.equals("All")) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": All appears in department DDL.");
		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": Issue in selecting the department from DDL.");
		}
		org.testng.Assert.assertEquals(departDisplayed, "All", "Issue in selecting the department All from DDL.");

	}

	@Parameters({ "step", "testdescription" })
	public void verifyNoApp(String step, String testdescription, ITestContext testContext) throws InterruptedException {

		int i = 0;
		String departName = random;
		CreateAssignDepartmentPage createAssignDepartment = new CreateAssignDepartmentPage(driver);
		boolean success = createAssignDepartment.verifyNoApp(parameters.get("designername"),
				parameters.get("designerrole"), departName);
		if (success) {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.PASS,
					"Step " + step + "." + (++i) + ": No App is appear in the selected department.");

		} else {
			ReportManager.lognew(testContext.getName(), testdescription, LogStatus.FAIL,
					"Step " + step + "." + (++i) + ": No app assigned to new department.");
		}
		org.testng.Assert.assertTrue(success, "Some app already assigned to new department. It Should not be.");

	}

}
