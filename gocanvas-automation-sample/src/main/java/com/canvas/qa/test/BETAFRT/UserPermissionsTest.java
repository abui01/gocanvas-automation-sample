package com.canvas.qa.test.BETAFRT;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.canvas.qa.pages.DashboardPage;
import com.canvas.qa.pages.profile.LoginPage;
import com.canvas.qa.test.BrowserLaunchTest;
import com.canvas.util.FileReaderUtil;

public class UserPermissionsTest extends BrowserLaunchTest {

	@Test
	@Parameters({ "testdescription" })
	public void accountAdminAndReporterTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as the Account Admin: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department1"));
			String[] menuList = parameters.get("list1").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify menu displays: " + menuList[i], "1.1." + i,
						menuList[i] + " : menu displayed");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickAccount();
			dashboardPage.clickAccount();
			menuList = parameters.get("list2").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuOptionDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify menu displays for Account: " + menuList[i], "1.2." + i,
						menuList[i] + " : menu displayed for Account");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickReports();
			menuList = parameters.get("list3").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuOptionDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify option displays for Reports: " + menuList[i],
						"1.3." + i, menuList[i] + " : option displayed for Reports");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department2"));
			menuList = parameters.get("list4").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify menu displays: " + menuList[i], "2." + i,
						menuList[i] + " : menu displayed");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickDepartmentMenu();
			dashboardPage.clickDepartmentMenu();
			menuList = parameters.get("list5").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuOptionDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify option displays for Department: " + menuList[i],
						"3." + i, menuList[i] + " : option displayed for Department");
				org.testng.Assert.assertTrue(status);
			}

			LoginPage loginPage = dashboardPage.clickLogOut();
			loginPage.typeusername(parameters.get("username2"));
			loginPage.typepassword(parameters.get("password"));
			loginPage.Clickonloginbutton();
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department1"));
			menuList = parameters.get("list6").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify menu displays: " + menuList[i], "4.0." + i,
						menuList[i] + " : menu displayed");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickReports();
			menuList = parameters.get("list7").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuOptionDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify option displays for Reports: " + menuList[i],
						"4.1." + i, menuList[i] + " : option displayed for Reports");
				org.testng.Assert.assertTrue(status);
			}

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}

	@Test
	@Parameters({ "testdescription" })
	public void departmentAdminReporterUserDesignerTest(String testDescription, ITestContext testContext)
			throws IOException, InterruptedException {
		Map<String, String> parameters = FileReaderUtil.getTestParameters(testContext);
		try {
			performLogin(1, parameters.get("username"), parameters.get("password"),
					"Login as: " + parameters.get("username"), testContext, testDescription);
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department1"));
			String[] menuList = parameters.get("list1").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify menu displays: " + menuList[i], "1.1." + i,
						menuList[i] + " : menu displayed");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickDepartmentMenu();
			menuList = parameters.get("list2").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuOptionDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify menu displays: " + menuList[i], "1.2." + i,
						menuList[i] + " : menu displayed");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickReports();
			menuList = parameters.get("list3").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuOptionDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify option displays: " + menuList[i], "1.2." + i,
						menuList[i] + " : option displayed for reports");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department2"));
			menuList = parameters.get("list4").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify menu displays: " + menuList[i], "2.0." + i,
						menuList[i] + " : menu displayed");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickReports();
			menuList = parameters.get("list5").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuOptionDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify option displays: " + menuList[i], "2.1." + i,
						menuList[i] + " : option displayed for reports");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department3"));
			menuList = parameters.get("list6").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify menu displays: " + menuList[i], "3.0." + i,
						menuList[i] + " : menu displayed");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickReports();
			menuList = parameters.get("list5").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuOptionDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify option displays: " + menuList[i], "3.1." + i,
						menuList[i] + " : option displayed for reports");
				org.testng.Assert.assertTrue(status);
			}

			dashboardPage.clickDepartmentDropDownIcon();
			dashboardPage.clickDepartment(parameters.get("department4"));
			menuList = parameters.get("list7").split(";");
			for (int i = 0; i < menuList.length; i++) {
				boolean status = dashboardPage.isMenuDisplayed(menuList[i]);
				reportLog(status, testContext.getName(), "Verify menu displays: " + menuList[i], "4.0." + i,
						menuList[i] + " : menu displayed");
				org.testng.Assert.assertTrue(status);
			}

			reportLog(true, testContext.getName(), testDescription, "", "FINISHED");
		} catch (Exception e) {
			reportLog(false, testContext.getName(), testDescription, "Error encountered - Details: ", e.getMessage());
			org.testng.Assert.assertTrue(false, e.getMessage());
		}
	}
}
